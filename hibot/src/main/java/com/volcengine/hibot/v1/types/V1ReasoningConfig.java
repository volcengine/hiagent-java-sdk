package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ReasoningConfig {
    @JsonProperty("Switch") public V1ReasoningSwitch switchValue;
    @JsonProperty("Effort") public V1ReasoningEffort effort;
}
