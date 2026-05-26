package com.volcengine.hibot.v1.types;

import java.util.List;

/** Parameters for AgentsService.create. */
public final class V1AgentNewParams {
    public String workspaceId;
    public String name;
    public String envId;
    public V1ManagedAgentModelConfigParams model;
    public String system;
    public List<V1AgentNewParamsToolUnion> tools;
    public List<V1ManagedAgentResourceRefParams> resources;

    public V1AgentNewParams() {}
}
