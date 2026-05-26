package com.volcengine.hibot.v1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.internal.Bodies;
import com.volcengine.hibot.internal.RequestExecutor;
import com.volcengine.hibot.internal.Versions;
import com.volcengine.hibot.v1.types.V1Environment;
import com.volcengine.hibot.v1.types.V1EnvironmentDeleteParams;
import com.volcengine.hibot.v1.types.V1EnvironmentGetParams;
import com.volcengine.hibot.v1.types.V1EnvironmentListParams;
import com.volcengine.hibot.v1.types.V1EnvironmentNewParams;
import com.volcengine.hibot.v1.types.V1EnvironmentUpdateParams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/** Mirrors go/hibot/v1/environments.go. */
public final class EnvironmentsService {
    private final RequestExecutor requester;
    private final HibotConfig config;

    public EnvironmentsService(RequestExecutor requester, HibotConfig config) {
        this.requester = requester;
        this.config = config;
    }

    public V1Environment create(V1EnvironmentNewParams params) {
        Map<String, Object> payload = Bodies.map();
        Bodies.putIfNotEmpty(payload, "ImageType", params.imageType);
        Bodies.putIfNotEmpty(payload, "Name", params.name);
        Bodies.putIfNotEmpty(payload, "Description", params.description);
        if (params.envVars != null && !params.envVars.isMissingNode() && !params.envVars.isNull()) {
            payload.put("EnvVars", params.envVars);
        }
        Bodies.putIfNotEmpty(payload, "CpuLimit", params.cpuLimit);
        Bodies.putIfNotEmpty(payload, "MemoryLimit", params.memoryLimit);
        Bodies.putIfNotEmpty(payload, "PVCSize", params.pvcSize);
        Bodies.putIfNotEmpty(payload, "DataPath", params.dataPath);

        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("Payload", payload);

        V1Environment result = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "CreateEnv", body),
                new TypeReference<V1Environment>() {});
        if (result == null || result.id == null || result.id.isEmpty()) {
            throw new IllegalStateException("hibot: create environment response missing ID");
        }
        result.name = params.name;
        result.imageType = params.imageType;
        return result;
    }

    public List<V1Environment> list(V1EnvironmentListParams params) {
        Map<String, Object> body = Bodies.map();
        if (params != null) {
            Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        }
        EnvList result = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListEnv", body),
                new TypeReference<EnvList>() {});
        if (result == null || result.items == null) {
            return new ArrayList<>();
        }
        return result.items;
    }

    public V1Environment get(V1EnvironmentGetParams params) {
        if (params == null || params.envId == null || params.envId.isEmpty()) {
            throw new IllegalArgumentException("hibot: env id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("EnvID", params.envId);
        V1Environment result = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetEnv", body),
                new TypeReference<V1Environment>() {});
        if (result == null || result.id == null || result.id.isEmpty()) {
            throw new IllegalStateException("hibot: get environment response missing ID");
        }
        return result;
    }

    public void update(V1EnvironmentUpdateParams params) {
        if (params == null || params.envId == null || params.envId.isEmpty()) {
            throw new IllegalArgumentException("hibot: env id is required");
        }
        Map<String, Object> payload = Bodies.map();
        Bodies.putIfNotEmpty(payload, "Name", params.name);
        Bodies.putIfNotEmpty(payload, "Description", params.description);
        Bodies.putIfNotEmpty(payload, "ImageType", params.imageType);
        if (params.envVars != null && !params.envVars.isMissingNode() && !params.envVars.isNull()) {
            payload.put("EnvVars", params.envVars);
        }
        Bodies.putIfNotEmpty(payload, "CpuLimit", params.cpuLimit);
        Bodies.putIfNotEmpty(payload, "MemoryLimit", params.memoryLimit);
        Bodies.putIfNotEmpty(payload, "PVCSize", params.pvcSize);
        Bodies.putIfNotEmpty(payload, "DataPath", params.dataPath);
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("EnvID", params.envId);
        body.put("Payload", payload);
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "UpdateEnv", body),
                null);
    }

    public void delete(V1EnvironmentDeleteParams params) {
        if (params == null || params.envId == null || params.envId.isEmpty()) {
            throw new IllegalArgumentException("hibot: env id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("EnvID", params.envId);
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "DeleteEnv", body),
                null);
    }

    public V1Environment defaultEnvironment(V1EnvironmentListParams params) {
        List<V1Environment> items = list(params);
        if (items.isEmpty()) {
            throw new IllegalStateException("hibot: no environment found");
        }
        items.sort(Comparator.comparing((V1Environment e) -> e.createdAt == null ? "" : e.createdAt));
        return items.get(0);
    }

    private static final class EnvList {
        @com.fasterxml.jackson.annotation.JsonProperty("Items")
        public List<V1Environment> items;
    }
}
