package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ImageConfig {
    @JsonProperty("TextToImage") public V1IOConfig textToImage;
    @JsonProperty("ImageToImage") public V1IOConfig imageToImage;
    @JsonProperty("HW") public V1HWConfig hw;
    @JsonProperty("Resolution") public V1Resolution resolution;
    @JsonProperty("NegativePrompt") public V1CommonSwitch negativePrompt;
    @JsonProperty("Watermark") public V1CommonSwitch watermark;
}
