package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1EmbeddingConfig {
    @JsonProperty("HiddenSize") public V1HiddenSizeConfig hiddenSize;
    @JsonProperty("Input") public V1EmbeddingInputConfig input;
}
