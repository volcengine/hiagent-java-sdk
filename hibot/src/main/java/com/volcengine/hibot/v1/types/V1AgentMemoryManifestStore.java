package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1AgentMemoryManifestStore {
    @JsonProperty("StoreID") public String storeId;
    @JsonProperty("Alias") public String alias;
    @JsonProperty("Description") public String description;
    @JsonProperty("Access") public String access;
    @JsonProperty("TargetSubPath") public String targetSubPath;
    @JsonProperty("Files") public List<V1AgentMemoryManifestFile> files;
}
