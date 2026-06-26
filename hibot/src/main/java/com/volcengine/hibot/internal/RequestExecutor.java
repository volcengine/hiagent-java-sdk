package com.volcengine.hibot.internal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volcengine.hibot.ApiException;
import com.volcengine.hibot.HibotConfig;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Signs, sends, and decodes TOP Action requests.
 *
 * Mirrors go/hibot/internal/request/request.go.
 */
public final class RequestExecutor {
    private static final ObjectMapper MAPPER = ResponseDecoder.mapper();

    private final String endpoint;
    private final String accessKey;
    private final String secretKey;
    private final String workspaceId;
    private final String region;
    private final OkHttpClient httpClient;
    private final int maxRetries;
    private final long retryBaseDelayMs;
    private final long streamReadTimeoutSeconds;

    public RequestExecutor(String endpoint, String accessKey, String secretKey,
            String workspaceId, String region, OkHttpClient httpClient) {
        this(endpoint, accessKey, secretKey, workspaceId, region, httpClient,
                3, 500, 3600);
    }

    public RequestExecutor(String endpoint, String accessKey, String secretKey,
            String workspaceId, String region, OkHttpClient httpClient,
            int maxRetries, long retryBaseDelayMs, long streamReadTimeoutSeconds) {
        this.endpoint = endpoint;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.workspaceId = workspaceId;
        this.region = region;
        this.httpClient = httpClient;
        this.maxRetries = maxRetries;
        this.retryBaseDelayMs = retryBaseDelayMs;
        this.streamReadTimeoutSeconds = streamReadTimeoutSeconds;
    }

    public RequestExecutor(HibotConfig config) {
        this(config.endpoint(), config.accessKey(), config.secretKey(),
                config.workspaceId(), config.region(), config.httpClient(),
                config.maxRetries(), config.retryBaseDelayMs(), config.streamReadTimeoutSeconds());
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
        Request httpRequest = buildHttpRequest(req, body, "application/json", null);
        Exception lastException = null;
        for (int attempt = 0; attempt <= maxRetries; attempt++) {
            if (attempt > 0) {
                sleepRetryDelay(attempt);
            }
            try (Response resp = httpClient.newCall(httpRequest).execute()) {
                ResponseBody responseBody = resp.body();
                byte[] payload = responseBody == null ? new byte[0] : responseBody.bytes();
                if (isRetryableStatus(resp.code()) && attempt < maxRetries) {
                    continue;
                }
                return ResponseDecoder.decode(resp.code(), payload, resultType);
            } catch (ApiException e) {
                if (isRetryableStatus(e.statusCode()) && attempt < maxRetries) {
                    lastException = e;
                    continue;
                }
                throw e;
            } catch (Exception e) {
                if (attempt < maxRetries) {
                    lastException = e;
                    continue;
                }
                throw new RuntimeException("hibot: send request: " + e.getMessage(), e);
            }
        }
        if (lastException != null) {
            throw new RuntimeException("hibot: send request failed after " + maxRetries + " retries: "
                    + lastException.getMessage(), lastException);
        }
        throw new RuntimeException("hibot: send request failed after " + maxRetries + " retries");
    }

    /** Send a raw (non-JSON-encoded) Action request — used for UploadBlob. */
    public <T> T doRawAction(Action req, byte[] body, String contentType,
            Map<String, String> extraQuery, TypeReference<T> resultType) {
        Request httpRequest = buildHttpRequest(req, body == null ? new byte[0] : body, contentType, extraQuery);
        Exception lastException = null;
        for (int attempt = 0; attempt <= maxRetries; attempt++) {
            if (attempt > 0) {
                sleepRetryDelay(attempt);
            }
            try (Response resp = httpClient.newCall(httpRequest).execute()) {
                ResponseBody responseBody = resp.body();
                byte[] payload = responseBody == null ? new byte[0] : responseBody.bytes();
                if (isRetryableStatus(resp.code()) && attempt < maxRetries) {
                    continue;
                }
                return ResponseDecoder.decode(resp.code(), payload, resultType);
            } catch (ApiException e) {
                if (isRetryableStatus(e.statusCode()) && attempt < maxRetries) {
                    lastException = e;
                    continue;
                }
                throw e;
            } catch (Exception e) {
                if (attempt < maxRetries) {
                    lastException = e;
                    continue;
                }
                throw new RuntimeException("hibot: send request: " + e.getMessage(), e);
            }
        }
        if (lastException != null) {
            throw new RuntimeException("hibot: send request failed after " + maxRetries + " retries: "
                    + lastException.getMessage(), lastException);
        }
        throw new RuntimeException("hibot: send request failed after " + maxRetries + " retries");
    }

