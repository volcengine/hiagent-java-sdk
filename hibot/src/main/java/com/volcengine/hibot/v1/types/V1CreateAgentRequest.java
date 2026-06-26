package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1CreateAgentRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("Name") public String name;
    @JsonProperty("Description") public String description;
    @JsonProperty("ModelID") public String modelId;
    @JsonProperty("Channels") public List<V1ChannelUpsertInput> channels;
    @JsonProperty("Skills") public List<V1AgentSkillInput> skills;
    @JsonProperty("Resources") public V1AgentResourceInput resources;
    @JsonProperty("MCPs") public List<V1AgentMCPInput> mcps;
    @JsonProperty("EnvID") public String envId;
    @JsonProperty("SystemPrompt") public String systemPrompt;
    @JsonProperty("Config") public V1AgentConfig config;
    @JsonProperty("Metadata") public Map<String, String> metadata;
    @JsonProperty("Icon") public String icon;
    @JsonProperty("Visibility") public String visibility;
    @JsonProperty("SourceType") public String sourceType;
    @JsonProperty("SourceID") public String sourceId;
}
