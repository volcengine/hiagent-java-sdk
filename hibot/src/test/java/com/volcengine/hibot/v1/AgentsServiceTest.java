package com.volcengine.hibot.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.volcengine.hibot.Hibot;
import com.volcengine.hibot.testutil.MockHibotServer;
import com.volcengine.hibot.v1.types.V1Agent;
import com.volcengine.hibot.v1.types.V1AgentNewParams;
import com.volcengine.hibot.v1.types.V1AgentNewParamsToolUnion;
import com.volcengine.hibot.v1.types.V1ManagedAgentMCPToolParams;
import com.volcengine.hibot.v1.types.V1ManagedAgentModelConfigParams;
import com.volcengine.hibot.v1.types.V1ManagedAgentResourceRefParams;
import com.volcengine.hibot.v1.types.V1ManagedAgentSkillToolParams;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AgentsServiceTest {
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
    void create_pullsDefaultEnvironment_andSendsExpectedBody() throws Exception {
        server.onRequest((rec, ex) -> {
            try {
                String action = rec.action();
                if ("ListEnv".equals(action)) {
                    Map<String, Object> result = new LinkedHashMap<>();
                    Map<String, Object> e1 = new LinkedHashMap<>();
                    e1.put("ID", "env-1");
                    e1.put("Name", "default");
                    e1.put("CreatedAt", "2025-01-01T00:00:00Z");
                    Map<String, Object> e2 = new LinkedHashMap<>();
                    e2.put("ID", "env-2");
                    e2.put("Name", "later");
                    e2.put("CreatedAt", "2025-06-01T00:00:00Z");
                    result.put("Items", Arrays.asList(e2, e1));
                    MockHibotServer.writeOk(ex, result);
                } else if ("CreateAgent".equals(action)) {
                    Map<String, Object> result = new LinkedHashMap<>();
                    result.put("ID", "agent-1");
                    MockHibotServer.writeOk(ex, result);
                } else {
                    MockHibotServer.writeOk(ex, new LinkedHashMap<>());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        V1AgentNewParams params = new V1AgentNewParams();
        params.name = "my-agent";
        params.model = new V1ManagedAgentModelConfigParams("model-1");
        params.system = "you are helpful";
        params.tools = new ArrayList<>();
        params.tools.add(V1AgentNewParamsToolUnion.ofSkill(new V1ManagedAgentSkillToolParams("skill", "skv-1")));
        V1ManagedAgentMCPToolParams mcp = new V1ManagedAgentMCPToolParams();
        mcp.id = "mcp-1";
        params.tools.add(V1AgentNewParamsToolUnion.ofMcp(mcp));
        params.resources = new ArrayList<>();
        V1ManagedAgentResourceRefParams r1 = new V1ManagedAgentResourceRefParams();
        r1.id = "res-1";
        params.resources.add(r1);
        V1ManagedAgentResourceRefParams r2 = new V1ManagedAgentResourceRefParams();
        r2.directoryId = "dir-1";
        params.resources.add(r2);

        V1Agent agent = client.v1.agents.create(params);
        assertEquals("agent-1", agent.id);
        assertEquals("my-agent", agent.name);
        assertEquals("model-1", agent.modelId);

        // Verify ListEnv came first, CreateAgent came second.
        List<MockHibotServer.RecordedRequest> reqs = server.recorded();
        assertEquals("ListEnv", reqs.get(0).action());
        MockHibotServer.RecordedRequest createReq = reqs.get(1);
        assertEquals("CreateAgent", createReq.action());
        assertEquals("hibot-server", createReq.topService);
        assertTrue(createReq.authorization.startsWith("HMAC-SHA256 "));

        JsonNode body = createReq.bodyJson();
        assertEquals("ws-test", body.get("WorkspaceID").asText());
        assertEquals("my-agent", body.get("Name").asText());
        assertEquals("model-1", body.get("ModelID").asText());
        assertEquals("env-1", body.get("EnvID").asText(), "should pick earliest CreatedAt as default env");
        assertEquals("you are helpful", body.get("SystemPrompt").asText());
        assertEquals("skv-1", body.get("Skills").get(0).get("ID").asText());
        assertEquals("mcp-1", body.get("MCPs").get(0).get("ID").asText());
        assertTrue(body.get("MCPs").get(0).get("Enabled").asBoolean());
        assertEquals("res-1", body.get("Resources").get("ResourceIDs").get(0).asText());
        assertEquals("dir-1", body.get("Resources").get("DirectoryIDs").get(0).asText());
    }

    @Test
    void create_skipsListEnvWhenEnvIdProvided() throws Exception {
        server.onRequest((rec, ex) -> {
            try {
                if ("CreateAgent".equals(rec.action())) {
                    Map<String, Object> result = new LinkedHashMap<>();
                    result.put("ID", "a-1");
                    MockHibotServer.writeOk(ex, result);
                } else {
                    MockHibotServer.writeOk(ex, new LinkedHashMap<>());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        V1AgentNewParams p = new V1AgentNewParams();
        p.name = "x";
        p.envId = "env-supplied";
        V1Agent a = client.v1.agents.create(p);
        assertEquals("a-1", a.id);
        assertEquals(1, server.recorded().size());
        assertEquals("CreateAgent", server.last().action());
        assertEquals("env-supplied", server.last().bodyJson().get("EnvID").asText());
    }

    @Test
    void list_returnsItems() throws Exception {
        server.onRequest((rec, ex) -> {
            try {
                Map<String, Object> result = new LinkedHashMap<>();
                Map<String, Object> a1 = new LinkedHashMap<>();
                a1.put("ID", "a1");
                a1.put("Name", "n1");
                Map<String, Object> a2 = new LinkedHashMap<>();
                a2.put("ID", "a2");
                a2.put("Name", "n2");
                result.put("Items", Arrays.asList(a1, a2));
                MockHibotServer.writeOk(ex, result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        List<V1Agent> got = client.v1.agents.list(null);
        assertEquals(2, got.size());
        assertEquals("a1", got.get(0).id);
        assertEquals("ListAgents", server.last().action());
    }

    @Test
    void delete_validatesAgentId() {
        Hibot c = client;
        try {
            com.volcengine.hibot.v1.types.V1AgentDeleteParams p = new com.volcengine.hibot.v1.types.V1AgentDeleteParams();
            c.v1.agents.delete(p);
            assertTrue(false, "expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected.getMessage());
        }
    }
}
