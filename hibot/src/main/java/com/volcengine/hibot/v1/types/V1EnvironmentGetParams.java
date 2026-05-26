package com.volcengine.hibot.v1.types;

public final class V1EnvironmentGetParams {
    public String workspaceId;
    public String envId;

    public V1EnvironmentGetParams() {}

    public V1EnvironmentGetParams(String envId) {
        this.envId = envId;
    }
}
