package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ListMemoryFilesForSyncRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("StoreID") public String storeId;
    @JsonProperty("AfterRevision") public Long afterRevision;
    @JsonProperty("Page") public V1PageInput page;
}
