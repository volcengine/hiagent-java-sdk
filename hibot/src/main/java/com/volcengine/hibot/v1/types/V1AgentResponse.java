package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1AgentResponse {
    @JsonProperty("ID") public String id;
    @JsonProperty("Name") public String name;
    @JsonProperty("Description") public String description;
    @JsonProperty("ModelID") public String modelId;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("UpdatedAt") public String updatedAt;
    @JsonProperty("EnvID") public String envId;
    @JsonProperty("Skills") public List<V1AgentSkillInput> skills = new ArrayList<>();
    @JsonProperty("CreatedBy") public String createdBy;
    @JsonProperty("UpdatedBy") public String updatedBy;
    @JsonProperty("ResourceIDs") public List<String> resourceIds = new ArrayList<>();
    @JsonProperty("MCPs") public List<V1AgentMCPResponse> mcps = new ArrayList<>();
    @JsonProperty("Channels") public List<V1ChannelUpsertInput> channels = new ArrayList<>();
    @JsonProperty("SystemPrompt") public String systemPrompt;
    @JsonProperty("Config") public V1AgentConfig config;
    @JsonProperty("RuntimeStatus") public V1AgentRuntimeStatus runtimeStatus;
    @JsonProperty("SessionCount") public Long sessionCount;
    @JsonProperty("Metadata") public Map<String, String> metadata = new LinkedHashMap<>();
    @JsonProperty("MemoryStores") public List<V1AgentMemoryStoreInput> memoryStores = new ArrayList<>();
    @JsonProperty("EffectiveChannelCount") public Integer effectiveChannelCount;
    @JsonProperty("Icon") public String icon;
    @JsonProperty("Visibility") public String visibility;
    @JsonProperty("SourceType") public String sourceType;
    @JsonProperty("SourceID") public String sourceId;
}
