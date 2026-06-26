package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1IntRange {
    @JsonProperty("Min") public Long min;
    @JsonProperty("Max") public Long max;
    @JsonProperty("Default") public Long defaultValue;
    @JsonProperty("Switch") public V1CommonSwitch switchValue;
}
