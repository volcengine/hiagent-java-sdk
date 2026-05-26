package com.volcengine.hibot.v1.types;

public final class V1SessionGetParams {
    public String workspaceId;
    public String sessionId;

    public V1SessionGetParams() {}

    public V1SessionGetParams(String sessionId) {
        this.sessionId = sessionId;
    }
}
