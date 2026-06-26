package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ModelProperty {
    @JsonProperty("Common") public V1CommonModelConfig common;
    @JsonProperty("LLM") public V1LLMConfig llm;
    @JsonProperty("Embedding") public V1EmbeddingConfig embedding;
    @JsonProperty("Vision") public V1VisionConfig vision;
    @JsonProperty("Audio") public V1AudioConfig audio;
}
