package com.volcengine.hibot.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.volcengine.hibot.Hibot;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.ApiException;
import com.volcengine.hibot.testutil.MockHibotServer;
import com.volcengine.hibot.v1.types.V1Message;
import com.volcengine.hibot.v1.types.V1MessageFile;
import com.volcengine.hibot.v1.types.V1SessionChatParams;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 验证 chat 同步聚合契约：
 *   1. 非流式 chat 必须发出 {@code Stream=false}，并默认带 {@code Approve="all"}；
 *   2. 流式 chatStreaming 不发 {@code Stream}/{@code Approve}；
 *   3. Files 与空 Content 在两条路径上都能透传。
 */
class ChatFilesApproveTest {
    private MockHibotServer server;
    private Hibot client;

    @BeforeEach
    void setUp() throws Exception {
        server = new MockHibotServer();
        client = server.newClient();
    }

    @AfterEach
    void tearDown() throws Exception {
        client.close();
        server.close();
    }

    @Test
    void chat_nonStreaming_sendsStreamFalseAndDefaultsApproveAll() throws Exception {
        server.onRequest((rec, ex) -> {
            try {
                if ("Chat".equals(rec.action())) {
                    syncChat(ex, "ok");
                } else {
                    MockHibotServer.writeOk(ex, new LinkedHashMap<>());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        V1SessionChatParams p = new V1SessionChatParams();
        p.agentId = "ag";
        p.input = "hi";
        V1Message m = client.v1.sessions.chat("ses", p);
        assertEquals("ok", m.content);
        JsonNode body = lastChatBody();
        assertNotNull(body.get("Stream"), "非流式 chat 必须发出 Stream 字段");
        assertFalse(body.get("Stream").asBoolean(), "非流式 chat 必须发出 Stream=false");
        assertEquals("all", body.get("Approve").asText(),
                "非流式 chat 在调用方未显式指定 approve 时应默认下发 \"all\"");
    }

    @Test
    void chat_nonStreamingDoesNotRetryDuplicateClientMessageId() throws Exception {
        AtomicInteger chatCalls = new AtomicInteger();
        server.onRequest((rec, ex) -> {
            try {
                if ("Chat".equals(rec.action())) {
                    chatCalls.incrementAndGet();
                    MockHibotServer.writeJson(ex, 500,
                            "{\"ResponseMetadata\":{\"Error\":{\"Code\":\"InternalError\",\"Message\":\"boom\"}}}");
                } else {
                    MockHibotServer.writeOk(ex, new LinkedHashMap<>());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        V1SessionChatParams p = new V1SessionChatParams();
        p.agentId = "ag";
        p.input = "hi";
        p.clientMessageId = "client-message-id";

        assertThrows(ApiException.class, () -> client.v1.sessions.chat("ses", p));
        assertEquals(1, chatCalls.get(), "non-streaming chat must not retry non-idempotent sends");
    }

    @Test
    void chat_nonStreamingUsesLongReadTimeout() throws Exception {
        client.close();
        client = new Hibot(HibotConfig.builder()
                .endpoint(server.baseUrl())
                .accessKey("AK")
                .secretKey("SK")
                .workspaceId("ws-test")
                .region("cn-north-1")
                .httpClient(new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(100, TimeUnit.MILLISECONDS)
                        .build())
                .streamReadTimeoutSeconds(2)
                .build());

        server.onRequest((rec, ex) -> {
            try {
                if ("Chat".equals(rec.action())) {
                    Thread.sleep(300);
                    syncChat(ex, "ok-after-delay");
                } else {
                    MockHibotServer.writeOk(ex, new LinkedHashMap<>());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });

        V1SessionChatParams p = new V1SessionChatParams();
        p.agentId = "ag";
        p.input = "hi";

        V1Message m = client.v1.sessions.chat("ses", p);
        assertEquals("ok-after-delay", m.content);
    }

    @Test
    void chatStreaming_doesNotInjectApproveOrStream() throws Exception {
        server.onRequest((rec, ex) -> {
            try {
                if ("Chat".equals(rec.action())) {
                    sseChat(ex);
                } else {
                    MockHibotServer.writeOk(ex, new LinkedHashMap<>());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        V1SessionChatParams p = new V1SessionChatParams();
        p.agentId = "ag";
        p.input = "hi";
        try (V1ChatStream stream = client.v1.sessions.chatStreaming("ses", p)) {
            while (stream.next()) {
                // drain
            }
        }
        JsonNode body = lastChatBody();
        assertFalse(body.has("Approve"), "streaming 不应自动注入 Approve");
        assertFalse(body.has("Stream"), "streaming 不应主动下发 Stream（服务端默认即流式）");
    }

    @Test
    void chat_supportsFilesAndEmptyContent() throws Exception {
        server.onRequest((rec, ex) -> {
            try {
                if ("Chat".equals(rec.action())) {
                    syncChat(ex, "");
                } else {
                    MockHibotServer.writeOk(ex, new LinkedHashMap<>());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        V1MessageFile f = new V1MessageFile();
        f.name = "a.png";
        f.contentType = "image/png";
        f.blobId = "blob-123";
        List<V1MessageFile> files = new ArrayList<>();
        files.add(f);

        V1SessionChatParams p = new V1SessionChatParams();
        p.agentId = "ag";
        p.input = ""; // 空 content
        p.files = files;
        client.v1.sessions.chat("ses", p);

        JsonNode body = lastChatBody();
        // 空 Content 也要发出去（允许仅传文件对话）
        assertNotNull(body.get("Content"));
        assertEquals("", body.get("Content").asText());
        JsonNode arr = body.get("Files");
        assertNotNull(arr, "Files 字段应被序列化");
        assertTrue(arr.isArray());
        assertEquals(1, arr.size());
        assertEquals("blob-123", arr.get(0).get("BlobID").asText());
        assertEquals("a.png", arr.get(0).get("Name").asText());
        assertEquals("image/png", arr.get(0).get("ContentType").asText());
    }

    private JsonNode lastChatBody() {
        for (int i = server.recorded().size() - 1; i >= 0; i--) {
            MockHibotServer.RecordedRequest r = server.recorded().get(i);
            if ("Chat".equals(r.action())) {
                return r.bodyJson();
            }
        }
        throw new IllegalStateException("no Chat request recorded");
    }

    /** 同步聚合分支：返回 {@code ChatSyncResponse{Message}} 普通 JSON。 */
    private static void syncChat(com.sun.net.httpserver.HttpExchange ex, String content) throws IOException {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("Message", content);
        MockHibotServer.writeOk(ex, result);
    }

    private static void sseChat(com.sun.net.httpserver.HttpExchange ex) throws IOException {
        ex.getResponseHeaders().add("Content-Type", "text/event-stream");
        ex.sendResponseHeaders(200, 0);
        try (OutputStream os = ex.getResponseBody()) {
            writeFrame(os, "message_completed",
                    "{\"message_id\":\"m-1\",\"content\":\"ok\"}");
        }
    }

    private static void writeFrame(OutputStream os, String event, String data) throws IOException {
        os.write(("event: " + event + "\n").getBytes(StandardCharsets.UTF_8));
        os.write(("data: " + data + "\n\n").getBytes(StandardCharsets.UTF_8));
        os.flush();
    }
}
