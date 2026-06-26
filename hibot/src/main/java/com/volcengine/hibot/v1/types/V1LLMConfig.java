package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1LLMConfig {
    @JsonProperty("API") public V1APIConfig api;
    @JsonProperty("Token") public V1TokenConfig token;
    @JsonProperty("Reasoning") public V1ReasoningConfig reasoning;
    @JsonProperty("ToolCall") public V1ToolCallConfig toolCall;
}
