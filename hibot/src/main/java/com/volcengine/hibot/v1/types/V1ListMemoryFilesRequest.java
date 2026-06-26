package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ListMemoryFilesRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("StoreID") public String storeId;
    @JsonProperty("Prefix") public String prefix;
    @JsonProperty("Page") public V1PageInput page;
}
