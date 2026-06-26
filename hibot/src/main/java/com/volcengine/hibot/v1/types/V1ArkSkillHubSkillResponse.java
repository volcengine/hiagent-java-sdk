package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ArkSkillHubSkillResponse {
    @JsonProperty("Slug") public String slug;
    @JsonProperty("Name") public String name;
    @JsonProperty("Description") public String description;
    @JsonProperty("Namespace") public String namespace;
    @JsonProperty("Tags") public List<String> tags;
    @JsonProperty("Banned") public Boolean banned;
    @JsonProperty("SourceType") public String sourceType;
    @JsonProperty("SourceRepo") public String sourceRepo;
    @JsonProperty("Verify") public Boolean verify;
    @JsonProperty("Path") public String path;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("UpdatedAt") public String updatedAt;
    @JsonProperty("SkillMarkdown") public String skillMarkdown;
}
