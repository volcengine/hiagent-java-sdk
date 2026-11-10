package com.volcengine.hiagent.api;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pins {@link UpSigner} to vectors produced offline by UP's own signature
 * verifier, so the SDK and the server cannot drift on canonicalisation.
 */
class UpSignerTest {
    private static final String VECTORS = "/up-signature-vectors.json";
    private static final DateTimeFormatter X_DATE = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

    @Test
    void reproducesEveryServerVector() throws IOException {
        VectorFile file = loadVectors();
        assertFalse(file.vectors.isEmpty(), "vector file has no vectors");
        UpSigner signer = new UpSigner(file.accessKey, file.secretKey, null, file.region);

        for (Vector v : file.vectors) {
            Request unsigned = unsignedRequest(v.url, v.contentSha256);

            assertEquals(v.canonicalRequest,
                    UpSigner.canonicalRequest(unsigned.method(), unsigned.httpUrl(), v.contentSha256, v.xDate),
                    v.name + ": canonical request");
            assertEquals(v.stringToSign,
                    UpSigner.stringToSign(v.xDate, file.region, v.canonicalRequest),
                    v.name + ": string to sign");

            Request signed = signer.sign(unsigned, parseXDate(v.xDate));
            String scope = v.xDate.substring(0, 8) + "/" + file.region + "/" + file.service + "/request";
            assertEquals("HMAC-SHA256 Credential=" + file.accessKey + "/" + scope
                            + ", SignedHeaders=" + v.signedHeaders + ", Signature=" + v.signature,
                    signed.header("Authorization"), v.name + ": authorization");
            assertEquals(v.xDate, signed.header("X-Date"), v.name + ": x-date");
        }
    }

    @Test
    void refusesToSignWithoutPayloadHash() {
        UpSigner signer = new UpSigner("ak", "sk", null, "cn-north-1");
        Request request = new Request.Builder()
                .url("http://127.0.0.1:31010/?Action=Delete&Version=2023-08-01")
                .post(RequestBody.create(null, new byte[0]))
                .build();

        assertThrows(IllegalArgumentException.class, () -> signer.sign(request, Instant.now()));
    }

    @Test
    void sendsSessionTokenWithoutSigningIt() throws IOException {
        VectorFile file = loadVectors();
        Vector v = file.vectors.get(0);
        UpSigner withToken = new UpSigner(file.accessKey, file.secretKey, "sts-token", file.region);
        UpSigner withoutToken = new UpSigner(file.accessKey, file.secretKey, null, file.region);
        Request unsigned = unsignedRequest(v.url, v.contentSha256);

        Request signed = withToken.sign(unsigned, parseXDate(v.xDate));

        assertEquals("sts-token", signed.header("X-Security-Token"));
        assertEquals(withoutToken.sign(unsigned, parseXDate(v.xDate)).header("Authorization"),
                signed.header("Authorization"));
        assertNull(withoutToken.sign(unsigned, parseXDate(v.xDate)).header("X-Security-Token"));
    }

    @Test
    void rejectsMissingCredentialsWithoutEchoingThem() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new UpSigner("ak", "", null, "cn-north-1"));
        assertFalse(e.getMessage().contains("ak"), "message must not echo credentials");
        assertThrows(IllegalArgumentException.class, () -> new UpSigner("ak", "sk", null, " "));
    }

    @Test
    void credentialsOkHttpCannotSendFailWithoutEchoingThem() {
        // OkHttp refuses header chars <= 0x1f and >= 0x7f, and quotes the whole value when it does.
        String[][] accessKeyAndToken = {
                {"secret-ak\n", null},
                {"AKLTsecreté", null},
                {"ak", "secret\r\ntoken"},
                {"ak", "secret-tokén"},
        };
        for (String[] c : accessKeyAndToken) {
            UpSigner signer = new UpSigner(c[0], "sk", c[1], "cn-north-1");

            IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                    () -> signer.sign(signableRequest(), Instant.now()));

            assertFalse(e.getMessage().contains("secret"), "message must not echo credentials");
            assertNull(e.getCause(), "OkHttp's exception quotes the header value and must not be chained");
        }
    }

    @Test
    void trimsATrailingNewlineFromTheSessionToken() {
        // A token read from a file keeps its trailing newline; that is not a reason to fail.
        Request signed = new UpSigner("ak", "sk", "sts-token\r\n", "cn-north-1")
                .sign(signableRequest(), Instant.now());

        assertEquals("sts-token", signed.header("X-Security-Token"));
    }

    private static Request signableRequest() {
        return unsignedRequest("http://127.0.0.1:31010/?Action=Delete&Version=2023-08-01",
                "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
    }

    @Test
    void encodesLikeTheServer() {
        assertEquals("a%20b%2Bc%2A~-_.%21%27%28%29%2F%E4%B8%AD", UpSigner.signEncode("a b+c*~-_.!'()/中"));
        assertEquals("Action=X&Version=1&path=a%20b&sha256=",
                UpSigner.canonicalQueryString("path=a+b&Version=1&Action=X&sha256"));
    }

    static Request unsignedRequest(String url, String contentSha256) {
        return new Request.Builder()
                .url(url)
                .post(RequestBody.create(null, new byte[0]))
                .header("X-Content-Sha256", contentSha256)
                .build();
    }

    static Instant parseXDate(String xDate) {
        return LocalDateTime.parse(xDate, X_DATE).toInstant(ZoneOffset.UTC);
    }

    static VectorFile loadVectors() throws IOException {
        try (InputStream in = UpSignerTest.class.getResourceAsStream(VECTORS)) {
            if (in == null) {
                throw new IOException("missing test resource " + VECTORS);
            }
            try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                return new Gson().fromJson(reader, VectorFile.class);
            }
        }
    }

    static final class VectorFile {
        String accessKey;
        String secretKey;
        String region;
        String service;
        List<Vector> vectors;
    }

    static final class Vector {
        String name;
        String url;
        String xDate;
        String contentSha256;
        String signedHeaders;
        String canonicalRequest;
        String stringToSign;
        String signature;
    }
}
