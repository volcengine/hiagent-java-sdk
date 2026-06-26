package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1MultiVoiceConfig {
    @JsonProperty("Enabled") public Boolean enabled;
    @JsonProperty("Voices") public List<V1VoiceConfig> voices;
    @JsonProperty("DefaultVoice") public String defaultVoice;
}
