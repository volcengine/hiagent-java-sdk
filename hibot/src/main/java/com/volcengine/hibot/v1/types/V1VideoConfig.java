package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1VideoConfig {
    @JsonProperty("Resolutions") public List<String> resolutions;
    @JsonProperty("Ratio") public V1Ratio ratio;
    @JsonProperty("Duration") public V1Duration duration;
    @JsonProperty("CameraFixed") public V1CommonSwitch cameraFixed;
    @JsonProperty("Features") public List<V1VisionFeature> features;
    @JsonProperty("GenerateAudio") public V1CommonSwitch generateAudio;
    @JsonProperty("NegativePrompt") public V1CommonSwitch negativePrompt;
    @JsonProperty("Watermark") public V1CommonSwitch watermark;
}
