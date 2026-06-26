package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1IOConfig {
    @JsonProperty("Input") public V1CommonSwitch input;
    @JsonProperty("InputConfig") public V1IntRange inputConfig;
    @JsonProperty("Output") public V1CommonSwitch output;
    @JsonProperty("OutputConfig") public V1IntRange outputConfig;
}
