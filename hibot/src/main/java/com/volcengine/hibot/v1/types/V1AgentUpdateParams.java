package com.volcengine.hibot.v1.types;

import java.util.List;

public final class V1AgentUpdateParams {
    public String workspaceId;
    public String agentId;
    public String description;
    public String modelId;
    public String envId;
    public String system;
    public List<V1ManagedAgentSkillToolParams> skills;
    public List<V1ManagedAgentMCPToolParams> mcps;
    public List<V1ManagedAgentResourceRefParams> resources;
    public boolean resetResources;
}
