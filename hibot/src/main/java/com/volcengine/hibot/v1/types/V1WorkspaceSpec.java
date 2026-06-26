package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1WorkspaceSpec {
    @JsonProperty("SpecCode") public String specCode;
    @JsonProperty("DisplayName") public String displayName;
    @JsonProperty("Cpu") public String cpu;
    @JsonProperty("Memory") public String memory;
    @JsonProperty("Storage") public String storage;
    @JsonProperty("IsDefault") public Boolean isDefault;
}
