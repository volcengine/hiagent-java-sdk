package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1CreateMemoryStoreRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("Name") public String name;
    @JsonProperty("Alias") public String alias;
    @JsonProperty("Description") public String description;
    @JsonProperty("Access") public String access;
    @JsonProperty("ExtractionPolicy") public V1RawJSON extractionPolicy;
    @JsonProperty("QuotaPolicy") public V1RawJSON quotaPolicy;
}
