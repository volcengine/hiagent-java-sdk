package com.volcengine.hibot.testutil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.volcengine.hibot.Hibot;
import com.volcengine.hibot.HibotConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

/** Lightweight in-process HTTP server to drive Hibot SDK against. */
public final class MockHibotServer implements AutoCloseable {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    private final HttpServer server;
    private final List<RecordedRequest> recorded = new ArrayList<>();
    private final AtomicReference<BiConsumer<RecordedRequest, HttpExchange>> handler = new AtomicReference<>();

    public MockHibotServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", this::dispatch);
        server.start();
    }

    public String baseUrl() {
        return "http://127.0.0.1:" + server.getAddress().getPort();
    }

    public List<RecordedRequest> recorded() {
        return recorded;
    }

    public RecordedRequest last() {
        if (recorded.isEmpty()) throw new IllegalStateException("no requests recorded");
        return recorded.get(recorded.size() - 1);
    }

    public void onRequest(BiConsumer<RecordedRequest, HttpExchange> h) {
        handler.set(h);
    }

    public Hibot newClient() {
        return new Hibot(HibotConfig.builder()
                .endpoint(baseUrl())
                .accessKey("AK")
                .secretKey("SK")
                .workspaceId("ws-test")
                .region("cn-north-1")
                .build());
    }

    private void dispatch(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        byte[] body;
        try (InputStream is = exchange.getRequestBody()) {
            body = is.readAllBytes();
        }
        RecordedRequest rec = new RecordedRequest(
                exchange.getRequestMethod(),
                uri.getPath(),
                uri.getQuery() == null ? "" : uri.getQuery(),
                exchange.getRequestHeaders().getFirst("X-Top-Service"),
                exchange.getRequestHeaders().getFirst("Authorization"),
                exchange.getRequestHeaders().getFirst("Content-Type"),
                body);
        recorded.add(rec);
        BiConsumer<RecordedRequest, HttpExchange> h = handler.get();
        if (h != null) {
            try {
                h.accept(rec, exchange);
                return;
            } catch (RuntimeException re) {
                writeJson(exchange, 500, "{\"ResponseMetadata\":{\"Error\":{\"Code\":\"InternalError\",\"Message\":\""
                        + re.getMessage().replace("\"", "\\\"") + "\"}}}");
                return;
            }
        }
        writeJson(exchange, 200, "{\"ResponseMetadata\":{\"RequestId\":\"r-" + UUID.randomUUID() + "\"},\"Result\":{}}");
    }

    public static void writeJson(HttpExchange exchange, int status, String body) throws IOException {
        byte[] data = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, data.length);
        exchange.getResponseBody().write(data);
        exchange.getResponseBody().close();
    }

    public static void writeOk(HttpExchange exchange, Object result) throws IOException {
        String resultJson = MAPPER.writeValueAsString(result == null ? new java.util.LinkedHashMap<>() : result);
        String body = "{\"ResponseMetadata\":{\"RequestId\":\"r-" + UUID.randomUUID() + "\"},\"Result\":" + resultJson + "}";
        writeJson(exchange, 200, body);
    }

    public static JsonNode parseJson(byte[] body) {
        try {
            return MAPPER.readTree(body);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        server.stop(0);
    }

    public static final class RecordedRequest {
        public final String method;
        public final String path;
        public final String query;
        public final String topService;
        public final String authorization;
        public final String contentType;
        public final byte[] body;

        RecordedRequest(String method, String path, String query, String topService,
                        String authorization, String contentType, byte[] body) {
            this.method = method;
            this.path = path;
            this.query = query;
            this.topService = topService;
            this.authorization = authorization;
            this.contentType = contentType;
            this.body = body;
        }

        public JsonNode bodyJson() {
            return parseJson(body);
        }

        public String action() {
            for (String pair : query.split("&")) {
                int eq = pair.indexOf('=');
                if (eq > 0 && "Action".equals(pair.substring(0, eq))) {
                    return pair.substring(eq + 1);
                }
            }
            return "";
        }

        public String version() {
            for (String pair : query.split("&")) {
                int eq = pair.indexOf('=');
                if (eq > 0 && "Version".equals(pair.substring(0, eq))) {
                    return pair.substring(eq + 1);
                }
            }
            return "";
        }
    }
}
