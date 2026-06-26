package com.volcengine.hibot.v1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.internal.RequestExecutor;
import com.volcengine.hibot.internal.Versions;
import com.volcengine.hibot.v1.types.*;

/** Mirrors go/hibot/v1/observations.go. */
public final class ObservationsService {
    private final RequestExecutor requester;
    private final HibotConfig config;

    public ObservationsService(RequestExecutor requester, HibotConfig config) {
        this.requester = requester;
        this.config = config;
    }

    public V1ListTracesResponse listTraces(V1ListTracesRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListTraces", params),
                new TypeReference<V1ListTracesResponse>() {});
    }

    public V1ListTraceSpansResponse listSpans(V1ListTraceSpansRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListTraceSpans", params),
                new TypeReference<V1ListTraceSpansResponse>() {});
    }

    public V1GetSpanDetailResponse getSpan(V1GetSpanDetailRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetSpanDetail", params),
                new TypeReference<V1GetSpanDetailResponse>() {});
    }
}
