package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ListTracesRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("ScrollID") public String scrollId;
    @JsonProperty("PageSize") public Integer pageSize;
    @JsonProperty("Filter") public V1ListTracesRequestFilter filter;
}
