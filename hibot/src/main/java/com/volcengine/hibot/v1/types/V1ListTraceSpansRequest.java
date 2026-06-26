package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ListTraceSpansRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("TraceID") public String traceId;
    @JsonProperty("ScrollID") public String scrollId;
    @JsonProperty("PageSize") public Integer pageSize;
}
