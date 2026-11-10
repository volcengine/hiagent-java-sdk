package com.volcengine.hiagent.api;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.volcengine.ApiException;
import com.volcengine.auth.CredentialProvider;
import com.volcengine.auth.StaticCredentialProvider;
import com.volcengine.hiagent.api.model.DeleteRequest;
import com.volcengine.hiagent.api.model.DownloadKeyRequest;
import com.volcengine.hiagent.api.model.DownloadKeyResponse;
import com.volcengine.hiagent.api.model.DownloadRequest;
import com.volcengine.hiagent.api.model.LongLiveRequest;
import com.volcengine.hiagent.api.model.LongLiveResponse;
import com.volcengine.hiagent.api.model.UploadRawRequest;
import com.volcengine.hiagent.api.model.UploadRawResponse;
import com.volcengine.sign.Credentials;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Drives every {@link UpClient} method against a local server and checks that
 * what arrives is exactly what was signed: the signature is recomputed from the
 * received path, query and headers with the vector-proven {@link UpSigner}, and
 * the received {@code X-Content-Sha256} must be the hash of the received body.
 */
class UpClientTest {
    private static final String AK = "AKLTUPCLIENTTESTONLY";
    private static final String SK = "up-client-test-secret-not-real";
    private static final String REGION = "cn-north-1";
    private static final String EMPTY_SHA256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
    private static final int LARGE_FILE_BYTES = 300 * 1024;
    private static final long TRUNCATED_READ_TIMEOUT_MS = 500;

    @TempDir
    Path tmp;

    private HttpServer server;
    private final List<Captured> captured = new CopyOnWriteArrayList<>();
    private volatile int downloadStatus = 200;
    private volatile byte[] downloadBody = new byte[0];
    private volatile boolean truncateDownload;

