package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1AudioConfig {
    @JsonProperty("TTS") public V1TTSConfig tts;
    @JsonProperty("ASR") public V1ASRConfig asr;
    @JsonProperty("Transcription") public V1TranscriptionConfig transcription;
}
