package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1VisionConfig {
    @JsonProperty("GuidanceScale") public V1DoubleRange guidanceScale;
    @JsonProperty("Seed") public V1IntRange seed;
    @JsonProperty("Image") public V1ImageConfig image;
    @JsonProperty("Video") public V1VideoConfig video;
}
