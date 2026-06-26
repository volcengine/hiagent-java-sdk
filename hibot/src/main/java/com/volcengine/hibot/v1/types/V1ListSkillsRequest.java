package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ListSkillsRequest {
    @JsonProperty("Keyword") public String keyword;
    @JsonProperty("Source") public String source;
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("Name") public String name;
    @JsonProperty("Page") public V1PageInput page;
    @JsonProperty("SlugID") public String slugId;
}
