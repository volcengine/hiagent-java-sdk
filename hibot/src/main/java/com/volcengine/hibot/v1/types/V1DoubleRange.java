package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1DoubleRange {
    @JsonProperty("Min") public Double min;
    @JsonProperty("Max") public Double max;
    @JsonProperty("Default") public Double defaultValue;
    @JsonProperty("Switch") public V1CommonSwitch switchValue;
}
