package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1DeleteMemoryFileRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("StoreID") public String storeId;
    @JsonProperty("Path") public String path;
    @JsonProperty("BaseSha256") public String baseSha256;
    @JsonProperty("BaseRevision") public Long baseRevision;
}
