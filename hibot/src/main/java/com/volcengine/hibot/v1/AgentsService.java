package com.volcengine.hibot.v1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.internal.Bodies;
import com.volcengine.hibot.internal.RequestExecutor;
import com.volcengine.hibot.internal.Versions;
import com.volcengine.hibot.v1.types.V1Agent;
import com.volcengine.hibot.v1.types.V1AgentBatchGetParams;
import com.volcengine.hibot.v1.types.V1AgentDeleteParams;
import com.volcengine.hibot.v1.types.V1AgentGetParams;
import com.volcengine.hibot.v1.types.V1AgentListParams;
import com.volcengine.hibot.v1.types.V1AgentNewParams;
import com.volcengine.hibot.v1.types.V1AgentNewParamsToolUnion;
import com.volcengine.hibot.v1.types.V1AgentUpdateParams;
import com.volcengine.hibot.v1.types.V1Environment;
import com.volcengine.hibot.v1.types.V1EnvironmentListParams;
import com.volcengine.hibot.v1.types.V1ManagedAgentMCPToolParams;
import com.volcengine.hibot.v1.types.V1ManagedAgentResourceRefParams;
import com.volcengine.hibot.v1.types.V1ManagedAgentSkillToolParams;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Mirrors go/hibot/v1/agents.go. */
public final class AgentsService {
    private final RequestExecutor requester;
    private final HibotConfig config;
    private final EnvironmentsService environments;

    public AgentsService(RequestExecutor requester, HibotConfig config,
                         EnvironmentsService environments) {
        this.requester = requester;
        this.config = config;
        this.environments = environments;
    }

    public V1Agent create(V1AgentNewParams params) {
        if (params == null) params = new V1AgentNewParams();
        String envId = params.envId;
        if (isEmpty(envId)) {
            V1EnvironmentListParams elp = new V1EnvironmentListParams();
            elp.workspaceId = params.workspaceId;
            V1Environment env = environments.defaultEnvironment(elp);
            envId = env.id;
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        Bodies.putIfNotEmpty(body, "Name", params.name);
        if (params.model != null) {
            Bodies.putIfNotEmpty(body, "ModelID", params.model.id);
        }
        body.put("EnvID", envId);
        if (params.system != null) {
            body.put("SystemPrompt", params.system);
        }
        Map<String, Object> resources = buildResourceInput(params.resources);
        if (resources != null) {
            body.put("Resources", resources);
        }
        List<Map<String, Object>> skills = new ArrayList<>();
        List<Map<String, Object>> mcps = new ArrayList<>();
        if (params.tools != null) {
            for (V1AgentNewParamsToolUnion t : params.tools) {
                if (t.ofSkill != null && !isEmpty(t.ofSkill.skillVersionId)) {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("ID", t.ofSkill.skillVersionId);
                    skills.add(m);
                }
                if (t.ofMcp != null && !isEmpty(t.ofMcp.id)) {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("ID", t.ofMcp.id);
                    m.put("Enabled", true);
                    mcps.add(m);
                }
            }
        }
        if (!skills.isEmpty()) body.put("Skills", skills);
        if (!mcps.isEmpty()) body.put("MCPs", mcps);

        V1Agent result = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "CreateAgent", body),
                new TypeReference<V1Agent>() {});
        if (result == null || isEmpty(result.id)) {
            throw new IllegalStateException("hibot: create agent response missing ID");
        }
        result.name = params.name;
        if (params.model != null) result.modelId = params.model.id;
        return result;
    }

    public List<V1Agent> list(V1AgentListParams params) {
        Map<String, Object> body = Bodies.map();
        if (params != null) {
            Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
            Bodies.putIfNotEmpty(body, "Keyword", params.keyword);
        }
        AgentItems r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListAgents", body),
                new TypeReference<AgentItems>() {});
        if (r == null || r.items == null) return new ArrayList<>();
        return r.items;
    }

    public V1Agent get(V1AgentGetParams params) {
        if (params == null || isEmpty(params.agentId)) {
            throw new IllegalArgumentException("hibot: agent id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("AgentID", params.agentId);
        V1Agent r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetAgent", body),
                new TypeReference<V1Agent>() {});
        if (r == null || isEmpty(r.id)) {
            throw new IllegalStateException("hibot: get agent response missing ID");
        }
        return r;
    }

    public List<V1Agent> batchGet(V1AgentBatchGetParams params) {
        if (params == null || params.agentIds == null || params.agentIds.isEmpty()) {
            throw new IllegalArgumentException("hibot: AgentIDs is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("AgentIDs", params.agentIds);
        AgentItems r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "BatchGetAgents", body),
                new TypeReference<AgentItems>() {});
        if (r == null || r.items == null) return new ArrayList<>();
        return r.items;
    }

    public void update(V1AgentUpdateParams params) {
        if (params == null || isEmpty(params.agentId)) {
            throw new IllegalArgumentException("hibot: agent id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("AgentID", params.agentId);
        if (params.description != null) body.put("Description", params.description);
        if (params.modelId != null) body.put("ModelID", params.modelId);
        if (params.envId != null) body.put("EnvID", params.envId);
        if (params.system != null) body.put("SystemPrompt", params.system);
        if (params.skills != null) {
            List<Map<String, Object>> skills = new ArrayList<>();
            for (V1ManagedAgentSkillToolParams t : params.skills) {
                if (t == null || isEmpty(t.skillVersionId)) continue;
                Map<String, Object> m = new LinkedHashMap<>();
                m.put("ID", t.skillVersionId);
                skills.add(m);
            }
            body.put("Skills", skills);
        }
        if (params.mcps != null) {
            List<Map<String, Object>> mcps = new ArrayList<>();
            for (V1ManagedAgentMCPToolParams t : params.mcps) {
                if (t == null || isEmpty(t.id)) continue;
                Map<String, Object> m = new LinkedHashMap<>();
                m.put("ID", t.id);
                m.put("Enabled", true);
                mcps.add(m);
            }
            body.put("MCPs", mcps);
        }
        if (params.resetResources || (params.resources != null && !params.resources.isEmpty())) {
            Map<String, Object> r = buildResourceInput(params.resources);
            body.put("Resources", r != null ? r : new LinkedHashMap<>());
        }
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "UpdateAgent", body),
                null);
    }

    public void delete(V1AgentDeleteParams params) {
        if (params == null || isEmpty(params.agentId)) {
            throw new IllegalArgumentException("hibot: agent id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("AgentID", params.agentId);
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "DeleteAgent", body),
                null);
    }

    static Map<String, Object> buildResourceInput(List<V1ManagedAgentResourceRefParams> resources) {
        if (resources == null || resources.isEmpty()) return null;
        List<String> resourceIds = new ArrayList<>();
        List<String> directoryIds = new ArrayList<>();
        for (V1ManagedAgentResourceRefParams r : resources) {
            if (r == null) continue;
            if (!isEmpty(r.directoryId)) {
                directoryIds.add(r.directoryId);
            } else if (!isEmpty(r.id)) {
                resourceIds.add(r.id);
            }
        }
        Map<String, Object> out = new LinkedHashMap<>();
        if (!resourceIds.isEmpty()) out.put("ResourceIDs", resourceIds);
        if (!directoryIds.isEmpty()) out.put("DirectoryIDs", directoryIds);
        if (out.isEmpty()) return null;
        return out;
    }

    private static boolean isEmpty(String s) { return s == null || s.isEmpty(); }

    private static final class AgentItems {
        @com.fasterxml.jackson.annotation.JsonProperty("Items") public List<V1Agent> items;
    }
}
