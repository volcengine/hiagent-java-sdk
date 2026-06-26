package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1MemoryFileResponse {
    @JsonProperty("ID") public String id;
    @JsonProperty("StoreID") public String storeId;
    @JsonProperty("Path") public String path;
    @JsonProperty("ArtifactID") public String artifactId;
    @JsonProperty("ContentSha256") public String contentSha256;
    @JsonProperty("SizeBytes") public Long sizeBytes;
    @JsonProperty("MimeType") public String mimeType;
    @JsonProperty("Revision") public Long revision;
    @JsonProperty("Content") public String content;
    @JsonProperty("CreatedBy") public String createdBy;
    @JsonProperty("UpdatedBy") public String updatedBy;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("UpdatedAt") public String updatedAt;
}