    /**
     * Send a streaming Action request (text/event-stream) and return the raw
     * response.
     */
    public Response doStream(Action req) {
        byte[] body = marshalActionBody(req.body);
        req.stream = true;
        Request httpRequest = buildHttpRequest(req, body, "application/json", null);
        Exception lastException = null;
        for (int attempt = 0; attempt <= maxRetries; attempt++) {
            if (attempt > 0) {
                sleepRetryDelay(attempt);
            }
            try {
                OkHttpClient streamClient = httpClient.newBuilder()
                        .readTimeout(streamReadTimeoutSeconds, TimeUnit.SECONDS)
                        .build();
                Response resp = streamClient.newCall(httpRequest).execute();
                if (isRetryableStatus(resp.code()) && attempt < maxRetries) {
                    resp.close();
                    continue;
                }
                return resp;
            } catch (Exception e) {
                if (attempt < maxRetries) {
                    lastException = e;
                    continue;
                }
                throw new RuntimeException("hibot: send stream request: " + e.getMessage(), e);
            }
        }
        if (lastException != null) {
            throw new RuntimeException("hibot: send stream request failed after " + maxRetries + " retries: "
                    + lastException.getMessage(), lastException);
        }
        throw new RuntimeException("hibot: send stream request failed after " + maxRetries + " retries");
    }

    private static boolean isRetryableStatus(int statusCode) {
        return statusCode == 429 || statusCode >= 500;
    }

    private void sleepRetryDelay(int attempt) {
        long delay = retryBaseDelayMs * (1L << (attempt - 1));
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private Request buildHttpRequest(Action req, byte[] body, String contentType, Map<String, String> extraQuery) {
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
        url.append("?Action=").append(encode(req.action));
        url.append("&Version=").append(encode(req.version));
        if (extraQuery != null) {
            for (Map.Entry<String, String> e : extraQuery.entrySet()) {
                url.append('&')
                        .append(encode(e.getKey()))
                        .append('=')
                        .append(encode(e.getValue()));
            }
        }

        URI uri = URI.create(url.toString());
        String ct = contentType == null || contentType.isEmpty() ? "application/octet-stream" : contentType;

        // Signing
        Map<String, String> headersForSign = new LinkedHashMap<>();
        headersForSign.put("content-type", ct);
        Signer signer = new Signer(accessKey, secretKey, region, req.service);
        Signer.Signed signed = signer.sign("POST", uri, headersForSign, body, null);

        RequestBody requestBody = RequestBody.create(MediaType.parse(ct), body);
        Request.Builder b = new Request.Builder()
                .url(url.toString())
                .post(requestBody)
                .header("Content-Type", ct)
                .header("X-Top-Service", req.service)
                .header("X-Date", signed.xDate)
                .header("X-Content-Sha256", signed.xContentSha256)
                .header("Authorization", signed.authorization);
        if (req.stream) {
            b.header("Accept", "text/event-stream");
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
            body = MAPPER.convertValue(v, new TypeReference<Map<String, Object>>() {
            });
            if (body == null)
                body = new LinkedHashMap<>();
        }
        injectWorkspace(body);
        try {
            return MAPPER.writeValueAsBytes(body);
        } catch (Exception e) {
            throw new RuntimeException("hibot: encode request: " + e.getMessage(), e);
        }
    }

    private void injectWorkspace(Map<String, Object> body) {
        if (workspaceId == null || workspaceId.isEmpty())
            return;
        Object existing = body.get("WorkspaceID");
        if (existing == null || (existing instanceof String && ((String) existing).isEmpty())) {
            body.put("WorkspaceID", workspaceId);
        }
    }

    /** Convert the body of a non-2xx streaming response into an ApiException. */
    public static ApiException toApiException(Response resp, byte[] body) {
        return new ApiException(resp.code(), "", "", new String(body, StandardCharsets.UTF_8));
    }

    private static String encode(String value) {
        if (value == null || value.isEmpty())
            return "";
        try {
            String encoded = URLEncoder.encode(value, StandardCharsets.UTF_8.name());
            return encoded.replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
        } catch (Exception e) {
            throw new IllegalStateException("UTF-8 encoding is not supported", e);
        }
    }
}
