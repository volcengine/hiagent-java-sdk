package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1MoveResourceRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("ResourceID") public String resourceId;
    @JsonProperty("OldDirectoryID") public String oldDirectoryId;
    @JsonProperty("NewDirectoryID") public String newDirectoryId;
}
