package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ListCronJobRunsForSyncRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("CursorUpdatedAt") public String cursorUpdatedAt;
    @JsonProperty("CursorID") public String cursorId;
    @JsonProperty("PageSize") public Integer pageSize;
}