    @BeforeEach
    void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", this::handle);
        server.start();
    }

    @AfterEach
    void stopServer() {
        server.stop(0);
    }

    @Test
    void uploadRawSignsTheQueryItSendsAndStreamsTheRealBytes() throws Exception {
        byte[] content = binaryContent(LARGE_FILE_BYTES);
        Path file = Files.write(tmp.resolve("photo.jpg"), content);
        UploadRawRequest req = new UploadRawRequest();
        req.setId("id 1+2/=&");
        req.setExpire("15h");
        req.setContentType("image/jpeg");

        UploadRawResponse resp = client("", null).uploadRaw(req, file.toFile());

        Captured c = single();
        assertEquals("POST", c.method);
        assertEquals("/", c.rawPath);
        Map<String, List<String>> query = c.query();
        assertEquals(Arrays.asList("UploadRaw"), query.get("Action"));
        assertEquals(Arrays.asList("2023-08-01"), query.get("Version"));
        assertEquals(Arrays.asList("id 1+2/=&"), query.get("Id"));
        assertEquals(Arrays.asList("15h"), query.get("Expire"));
        assertFalse(query.containsKey(""), "no empty-name query parameter");
        assertArrayEquals(content, c.body);
        assertTrue(c.header("content-type").startsWith("image/jpeg"));
        assertSignatureMatchesReceived(c);
        assertPayloadHashMatchesBody(c);
        assertEquals("temp/uploaded", resp.getPath());
    }

    @Test
    void uploadRawSendsTheCallerHashAsGiven() throws Exception {
        Path file = Files.write(tmp.resolve("a.txt"), "hello".getBytes(StandardCharsets.UTF_8));
        String callerHash = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
        UploadRawRequest req = new UploadRawRequest();
        req.setId("id-1");
        req.setSha256("  " + callerHash + " ");

        client("", null).uploadRaw(req, file.toFile());

        Captured c = single();
        assertEquals(callerHash, c.header("x-content-sha256"));
        assertTrue(c.header("content-type").startsWith("application/octet-stream"));
        assertSignatureMatchesReceived(c);
    }

    @Test
    void uploadRawReportsAnUnreadableFile() {
        UploadRawRequest req = new UploadRawRequest();
        req.setId("id-1");

        assertThrows(ApiException.class,
                () -> client("", null).uploadRaw(req, tmp.resolve("missing.bin").toFile()));
        assertTrue(captured.isEmpty());
    }

    @Test
    void endpointPathIsSentAndSignedWithOrWithoutTrailingSlash() throws Exception {
        Path file = Files.write(tmp.resolve("a.bin"), binaryContent(16));
        UploadRawRequest req = new UploadRawRequest();
        req.setId("id-1");

        client("/up", null).uploadRaw(req, file.toFile());
        client("/up/", null).uploadRaw(req, file.toFile());

        assertEquals(2, captured.size());
        for (Captured c : captured) {
            assertEquals("/up/", c.rawPath);
            assertSignatureMatchesReceived(c);
            assertPayloadHashMatchesBody(c);
        }
    }

    @Test
    void stsSessionTokenIsSentWithTheSignedRequest() throws Exception {
        ApiClient apiClient = apiClient("", null);
        apiClient.setCredentials(Credentials.getCredentials(AK, SK, "sts-session-token"));
        DeleteRequest req = new DeleteRequest();
        req.setId("id-1");

        new UpClient(apiClient).delete(req);

        Captured c = single();
        assertEquals("sts-session-token", c.header("x-security-token"));
        assertSignatureMatchesReceived(c);
    }

    @Test
    void credentialProviderIsUsedWhenNoStaticCredentialsAreSet() throws Exception {
        com.volcengine.ApiClient apiClient = new com.volcengine.ApiClient()
                .setEndpoint("127.0.0.1:" + server.getAddress().getPort())
                .setDisableSSL(true)
                .setRegion(REGION)
                .setCredentialProvider(new CredentialProvider(new StaticCredentialProvider(AK, SK, null)));
        DeleteRequest req = new DeleteRequest();
        req.setId("id-1");

        new UpClient(apiClient).delete(req);

        assertSignatureMatchesReceived(single());
    }

    @Test
    void credentialsOkHttpCannotSendFailWithoutEchoingThem() {
        Credentials[] unsendable = {
                Credentials.getCredentials(AK, SK, "secret-token\nx"),
                Credentials.getCredentials(AK, SK, "secret-tokén"),
                Credentials.getCredentials("AKLTsecreté", SK),
        };
        for (Credentials credentials : unsendable) {
            ApiClient apiClient = apiClient("", null);
            apiClient.setCredentials(credentials);
            DeleteRequest req = new DeleteRequest();
            req.setId("id-1");

            ApiException e = assertThrows(ApiException.class, () -> new UpClient(apiClient).delete(req));

            for (Throwable t = e; t != null; t = t.getCause()) {
                assertFalse(String.valueOf(t.getMessage()).contains("secret"), t.toString());
            }
        }
        assertTrue(captured.isEmpty(), "nothing may be sent");
    }

    @Test
    void downloadKeySendsReservedAndUnicodeValuesInTheSignedQuery() throws Exception {
        String path = "temp/中文 报告 a+b=c&d!'()*~.pdf";
        DownloadKeyRequest req = new DownloadKeyRequest();
        req.setPath(path);
        req.setSha256(EMPTY_SHA256);

        DownloadKeyResponse resp = client("", null).downloadKey(req);

        Captured c = single();
        assertEquals(Arrays.asList(path), c.query().get("path"));
        assertEquals(Arrays.asList(EMPTY_SHA256), c.query().get("sha256"));
        assertEquals(0, c.body.length);
        assertEquals(EMPTY_SHA256, c.header("x-content-sha256"));
        assertSignatureMatchesReceived(c);
        assertEquals("download-key", resp.getKey());
    }

    @Test
    void longLiveSendsOnlyActionAndVersionInTheQuery() throws Exception {
        LongLiveRequest req = new LongLiveRequest();
        req.setPath("temp/中文 报告.pdf");
        req.setId("id-1");

        LongLiveResponse resp = client("", null).longLive(req);

        Captured c = single();
        assertEquals(Arrays.asList("Action", "Version"), new ArrayList<>(c.query().keySet()));
        String json = new String(c.body, StandardCharsets.UTF_8);
        assertTrue(json.contains("\"Path\":\"temp/中文 报告.pdf\""), json);
        assertTrue(json.contains("\"Id\":\"id-1\""), json);
        assertTrue(c.header("content-type").startsWith("application/json"));
        assertEquals("application/json", c.header("accept"));
        assertSignatureMatchesReceived(c);
        assertPayloadHashMatchesBody(c);
        assertEquals("temp/long", resp.getPath());
    }

    @Test
    void deleteIsSignedAndServerErrorsCarryResponseMetadata() throws Exception {
        DeleteRequest req = new DeleteRequest();
        req.setId("id-1");

        client("", null).delete(req);

        Captured c = single();
        assertEquals("Delete", c.query().get("Action").get(0));
        assertEquals("application/json", c.header("accept"));
        assertSignatureMatchesReceived(c);
        assertPayloadHashMatchesBody(c);

        DeleteRequest failing = new DeleteRequest();
        failing.setId("fail");
        ApiException e = assertThrows(ApiException.class, () -> client("", null).delete(failing));
        assertEquals(500, e.getCode());
        assertNotNull(e.getResponseMetadata(), "error envelope must be attached");
        assertEquals("InternalError", e.getResponseMetadata().getError().getCode());
    }

    @Test
    void networkFailuresKeepTheirCause() throws Exception {
        int closedPort;
        try (ServerSocket socket = new ServerSocket(0, 1, InetAddress.getLoopbackAddress())) {
            closedPort = socket.getLocalPort();
        }
        DeleteRequest req = new DeleteRequest();
        req.setId("id-1");
        UpClient client = new UpClient(new ApiClient("http://127.0.0.1:" + closedPort, AK, SK, REGION));

        ApiException e = assertThrows(ApiException.class, () -> client.delete(req));

        assertTrue(e.getMessage().startsWith("Failed to execute request: "), e.getMessage());
        assertTrue(e.getCause() instanceof IOException, String.valueOf(e.getCause()));
    }

    @Test
    void downloadWritesIntoMissingDirectories() throws Exception {
        downloadBody = binaryContent(LARGE_FILE_BYTES);
        Path target = tmp.resolve("nested/dir/out.bin");
        DownloadRequest req = new DownloadRequest();
        req.setKey("download-key");
        req.setSaveTo(target.toString());

        client("", null).download(req);

        assertArrayEquals(downloadBody, Files.readAllBytes(target));
        assertEquals(Arrays.asList("out.bin"), listNames(target.getParent()));
        Captured c = single();
        assertSignatureMatchesReceived(c);
        assertPayloadHashMatchesBody(c);
    }

    @Test
    void downloadFailureCarriesTheErrorEnvelopeAndLeavesNoFile() throws Exception {
        // What UP answers when Download gets a Key but neither Path nor Sha256.
        downloadStatus = 400;
        downloadBody = ("{\"ResponseMetadata\":{\"RequestId\":\"req-1\",\"Action\":\"Download\","
                + "\"Version\":\"2023-08-01\",\"Error\":{\"Code\":\"InvalidParameter\","
                + "\"Message\":\"sha256 or path must be provided\"}}}").getBytes(StandardCharsets.UTF_8);
        Path dir = Files.createDirectories(tmp.resolve("out"));
        DownloadRequest req = new DownloadRequest();
        req.setKey("download-key");
        req.setSaveTo(dir.resolve("out.bin").toString());

        ApiException e = assertThrows(ApiException.class, () -> client("", null).download(req));

        assertEquals("Download failed with status: 400", e.getMessage());
        assertEquals(400, e.getCode());
        assertNotNull(e.getResponseMetadata(), "error envelope must be attached");
        assertEquals("InvalidParameter", e.getResponseMetadata().getError().getCode());
        assertEquals("sha256 or path must be provided", e.getResponseMetadata().getError().getMessage());
        assertTrue(listNames(dir).isEmpty(), "no file may be written");
    }

    @Test
    void truncatedDownloadKeepsTheOldFile() throws Exception {
        truncateDownload = true;
        downloadBody = binaryContent(LARGE_FILE_BYTES);
        Path dir = Files.createDirectories(tmp.resolve("out"));
        Path target = Files.write(dir.resolve("out.bin"), "previous".getBytes(StandardCharsets.UTF_8));
        DownloadRequest req = new DownloadRequest();
        req.setKey("download-key");
        req.setSaveTo(target.toString());
        // The JDK test server keeps the socket open after the short body, so the
        // client fails mid-body on its read timeout; keep that short.
        ApiClient apiClient = apiClient("", null);
        apiClient.getHttpClient().setReadTimeout(TRUNCATED_READ_TIMEOUT_MS, TimeUnit.MILLISECONDS);

        ApiException e = assertThrows(ApiException.class, () -> new UpClient(apiClient).download(req));

        assertTrue(e.getMessage().startsWith("Failed to save file: "), e.getMessage());
        assertArrayEquals("previous".getBytes(StandardCharsets.UTF_8), Files.readAllBytes(target));
        assertEquals(Arrays.asList("out.bin"), listNames(dir), "nothing else may be written");
    }

    @Test
    void downloadAcceptsABareFileName() throws Exception {
        downloadBody = "bare".getBytes(StandardCharsets.UTF_8);
        String name = "up-client-test-" + System.nanoTime() + ".bin";
        Path target = Paths.get(name);
        DownloadRequest req = new DownloadRequest();
        req.setKey("download-key");
        req.setSaveTo(name);
        try {
            client("", null).download(req);
            assertArrayEquals(downloadBody, Files.readAllBytes(target));
        } finally {
            Files.deleteIfExists(target);
        }
    }

    @Test
    void productCodeIsSentByEveryMethodOnlyWhenConfigured() throws Exception {
        callEveryMethod(client("", "maas"));
        assertEquals(5, captured.size());
        for (Captured c : captured) {
            assertEquals("maas", c.header("x-trace-product-code"), c.action());
            assertSignatureMatchesReceived(c);
        }

        captured.clear();
        callEveryMethod(client("", null));
        assertEquals(5, captured.size());
        for (Captured c : captured) {
            assertNull(c.header("x-trace-product-code"), c.action());
        }
    }

    @Test
    void authorizationUsesTheUpServiceAndExcludesHost() throws Exception {
        callEveryMethod(client("", null));
        for (Captured c : captured) {
            String authorization = c.header("authorization");
            assertTrue(authorization.contains("/" + REGION + "/up/request, SignedHeaders=x-content-sha256;x-date, "),
                    c.action() + ": " + authorization);
        }
    }

    private void callEveryMethod(UpClient client) throws Exception {
        Path file = Files.write(tmp.resolve("every-" + System.nanoTime() + ".bin"), binaryContent(32));
        UploadRawRequest upload = new UploadRawRequest();
        upload.setId("id-1");
        client.uploadRaw(upload, file.toFile());

        LongLiveRequest longLive = new LongLiveRequest();
        longLive.setPath("temp/x");
        longLive.setId("id-1");
        client.longLive(longLive);

        DownloadKeyRequest downloadKey = new DownloadKeyRequest();
        downloadKey.setPath("temp/x");
        client.downloadKey(downloadKey);

        DownloadRequest download = new DownloadRequest();
        download.setKey("download-key");
        download.setSaveTo(tmp.resolve("dl-" + System.nanoTime()).toString());
        client.download(download);

        DeleteRequest delete = new DeleteRequest();
        delete.setId("id-1");
        client.delete(delete);
    }

    private UpClient client(String endpointPath, String productCode) {
        return new UpClient(apiClient(endpointPath, productCode));
    }

    private ApiClient apiClient(String endpointPath, String productCode) {
        String endpoint = "http://127.0.0.1:" + server.getAddress().getPort() + endpointPath;
        return new ApiClient(endpoint, AK, SK, REGION, productCode);
    }

    private Captured single() {
        assertEquals(1, captured.size(), "requests received");
        return captured.get(0);
    }

    /** Re-signs the request as received and compares it with the Authorization that arrived. */
    private void assertSignatureMatchesReceived(Captured c) {
        String url = "http://127.0.0.1:" + server.getAddress().getPort() + c.rawPath
                + (c.rawQuery == null ? "" : "?" + c.rawQuery);
        Request received = new Request.Builder()
                .url(url)
                .post(RequestBody.create(null, new byte[0]))
                .header(UpSigner.HEADER_CONTENT_SHA256, c.header("x-content-sha256"))
                .build();
        Request resigned = new UpSigner(AK, SK, null, REGION).sign(received, UpSignerTest.parseXDate(c.header("x-date")));
        assertEquals(resigned.header("Authorization"), c.header("authorization"), c.action() + ": signature");
    }

    private static void assertPayloadHashMatchesBody(Captured c) {
        assertEquals(UpSigner.sha256Hex(c.body), c.header("x-content-sha256"), c.action() + ": payload hash");
    }

    private void handle(HttpExchange exchange) throws IOException {
        Captured c = new Captured(exchange);
        captured.add(c);
        String action = c.action();
        if ("Download".equals(action) && truncateDownload) {
            replyTruncated(exchange, downloadBody);
            return;
        }
        if ("Download".equals(action)) {
            reply(exchange, downloadStatus, "application/octet-stream", downloadBody);
            return;
        }
        if ("Delete".equals(action) && new String(c.body, StandardCharsets.UTF_8).contains("\"fail\"")) {
            String failed = "{\"ResponseMetadata\":{\"RequestId\":\"req-1\",\"Action\":\"Delete\","
                    + "\"Version\":\"2023-08-01\",\"Error\":{\"Code\":\"InternalError\",\"Message\":\"boom\"}}}";
            reply(exchange, 500, "application/json", failed.getBytes(StandardCharsets.UTF_8));
            return;
        }
        String result;
        if ("UploadRaw".equals(action)) {
            result = "{\"Path\":\"temp/uploaded\",\"Sha256\":\"" + c.header("x-content-sha256") + "\"}";
        } else if ("LongLive".equals(action)) {
            result = "{\"Path\":\"temp/long\"}";
        } else if ("DownloadKey".equals(action)) {
            result = "{\"Key\":\"download-key\"}";
        } else {
            result = "{}";
        }
        reply(exchange, 200, "application/json", envelope(action, result).getBytes(StandardCharsets.UTF_8));
    }

    private static String envelope(String action, String result) {
        return "{\"ResponseMetadata\":{\"RequestId\":\"req-1\",\"Action\":\"" + action
                + "\",\"Version\":\"2023-08-01\"},\"Result\":" + result + "}";
    }

    private static void reply(HttpExchange exchange, int status, String contentType, byte[] body) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(status, body.length == 0 ? -1 : body.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(body);
        }
    }

    /** Promises the whole body in Content-Length but sends only half of it. */
    private static void replyTruncated(HttpExchange exchange, byte[] body) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/octet-stream");
        exchange.sendResponseHeaders(200, body.length);
        OutputStream out = exchange.getResponseBody();
        out.write(body, 0, body.length / 2);
        out.flush();
        try {
            out.close();
        } catch (IOException expected) {
            // "insufficient bytes written": the short body is the point of this reply.
        }
    }

    private static byte[] binaryContent(int size) {
        byte[] bytes = new byte[size];
        new Random(42).nextBytes(bytes);
        // Guarantee sequences that are not valid UTF-8 regardless of the random fill.
        byte[] invalidUtf8 = {(byte) 0xff, (byte) 0xd8, (byte) 0xc3, (byte) 0x28, (byte) 0x80, (byte) 0xfe};
        System.arraycopy(invalidUtf8, 0, bytes, 0, Math.min(invalidUtf8.length, size));
        return bytes;
    }

    private static List<String> listNames(Path dir) throws IOException {
        try (Stream<Path> files = Files.list(dir)) {
            return files.map(p -> p.getFileName().toString()).sorted().collect(Collectors.toList());
        }
    }

    private static final class Captured {
        final String method;
        final String rawPath;
        final String rawQuery;
        final Map<String, String> headers = new HashMap<>();
        final byte[] body;

        Captured(HttpExchange exchange) throws IOException {
            method = exchange.getRequestMethod();
            rawPath = exchange.getRequestURI().getRawPath();
            rawQuery = exchange.getRequestURI().getRawQuery();
            for (Map.Entry<String, List<String>> e : exchange.getRequestHeaders().entrySet()) {
                headers.put(e.getKey().toLowerCase(Locale.ROOT), e.getValue().get(0));
            }
            body = readAll(exchange.getRequestBody());
        }

        String header(String name) {
            return headers.get(name);
        }

        String action() {
            List<String> action = query().get("Action");
            return action == null ? "?" : action.get(0);
        }

        /** Decodes the query the way the server's form parser does. */
        Map<String, List<String>> query() {
            Map<String, List<String>> params = new LinkedHashMap<>();
            if (rawQuery == null) {
                return params;
            }
            for (String pair : rawQuery.split("&")) {
                int eq = pair.indexOf('=');
                String name = decode(eq < 0 ? pair : pair.substring(0, eq));
                String value = eq < 0 ? "" : decode(pair.substring(eq + 1));
                params.computeIfAbsent(name, k -> new ArrayList<>()).add(value);
            }
            return params;
        }

        private static String decode(String s) {
            try {
                return URLDecoder.decode(s, StandardCharsets.UTF_8.name());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        private static byte[] readAll(InputStream in) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            return out.toByteArray();
        }
    }
}
