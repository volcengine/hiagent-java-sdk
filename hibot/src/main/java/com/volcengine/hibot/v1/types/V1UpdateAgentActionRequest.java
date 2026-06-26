package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1UpdateAgentActionRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("AgentID") public String agentId;
    @JsonProperty("Description") public String description;
    @JsonProperty("ModelID") public String modelId;
    @JsonProperty("Skills") public List<V1AgentSkillInput> skills;
    @JsonProperty("Resources") public V1AgentResourceInput resources;
    @JsonProperty("MCPs") public List<V1AgentMCPInput> mcps;
    @JsonProperty("EnvID") public String envId;
    @JsonProperty("SystemPrompt") public String systemPrompt;
    @JsonProperty("Config") public V1AgentConfig config;
    @JsonProperty("Metadata") public Map<String, String> metadata;
    @JsonProperty("MemoryStores") public List<V1AgentMemoryStoreInput> memoryStores;
    @JsonProperty("Icon") public String icon;
}
