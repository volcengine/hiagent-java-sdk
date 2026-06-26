package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1GetRunRequest {
    @JsonProperty("RunID") public String runId;
    @JsonProperty("WorkspaceID") public String workspaceId;
}
