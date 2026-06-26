package com.volcengine.hibot.v1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.internal.RequestExecutor;
import com.volcengine.hibot.internal.Versions;
import com.volcengine.hibot.v1.types.*;

/** Mirrors go/hibot/v1/overview.go. */
public final class OverviewService {
    private final RequestExecutor requester;
    private final HibotConfig config;

    public OverviewService(RequestExecutor requester, HibotConfig config) {
        this.requester = requester;
        this.config = config;
    }

    public V1GetOverviewResponse get(V1GetOverviewRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetOverview", params),
                new TypeReference<V1GetOverviewResponse>() {});
    }
}
