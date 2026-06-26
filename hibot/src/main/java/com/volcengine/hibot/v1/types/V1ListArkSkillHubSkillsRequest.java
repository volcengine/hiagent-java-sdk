package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ListArkSkillHubSkillsRequest {
    @JsonProperty("Keyword") public String keyword;
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("Page") public V1PageInput page;
}
