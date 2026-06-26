package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1CommonBoolSwitch {
    @JsonProperty("Supported") public Boolean supported;
    @JsonProperty("Enabled") public Boolean enabled;
}
