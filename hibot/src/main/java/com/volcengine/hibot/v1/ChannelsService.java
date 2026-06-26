package com.volcengine.hibot.v1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.internal.RequestExecutor;
import com.volcengine.hibot.internal.Versions;
import com.volcengine.hibot.v1.types.*;

/** Mirrors go/hibot/v1/channels.go. */
public final class ChannelsService {
    private final RequestExecutor requester;
    private final HibotConfig config;

    public ChannelsService(RequestExecutor requester, HibotConfig config) {
        this.requester = requester;
        this.config = config;
    }

    public V1CreateChannelResponse create(V1CreateChannelActionRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "CreateChannel", params),
                new TypeReference<V1CreateChannelResponse>() {});
    }

    public V1ListChannelsResponse list(V1ListChannelsRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListChannels", params),
                new TypeReference<V1ListChannelsResponse>() {});
    }

    public V1ChannelResponse get(V1GetChannelRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetChannel", params),
                new TypeReference<V1ChannelResponse>() {});
    }

    public void update(V1UpdateChannelActionRequest params) {
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "UpdateChannel", params),
                null);
    }

    public void delete(V1DeleteChannelRequest params) {
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "DeleteChannel", params),
                null);
    }
}
