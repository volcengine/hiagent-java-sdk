package com.volcengine.hibot.internal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volcengine.hibot.ApiException;
import com.volcengine.hibot.HibotConfig;

import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Signs, sends, and decodes TOP Action requests.
 *
 * <p>Mirrors go/hibot/internal/request/request.go.
 */
public final class RequestExecutor {
    private static final ObjectMapper MAPPER = ResponseDecoder.mapper();

    private final String endpoint;
    private final String accessKey;
    private final String secretKey;
    private final String workspaceId;
    private final String region;
    private final HttpClient httpClient;

    public RequestExecutor(String endpoint, String accessKey, String secretKey,
                           String workspaceId, String region, HttpClient httpClient) {
        this.endpoint = endpoint;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.workspaceId = workspaceId;
        this.region = region;
        this.httpClient = httpClient;
    }

    public RequestExecutor(HibotConfig config) {
        this(config.endpoint(), config.accessKey(), config.secretKey(),
                config.workspaceId(), config.region(), config.httpClient());
    }

    public static final class Action {
        public final String service;
        public final String version;
        public final String action;
        public final Object body;
        public boolean stream;

        public Action(String service, String version, String action, Object body) {
            this.service = service;
            this.version = version;
            this.action = action;
            this.body = body;
        }
    }

    /** Send a JSON Action request and decode the wrapped Result. */
    public <T> T doAction(Action req, TypeReference<T> resultType) {
        byte[] body = marshalActionBody(req.body);
        HttpRequest httpRequest = buildHttpRequest(req, body, "application/json", null);
        HttpResponse<byte[]> resp;
        try {
            resp = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
        } catch (Exception e) {
            throw new RuntimeException("hibot: send request: " + e.getMessage(), e);
        }
        return ResponseDecoder.decode(resp.statusCode(), resp.body(), resultType);
    }

    /** Send a raw (non-JSON-encoded) Action request — used for UploadBlob. */
    public <T> T doRawAction(Action req, byte[] body, String contentType,
                             Map<String, String> extraQuery, TypeReference<T> resultType) {
        HttpRequest httpRequest = buildHttpRequest(req, body == null ? new byte[0] : body, contentType, extraQuery);
        HttpResponse<byte[]> resp;
        try {
            resp = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
        } catch (Exception e) {
            throw new RuntimeException("hibot: send request: " + e.getMessage(), e);
        }
        return ResponseDecoder.decode(resp.statusCode(), resp.body(), resultType);
    }

    /** Send a streaming Action request (text/event-stream) and return the raw response. */
    public HttpResponse<InputStream> doStream(Action req) {
        byte[] body = marshalActionBody(req.body);
        req.stream = true;
        HttpRequest httpRequest = buildHttpRequest(req, body, "application/json", null);
        try {
            // For streaming we want no client-level timeout to prevent SSE getting cut off.
            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
        } catch (Exception e) {
            throw new RuntimeException("hibot: send stream request: " + e.getMessage(), e);
        }
    }

    private HttpRequest buildHttpRequest(Action req, byte[] body, String contentType, Map<String, String> extraQuery) {
        StringBuilder url = new StringBuilder(endpoint);
        // Strip trailing slash on endpoint.
        while (url.length() > 0 && url.charAt(url.length() - 1) == '/') {
            url.deleteCharAt(url.length() - 1);
        }
        // The TOP gateway exposes the up service at /up sub-path; the root path
        // does not accept up Actions. (Mirrors go/hibot/internal/request.)
        if ("up".equals(req.service)) {
            url.append("/up");
        }
        url.append("?Action=").append(URLEncoder.encode(req.action, StandardCharsets.UTF_8));
        url.append("&Version=").append(URLEncoder.encode(req.version, StandardCharsets.UTF_8));
        if (extraQuery != null) {
            for (Map.Entry<String, String> e : extraQuery.entrySet()) {
                url.append('&')
                        .append(URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8))
                        .append('=')
                        .append(URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8));
            }
        }

        URI uri = URI.create(url.toString());
        String ct = contentType == null || contentType.isEmpty() ? "application/octet-stream" : contentType;

        // Signing
        Map<String, String> headersForSign = new LinkedHashMap<>();
        headersForSign.put("content-type", ct);
        Signer signer = new Signer(accessKey, secretKey, region, req.service);
        Signer.Signed signed = signer.sign("POST", uri, headersForSign, body, null);

        HttpRequest.Builder b = HttpRequest.newBuilder(uri)
                .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                .header("Content-Type", ct)
                .header("X-Top-Service", req.service)
                .header("X-Date", signed.xDate)
                .header("X-Content-Sha256", signed.xContentSha256)
                .header("Authorization", signed.authorization);
        if (req.stream) {
            b.header("Accept", "text/event-stream");
            b.timeout(Duration.ofMinutes(60));
        } else {
            b.timeout(Duration.ofSeconds(60));
        }
        return b.build();
    }

    @SuppressWarnings("unchecked")
    private byte[] marshalActionBody(Object v) {
        Map<String, Object> body;
        if (v == null) {
            body = new LinkedHashMap<>();
        } else if (v instanceof Map) {
            body = new LinkedHashMap<>((Map<String, Object>) v);
        } else {
            body = MAPPER.convertValue(v, new TypeReference<Map<String, Object>>() {});
            if (body == null) body = new LinkedHashMap<>();
        }
        injectWorkspace(body);
        try {
            return MAPPER.writeValueAsBytes(body);
        } catch (Exception e) {
            throw new RuntimeException("hibot: encode request: " + e.getMessage(), e);
        }
    }

    private void injectWorkspace(Map<String, Object> body) {
        if (workspaceId == null || workspaceId.isEmpty()) return;
        Object existing = body.get("WorkspaceID");
        if (existing == null || (existing instanceof String && ((String) existing).isEmpty())) {
            body.put("WorkspaceID", workspaceId);
        }
    }

    /** Convert the body of a non-2xx streaming response into an ApiException. */
    public static ApiException toApiException(HttpResponse<?> resp, byte[] body) {
        return new ApiException(resp.statusCode(), "", "", new String(body, StandardCharsets.UTF_8));
    }
}
