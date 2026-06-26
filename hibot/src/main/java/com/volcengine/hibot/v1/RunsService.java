package com.volcengine.hibot.v1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.internal.RequestExecutor;
import com.volcengine.hibot.internal.Versions;
import com.volcengine.hibot.v1.types.*;

/** Mirrors go/hibot/v1/runs.go. */
public final class RunsService {
    private final RequestExecutor requester;
    private final HibotConfig config;

    public RunsService(RequestExecutor requester, HibotConfig config) {
        this.requester = requester;
        this.config = config;
    }

    public V1ListRunsResponse list(V1ListRunsRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListRuns", params),
                new TypeReference<V1ListRunsResponse>() {});
    }

    public V1RunResponse get(V1GetRunRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetRun", params),
                new TypeReference<V1RunResponse>() {});
    }

    public void cancel(V1CancelRunRequest params) {
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "CancelRun", params),
                null);
    }
}
