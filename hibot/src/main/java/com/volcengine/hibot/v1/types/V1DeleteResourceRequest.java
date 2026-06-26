package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1DeleteResourceRequest {
    @JsonProperty("DirectoryID") public String directoryId;
    @JsonProperty("ResourceID") public String resourceId;
    @JsonProperty("WorkspaceID") public String workspaceId;
}
