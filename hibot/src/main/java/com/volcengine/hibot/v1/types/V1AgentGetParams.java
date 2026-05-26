package com.volcengine.hibot.v1.types;

public final class V1AgentGetParams {
    public String workspaceId;
    public String agentId;

    public V1AgentGetParams() {}

    public V1AgentGetParams(String agentId) {
        this.agentId = agentId;
    }
}
