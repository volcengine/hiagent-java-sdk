package com.volcengine.hibot.v1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.internal.Bodies;
import com.volcengine.hibot.internal.RequestExecutor;
import com.volcengine.hibot.internal.Versions;
import com.volcengine.hibot.v1.types.V1Prompt;
import com.volcengine.hibot.v1.types.V1PromptDeleteParams;
import com.volcengine.hibot.v1.types.V1PromptListParams;
import com.volcengine.hibot.v1.types.V1PromptNewParams;
import com.volcengine.hibot.v1.types.V1PromptUpdateParams;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Mirrors go/hibot/v1/prompts.go. */
public final class PromptsService {
    private final RequestExecutor requester;
    private final HibotConfig config;

    public PromptsService(RequestExecutor requester, HibotConfig config) {
        this.requester = requester;
        this.config = config;
    }

    public V1Prompt create(V1PromptNewParams params) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("Name", params.name);
        payload.put("SystemPrompt", params.content);
        Map<String, Object> body = Bodies.map();
        body.put("Payload", payload);
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        IdResult result = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER,
                        "CreateAgentPromptTemplate", body),
                new TypeReference<IdResult>() {});
        V1Prompt p = new V1Prompt();
        p.id = result == null ? null : result.id;
        p.name = params.name;
        p.content = params.content;
        return p;
    }

    public List<V1Prompt> list(V1PromptListParams params) {
        Map<String, Object> body = Bodies.map();
        if (params != null) Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        Items result = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER,
                        "ListAgentPromptTemplates", body),
                new TypeReference<Items>() {});
        if (result == null || result.items == null) return new ArrayList<>();
        return result.items;
    }

    public void update(V1PromptUpdateParams params) {
        if (params == null || params.id == null || params.id.isEmpty()) {
            throw new IllegalArgumentException("hibot: prompt id is required");
        }
        Map<String, Object> payload = Bodies.map();
        Bodies.putIfNotEmpty(payload, "Name", params.name);
        if (params.content != null) {
            payload.put("SystemPrompt", params.content);
        }
        Map<String, Object> body = Bodies.map();
        body.put("ID", params.id);
        body.put("Payload", payload);
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER,
                        "UpdateAgentPromptTemplate", body),
                null);
    }

    public void delete(V1PromptDeleteParams params) {
        if (params == null || params.id == null || params.id.isEmpty()) {
            throw new IllegalArgumentException("hibot: prompt id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("ID", params.id);
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER,
                        "DeleteAgentPromptTemplate", body),
                null);
    }

    private static final class IdResult {
        @com.fasterxml.jackson.annotation.JsonProperty("ID") public String id;
    }
    private static final class Items {
        @com.fasterxml.jackson.annotation.JsonProperty("Items") public List<V1Prompt> items;
    }
}
