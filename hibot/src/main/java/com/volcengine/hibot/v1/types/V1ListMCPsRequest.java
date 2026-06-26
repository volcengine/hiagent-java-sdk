package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ListMCPsRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("Keyword") public String keyword;
    @JsonProperty("Status") public String status;
    @JsonProperty("Source") public String source;
    @JsonProperty("Page") public V1PageInput page;
}
