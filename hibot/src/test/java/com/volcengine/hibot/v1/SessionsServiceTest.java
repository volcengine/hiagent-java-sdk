package com.volcengine.hibot.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.volcengine.hibot.Hibot;
import com.volcengine.hibot.testutil.MockHibotServer;
import com.volcengine.hibot.v1.types.V1Message;
import com.volcengine.hibot.v1.types.V1Session;
import com.volcengine.hibot.v1.types.V1SessionChatEvent;
import com.volcengine.hibot.v1.types.V1SessionChatParams;
import com.volcengine.hibot.v1.types.V1SessionNewParams;
import com.volcengine.hibot.v1.types.V1SessionPeerParams;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SessionsServiceTest {
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
    void create_buildsPayloadWithDefaultsAndRecordsAgentId() throws Exception {
        server.onRequest((rec, ex) -> {
            try {
                Map<String, Object> result = new LinkedHashMap<>();
                result.put("ID", "ses-1");
                result.put("AgentID", "agent-x");
                MockHibotServer.writeOk(ex, result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        V1SessionNewParams p = new V1SessionNewParams();
        p.agentId = "agent-x";
        V1Session s = client.v1.sessions.create(p);
        assertEquals("ses-1", s.id);
        assertEquals("agent-x", s.agentId);
        // Verify body
        JsonNode body = server.last().bodyJson();
        assertEquals("agent-x", body.get("AgentID").asText());
        JsonNode payload = body.get("Payload");
        assertEquals("webchat", payload.get("Channel").asText());
        assertEquals("system", payload.get("PeerKind").asText());
        assertEquals("agent-x", payload.get("PeerID").asText());
    }

    @Test
    void create_withFeishuChannelOverridesPayload() throws Exception {
        server.onRequest((rec, ex) -> {
            try {
                Map<String, Object> result = new LinkedHashMap<>();
                result.put("ID", "ses-2");
                MockHibotServer.writeOk(ex, result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        V1SessionNewParams p = new V1SessionNewParams();
        p.agentId = "agent-x";
        p.peer = new V1SessionPeerParams("feishu", "user", "ou_xxx");
        client.v1.sessions.create(p);
        JsonNode payload = server.last().bodyJson().get("Payload");
        assertEquals("feishu", payload.get("Channel").asText());
        assertEquals("user", payload.get("PeerKind").asText());
        assertEquals("ou_xxx", payload.get("PeerID").asText());
    }

    @Test
    void chatStreaming_decodesEventsAndProducesFinalMessage() throws Exception {
        // First request: CreateSession.
        // Second request: Chat (streaming).
        server.onRequest((rec, ex) -> {
            try {
                if ("CreateSession".equals(rec.action())) {
                    Map<String, Object> result = new LinkedHashMap<>();
                    result.put("ID", "ses-1");
                    MockHibotServer.writeOk(ex, result);
                } else if ("Chat".equals(rec.action())) {
                    ex.getResponseHeaders().add("Content-Type", "text/event-stream");
                    ex.sendResponseHeaders(200, 0);
                    try (OutputStream os = ex.getResponseBody()) {
                        writeFrame(os, "message_delta", "{\"delta\":{\"text\":\"hello \"}}");
                        writeFrame(os, "message_delta", "{\"delta\":{\"text\":\"world\"}}");
                        writeFrame(os, "run_completed", "{\"message_id\":\"m-1\",\"content\":\"hello world\"}");
                    }
                } else {
                    MockHibotServer.writeOk(ex, new LinkedHashMap<>());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        V1SessionNewParams cp = new V1SessionNewParams();
        cp.agentId = "agent-x";
        V1Session ses = client.v1.sessions.create(cp);

        V1SessionChatParams params = new V1SessionChatParams();
        params.input = "hi";
        List<String> seen = new ArrayList<>();
        try (V1ChatStream stream = client.v1.sessions.chatStreaming(ses.id, params)) {
            while (stream.next()) {
                V1SessionChatEvent e = stream.current();
                seen.add(e.type);
            }
            assertEquals(null, stream.err());
            V1Message m = stream.finalMessage();
            assertEquals("m-1", m.id);
            assertEquals("hello world", m.content);
        }
        assertEquals(3, seen.size());
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_DELTA, seen.get(0));
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_DELTA, seen.get(1));
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_COMPLETED, seen.get(2));
    }

    @Test
    void chat_blockingReturnsFinalMessage() throws Exception {
        server.onRequest((rec, ex) -> {
            try {
                if ("Chat".equals(rec.action())) {
                    ex.getResponseHeaders().add("Content-Type", "text/event-stream");
                    ex.sendResponseHeaders(200, 0);
                    try (OutputStream os = ex.getResponseBody()) {
                        writeFrame(os, "message_delta", "{\"delta\":{\"text\":\"x\"}}");
                        writeFrame(os, "message_completed", "{\"message_id\":\"m-7\",\"content\":\"final\"}");
                    }
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
        V1Message m = client.v1.sessions.chat("ses-existing", p);
        assertEquals("m-7", m.id);
        assertEquals("final", m.content);
    }

    @Test
    void chatStreaming_accumulatesDeltaText() throws Exception {
        server.onRequest((rec, ex) -> {
            try {
                if ("Chat".equals(rec.action())) {
                    ex.getResponseHeaders().add("Content-Type", "text/event-stream");
                    ex.sendResponseHeaders(200, 0);
                    try (OutputStream os = ex.getResponseBody()) {
                        writeFrame(os, "message_delta", "{\"delta\":{\"text\":\"foo \"}}");
                        writeFrame(os, "message_delta", "{\"delta\":{\"text\":\"bar\"}}");
                        // No final message — accumulate fallback.
                    }
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
            V1Message m = stream.accumulate();
            assertEquals("assistant", m.role);
            assertEquals("foo bar", m.content);
        }
    }

    @Test
    void chatStreaming_returnsErrorOnHttpFailure() throws Exception {
        server.onRequest((rec, ex) -> {
            try {
                if ("Chat".equals(rec.action())) {
                    MockHibotServer.writeJson(ex, 500, "boom");
                } else {
                    MockHibotServer.writeOk(ex, new LinkedHashMap<>());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        V1SessionChatParams p = new V1SessionChatParams();
        p.agentId = "a";
        p.input = "hi";
        try (V1ChatStream stream = client.v1.sessions.chatStreaming("ses", p)) {
            assertTrue(!stream.next());
            assertNotNull(stream.err());
        }
    }

    private static void writeFrame(OutputStream os, String event, String data) throws IOException {
        os.write(("event: " + event + "\n").getBytes(StandardCharsets.UTF_8));
        os.write(("data: " + data + "\n\n").getBytes(StandardCharsets.UTF_8));
        os.flush();
    }
}
