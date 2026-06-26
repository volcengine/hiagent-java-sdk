package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1MaxTokens {
    @JsonProperty("Max") public Integer max;
    @JsonProperty("Min") public Integer min;
    @JsonProperty("Default") public Integer defaultValue;
}
