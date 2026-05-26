package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1Agent {
    @JsonProperty("ID") public String id;
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("Name") public String name;
    @JsonProperty("Description") public String description;
    @JsonProperty("ModelID") public String modelId;
    @JsonProperty("EnvID") public String envId;
    @JsonProperty("SystemPrompt") public String systemPrompt;
    @JsonProperty("Skills") public List<V1AgentSkillBinding> skills;
    @JsonProperty("MCPs") public List<V1AgentMCPBinding> mcps;
    @JsonProperty("ResourceIDs") public List<String> resourceIds;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("UpdatedAt") public String updatedAt;
    @JsonProperty("CreatedBy") public String createdBy;
    @JsonProperty("UpdatedBy") public String updatedBy;

    @JsonIgnore
    public String id() { return id; }
}
