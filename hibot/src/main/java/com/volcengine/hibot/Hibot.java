package com.volcengine.hibot;

import com.volcengine.hibot.internal.RequestExecutor;
import com.volcengine.hibot.v1.V1Client;

/**
 * Top-level Hibot SDK client. Wraps the underlying request executor and exposes the V1 namespace.
 *
 * <p>Equivalent to go/hibot.Client.
 */
public final class Hibot implements AutoCloseable {
    private final HibotConfig config;
    private final RequestExecutor requester;
    public final V1Client v1;

    public Hibot(HibotConfig config) {
        this.config = config;
        this.requester = new RequestExecutor(config);
        this.v1 = new V1Client(requester, config);
    }

    public HibotConfig config() {
        return config;
    }

    @Override
    public void close() {
        // HttpClient does not require explicit close on JDK 17.
    }
}
