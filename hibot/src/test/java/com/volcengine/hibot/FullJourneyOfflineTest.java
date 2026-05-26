package com.volcengine.hibot;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.net.httpserver.HttpExchange;
import com.volcengine.hibot.testutil.MockHibotServer;
import com.volcengine.hibot.v1.V1ChatStream;
import com.volcengine.hibot.v1.V1Constants;
import com.volcengine.hibot.v1.types.V1Agent;
import com.volcengine.hibot.v1.types.V1AgentNewParams;
import com.volcengine.hibot.v1.types.V1AgentNewParamsToolUnion;
import com.volcengine.hibot.v1.types.V1CredentialRefParams;
import com.volcengine.hibot.v1.types.V1MCP;
import com.volcengine.hibot.v1.types.V1MCPNewParams;
import com.volcengine.hibot.v1.types.V1ManagedAgentMCPToolParams;
import com.volcengine.hibot.v1.types.V1ManagedAgentModelConfigParams;
import com.volcengine.hibot.v1.types.V1ManagedAgentResourceRefParams;
import com.volcengine.hibot.v1.types.V1ManagedAgentSkillToolParams;
import com.volcengine.hibot.v1.types.V1Message;
import com.volcengine.hibot.v1.types.V1Model;
import com.volcengine.hibot.v1.types.V1ModelGetParams;
import com.volcengine.hibot.v1.types.V1Prompt;
import com.volcengine.hibot.v1.types.V1PromptNewParams;
import com.volcengine.hibot.v1.types.V1Resource;
import com.volcengine.hibot.v1.types.V1ResourceNewParams;
import com.volcengine.hibot.v1.types.V1Session;
import com.volcengine.hibot.v1.types.V1SessionChatEvent;
import com.volcengine.hibot.v1.types.V1SessionChatParams;
import com.volcengine.hibot.v1.types.V1SessionNewParams;
import com.volcengine.hibot.v1.types.V1SkillNewParams;
import com.volcengine.hibot.v1.types.V1SkillVersion;
import com.volcengine.hibot.v1.types.V1UploadBlob;
import com.volcengine.hibot.v1.types.V1UploadBlobParams;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * 端到端离线测试，对齐 Go 仓库的 examples/e2e/e2e_test.go::TestFullJourney_StreamingAndBatch。
 *
 * <p>11 步闭环：GetModel → CreateAgentPromptTemplate → UploadBlob×2 → CreateSkill →
 * CreateResource → CreateMCP →（隐式 ListEnv）→ CreateAgent → CreateSession →
 * Chat (streaming) → Chat (batch)。
 */
class FullJourneyOfflineTest {
    private MockHibotServer server;
    private Hibot client;

