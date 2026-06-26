package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1MemoryStoreResponse {
    @JsonProperty("ID") public String id;
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("Name") public String name;
    @JsonProperty("Alias") public String alias;
    @JsonProperty("Description") public String description;
    @JsonProperty("OwnerType") public String ownerType;
    @JsonProperty("OwnerID") public String ownerId;
    @JsonProperty("Visibility") public String visibility;
    @JsonProperty("Access") public String access;
    @JsonProperty("RetrievalMode") public String retrievalMode;
    @JsonProperty("ExtractionPolicy") public V1RawJSON extractionPolicy;
    @JsonProperty("QuotaPolicy") public V1RawJSON quotaPolicy;
    @JsonProperty("FileCount") public Integer fileCount;
    @JsonProperty("BytesUsed") public Long bytesUsed;
    @JsonProperty("CreatedBy") public String createdBy;
    @JsonProperty("UpdatedBy") public String updatedBy;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("UpdatedAt") public String updatedAt;
}
