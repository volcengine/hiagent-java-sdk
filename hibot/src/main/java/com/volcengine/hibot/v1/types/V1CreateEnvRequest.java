package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1CreateEnvRequest {
    @JsonProperty("Name") public String name;
    @JsonProperty("Description") public String description;
    @JsonProperty("ImageType") public String imageType;
    @JsonProperty("EnvVars") public V1RawJSON envVars;
    @JsonProperty("CpuLimit") public String cpuLimit;
    @JsonProperty("MemoryLimit") public String memoryLimit;
    @JsonProperty("PVCSize") public String pvcSize;
    @JsonProperty("DataPath") public String dataPath;
    @JsonProperty("SpecCode") public String specCode;
}