    @BeforeEach
    void setUp() throws Exception {
        server = new MockHibotServer();
        client = server.newClient();
        server.onRequest((rec, ex) -> {
            try {
                routeAction(rec, ex);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @AfterEach
    void tearDown() throws Exception {
        client.close();
        server.close();
    }

    @Test
    void fullJourney_streamingAndBatch() throws Exception {
        // --- Step 1: GetModel ---
        V1ModelGetParams modelParams = new V1ModelGetParams();
        modelParams.id = V1Constants.V1_MANAGED_AGENT_MODEL_DOUBAO_SEED_PRO;
        V1Model model = client.v1.models.get(modelParams);
        assertNotNull(model);
        assertEquals(V1Constants.V1_MANAGED_AGENT_MODEL_DOUBAO_SEED_PRO, model.id);

        // --- Step 2: CreateAgentPromptTemplate ---
        V1PromptNewParams promptParams = new V1PromptNewParams();
        promptParams.name = "e2e-prompt";
        promptParams.content = "你是一个 SDK 端到端测试中的助手。";
        V1Prompt prompt = client.v1.prompts.create(promptParams);
        assertNotNull(prompt.id);
        assertEquals(promptParams.content, prompt.content);

        // --- Step 3: UploadBlob × 2 ---
        V1UploadBlob skillBlob = client.v1.uploads.uploadBlob(
                new V1UploadBlobParams("skill.zip", "application/zip"),
                "PK\u0003\u0004skill-bytes".getBytes(StandardCharsets.UTF_8));
        assertNotNull(skillBlob.blobId);
        V1UploadBlob resourceBlob = client.v1.uploads.uploadBlob(
                new V1UploadBlobParams("runbook.md", "text/markdown"),
                "# runbook\nstep 1\n".getBytes(StandardCharsets.UTF_8));
        assertNotNull(resourceBlob.blobId);

        // --- Step 4: CreateSkill ---
        V1SkillNewParams skillParams = new V1SkillNewParams();
        skillParams.name = "e2e-skill";
        skillParams.description = "skill registered by e2e test";
        skillParams.blobId = skillBlob.blobId;
        skillParams.enabled = Boolean.TRUE;
        skillParams.version = "1.0.0";
        V1SkillVersion skill = client.v1.skills.create(skillParams);
        assertNotNull(skill.id);

        // --- Step 5: CreateResource ---
        V1ResourceNewParams resourceParams = new V1ResourceNewParams();
        resourceParams.name = "e2e-resource";
        resourceParams.type = V1Constants.V1_RESOURCE_TYPE_DOCUMENT_COLLECTION;
        resourceParams.blobId = resourceBlob.blobId;
        V1Resource resource = client.v1.resources.create(resourceParams);
        assertNotNull(resource.id);

        // --- Step 6: CreateMCP ---
        V1MCPNewParams mcpParams = new V1MCPNewParams();
        mcpParams.name = "e2e-mcp";
        mcpParams.transport = V1Constants.V1_MCP_TRANSPORT_STREAMABLE_HTTP;
        mcpParams.endpoint = "http://mcp.local/mcp";
        mcpParams.credential = new V1CredentialRefParams("e2e-token");
        V1MCP mcp = client.v1.mcps.create(mcpParams);
        assertNotNull(mcp.id);

        // --- Step 7: CreateAgent (no envId → triggers ListEnv default lookup) ---
        V1AgentNewParams agentParams = new V1AgentNewParams();
        agentParams.name = "e2e-agent";
        agentParams.model = new V1ManagedAgentModelConfigParams(model.id);
        agentParams.system = prompt.content;
        agentParams.tools = new ArrayList<>();
        agentParams.tools.add(V1AgentNewParamsToolUnion.ofSkill(
                new V1ManagedAgentSkillToolParams(
                        V1Constants.V1_MANAGED_AGENT_SKILL_TOOL_PARAMS_TYPE_SKILL, skill.id)));
        agentParams.tools.add(V1AgentNewParamsToolUnion.ofMcp(
                new V1ManagedAgentMCPToolParams(
                        V1Constants.V1_MANAGED_AGENT_MCP_TOOL_PARAMS_TYPE_MCP, mcp.id)));
        agentParams.resources = new ArrayList<>();
        agentParams.resources.add(V1ManagedAgentResourceRefParams.ofResource(resource.id));
        V1Agent agent = client.v1.agents.create(agentParams);
        assertNotNull(agent.id);

        // --- Step 8: CreateSession (omit peer; SDK falls back to webchat default) ---
        V1SessionNewParams sessionParams = new V1SessionNewParams();
        sessionParams.agentId = agent.id;
        V1Session session = client.v1.sessions.create(sessionParams);
        assertNotNull(session.id);

        // --- Step 9: Chat streaming ---
        V1SessionChatParams streamParams = new V1SessionChatParams();
        streamParams.agentId = agent.id;
        streamParams.input = "流式：请用一句话介绍自己。";
        boolean sawDelta = false;
        boolean sawCompleted = false;
        V1Message streamingFinal = null;
        try (V1ChatStream stream = client.v1.sessions.chatStreaming(session.id, streamParams)) {
            while (stream.next()) {
                V1SessionChatEvent ev = stream.current();
                if (V1Constants.V1_SESSION_CHAT_EVENT_DELTA.equals(ev.type)) {
                    sawDelta = true;
                } else if (V1Constants.V1_SESSION_CHAT_EVENT_COMPLETED.equals(ev.type)) {
                    sawCompleted = true;
                }
            }
            assertEquals(null, stream.err());
            streamingFinal = stream.finalMessage();
        }
        assertTrue(sawDelta, "streaming should observe at least one delta event");
        assertTrue(sawCompleted, "streaming should observe a completed event");
        assertNotNull(streamingFinal);
        assertEquals("message-1", streamingFinal.id);
        assertNotNull(streamingFinal.content);
        assertTrue(!streamingFinal.content.isEmpty());

        // --- Step 10: Chat (batch / non-streaming) reuses the same session ---
        V1SessionChatParams batchParams = new V1SessionChatParams();
        batchParams.input = "批量：再回答一次同样的问题。";
        V1Message batchFinal = client.v1.sessions.chat(session.id, batchParams);
        assertNotNull(batchFinal.id);
        assertNotNull(batchFinal.content);
        assertTrue(!batchFinal.content.isEmpty());

        // --- Step 11: Inspect recorded requests for per-Action contract ---
        // Mirror the Go mocktop semantics: bodies map is keyed by action and
        // overwritten on each request, so per-Action assertions look at the
        // *last* body seen for that action (matters for Chat where streaming
        // is first and batch is second).
        Set<String> seenActions = new HashSet<>();
        Map<String, MockHibotServer.RecordedRequest> lastByAction = new LinkedHashMap<>();
        for (MockHibotServer.RecordedRequest rec : server.recorded()) {
            String action = rec.action();
            seenActions.add(action);
            lastByAction.put(action, rec);
            // 全程 Authorization 必须以 HMAC-SHA256 开头。
            assertNotNull(rec.authorization, "authorization missing on " + action);
            assertTrue(rec.authorization.startsWith("HMAC-SHA256 "),
                    "authorization should start with HMAC-SHA256 for " + action
                            + " got=" + rec.authorization);
            // JSON 请求 body 顶层必须有 WorkspaceID（UploadBlob 是 raw 上传，不走 JSON 注入路径）。
            if (!"UploadBlob".equals(action)) {
                JsonNode body = rec.bodyJson();
                JsonNode ws = body.get("WorkspaceID");
                assertNotNull(ws, "WorkspaceID missing on " + action);
                assertEquals("ws-test", ws.asText(), "WorkspaceID mismatch on " + action);
            }
        }

        // CreateAgent 字段断言。
        JsonNode createAgentBody = lastByAction.get("CreateAgent").bodyJson();
        assertEquals(model.id, createAgentBody.get("ModelID").asText());
        assertEquals("env-1", createAgentBody.get("EnvID").asText(),
                "EnvID should default to env-1 from ListEnv result");
        assertTrue(createAgentBody.get("Skills").isArray(), "Skills should be an array");
        assertTrue(createAgentBody.get("MCPs").isArray(), "MCPs should be an array");
        assertTrue(createAgentBody.get("Resources").isObject(),
                "Resources should be an object (ResourceIDs/DirectoryIDs)");

        // CreateSession 字段断言。
        JsonNode createSessionBody = lastByAction.get("CreateSession").bodyJson();
        JsonNode payload = createSessionBody.get("Payload");
        assertNotNull(payload);
        assertEquals("webchat", payload.get("Channel").asText());
        assertEquals("system", payload.get("PeerKind").asText());
        assertEquals(agent.id, payload.get("PeerID").asText());

        // Chat 请求体断言（取最后一次 Chat —— 即批量调用，对齐 Go mocktop 行为）。
        JsonNode chatBody = lastByAction.get("Chat").bodyJson();
        assertEquals(session.id, chatBody.get("SessionID").asText());
        assertEquals(agent.id, chatBody.get("AgentID").asText());
        assertTrue(chatBody.get("Content").asText().contains("批量"),
                "last Chat call should be the batch call (containing '批量')");

        // UploadBlob 路径断言。
        boolean sawUploadBlob = false;
        for (MockHibotServer.RecordedRequest rec : server.recorded()) {
            if (!"UploadBlob".equals(rec.action())) continue;
            sawUploadBlob = true;
            assertEquals("/up", rec.path, "UploadBlob must POST to /up sub-path");
            assertEquals("up", rec.topService, "UploadBlob must use up top-service");
            assertTrue(rec.query.contains("Filename="),
                    "UploadBlob query must include Filename: " + rec.query);
        }
        assertTrue(sawUploadBlob, "expected at least one UploadBlob call");

        // 命中的 Action 集合至少包含整条闭环。
        for (String required : new String[]{
                "GetModel", "CreateAgentPromptTemplate", "UploadBlob", "CreateSkill",
                "CreateResource", "CreateMCP", "ListEnv", "CreateAgent", "CreateSession", "Chat"}) {
            assertTrue(seenActions.contains(required),
                    "action " + required + " was not observed; seen=" + seenActions);
        }
    }

    /** Routes a single recorded request to an Action-specific response. */
    private static void routeAction(MockHibotServer.RecordedRequest rec, HttpExchange ex) throws IOException {
        String action = rec.action();
        switch (action) {
            case "GetModel": {
                Map<String, Object> result = new LinkedHashMap<>();
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("ID", "doubao-seed-2.0-pro-260215");
                result.put("Items", java.util.Collections.singletonList(item));
                MockHibotServer.writeOk(ex, result);
                return;
            }
            case "CreateAgentPromptTemplate": {
                Map<String, Object> result = new LinkedHashMap<>();
                result.put("ID", "prompt-1");
                result.put("SystemPrompt", "你是一个 SDK 端到端测试中的助手。");
                MockHibotServer.writeOk(ex, result);
                return;
            }
            case "UploadBlob": {
                Map<String, Object> result = new LinkedHashMap<>();
                result.put("BlobID", "blob-" + java.util.UUID.randomUUID());
                MockHibotServer.writeOk(ex, result);
                return;
            }
            case "CreateSkill": {
                Map<String, Object> result = new LinkedHashMap<>();
                result.put("ID", "skill-version-1");
                MockHibotServer.writeOk(ex, result);
                return;
            }
            case "CreateResource": {
                Map<String, Object> result = new LinkedHashMap<>();
                result.put("ID", "resource-1");
                MockHibotServer.writeOk(ex, result);
                return;
            }
            case "CreateMCP": {
                Map<String, Object> result = new LinkedHashMap<>();
                result.put("ID", "mcp-1");
                MockHibotServer.writeOk(ex, result);
                return;
            }
            case "ListEnv": {
                Map<String, Object> result = new LinkedHashMap<>();
                Map<String, Object> env = new LinkedHashMap<>();
                env.put("ID", "env-1");
                env.put("Name", "default-env");
                env.put("ImageType", "hermes");
                env.put("CreatedAt", "2026-01-01T00:00:00Z");
                result.put("Items", java.util.Collections.singletonList(env));
                MockHibotServer.writeOk(ex, result);
                return;
            }
            case "CreateAgent": {
                Map<String, Object> result = new LinkedHashMap<>();
                result.put("ID", "agent-1");
                MockHibotServer.writeOk(ex, result);
                return;
            }
            case "CreateSession": {
                Map<String, Object> result = new LinkedHashMap<>();
                result.put("ID", "session-1");
                MockHibotServer.writeOk(ex, result);
                return;
            }
            case "Chat": {
                writeSseChat(ex);
                return;
            }
            default:
                fail("unexpected action: " + action);
        }
    }

    /** Writes the canonical Chat SSE response: one delta + one completed. */
    private static void writeSseChat(HttpExchange ex) throws IOException {
        ex.getResponseHeaders().add("Content-Type", "text/event-stream");
        ex.getResponseHeaders().add("Cache-Control", "no-cache");
        // 0 length signals chunked transfer-encoding for the JDK HttpServer.
        ex.sendResponseHeaders(200, 0);
        try (OutputStream os = ex.getResponseBody()) {
            writeSseFrame(os, "delta",
                    "{\"request_id\":\"req-test\",\"delta\":{\"text\":\"ok\"}}");
            writeSseFrame(os, "completed",
                    "{\"request_id\":\"req-test\",\"message\":{\"ID\":\"message-1\",\"Content\":\"ok\"}}");
        }
    }

    private static void writeSseFrame(OutputStream os, String event, String data) throws IOException {
        os.write(("event: " + event + "\n").getBytes(StandardCharsets.UTF_8));
        os.write(("data: " + data + "\n\n").getBytes(StandardCharsets.UTF_8));
        os.flush();
    }
}
