package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1VoiceConfig {
    @JsonProperty("Preset") public Boolean preset;
    @JsonProperty("Voice") public String voice;
    @JsonProperty("Name") public String name;
    @JsonProperty("SupportedLanguages") public List<String> supportedLanguages;
}
