package com.volcengine.hibot.v1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.internal.RequestExecutor;
import com.volcengine.hibot.internal.Versions;
import com.volcengine.hibot.v1.types.*;

/** Mirrors go/hibot/v1/runtime_api_keys.go. */
public final class RuntimeApiKeysService {
    private final RequestExecutor requester;
    private final HibotConfig config;

    public RuntimeApiKeysService(RequestExecutor requester, HibotConfig config) {
        this.requester = requester;
        this.config = config;
    }

    public V1CreateRuntimeAPIKeyResponse create(V1CreateRuntimeAPIKeyRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "CreateRuntimeAPIKey", params),
                new TypeReference<V1CreateRuntimeAPIKeyResponse>() {});
    }

    public V1ListRuntimeAPIKeysResponse list(V1ListRuntimeAPIKeysRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListRuntimeAPIKeys", params),
                new TypeReference<V1ListRuntimeAPIKeysResponse>() {});
    }

    public V1GetRuntimeAPIKeyResponse get(V1GetRuntimeAPIKeyRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetRuntimeAPIKey", params),
                new TypeReference<V1GetRuntimeAPIKeyResponse>() {});
    }

    public void update(V1UpdateRuntimeAPIKeyRequest params) {
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "UpdateRuntimeAPIKey", params),
                null);
    }

    public void delete(V1DeleteRuntimeAPIKeyRequest params) {
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "DeleteRuntimeAPIKey", params),
                null);
    }
}
