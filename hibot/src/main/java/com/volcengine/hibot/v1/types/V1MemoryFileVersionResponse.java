package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1MemoryFileVersionResponse {
    @JsonProperty("ID") public String id;
    @JsonProperty("FileID") public String fileId;
    @JsonProperty("StoreID") public String storeId;
    @JsonProperty("Path") public String path;
    @JsonProperty("Revision") public Long revision;
    @JsonProperty("ArtifactID") public String artifactId;
    @JsonProperty("ContentSha256") public String contentSha256;
    @JsonProperty("SizeBytes") public Long sizeBytes;
    @JsonProperty("OpType") public String opType;
    @JsonProperty("CreatedBy") public String createdBy;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("Content") public String content;
}
