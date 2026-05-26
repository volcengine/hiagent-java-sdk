package com.volcengine.hibot.v1.types;

public final class V1AgentDeleteParams {
    public String workspaceId;
    public String agentId;

    public V1AgentDeleteParams() {}

    public V1AgentDeleteParams(String agentId) {
        this.agentId = agentId;
    }
}
