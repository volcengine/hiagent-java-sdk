package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1Environment {
    @JsonProperty("ID") public String id;
    @JsonProperty("Name") public String name;
    @JsonProperty("Description") public String description;
    @JsonProperty("ImageType") public String imageType;
    @JsonProperty("EnvVars") public JsonNode envVars;
    @JsonProperty("CpuLimit") public String cpuLimit;
    @JsonProperty("MemoryLimit") public String memoryLimit;
    @JsonProperty("PVCSize") public String pvcSize;
    @JsonProperty("DataPath") public String dataPath;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("UpdatedAt") public String updatedAt;
    @JsonProperty("CreatedBy") public String createdBy;
    @JsonProperty("UpdatedBy") public String updatedBy;
}
