package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1TTSConfig {
    @JsonProperty("MultiLanguage") public V1MultiLanguageConfig multiLanguage;
    @JsonProperty("MultiVoice") public V1MultiVoiceConfig multiVoice;
}
