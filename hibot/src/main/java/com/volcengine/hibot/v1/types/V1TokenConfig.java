package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1TokenConfig {
    @JsonProperty("ContextTokens") public Integer contextTokens;
    @JsonProperty("MaxTokens") public V1MaxTokens maxTokens;
}
