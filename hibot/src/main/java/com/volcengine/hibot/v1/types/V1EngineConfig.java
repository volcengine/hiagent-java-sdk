package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1EngineConfig {
    @JsonProperty("Kind") public String kind;
    @JsonProperty("Image") public String image;
    @JsonProperty("ResourceLimit") public V1ResourceLimit resourceLimit;
    @JsonProperty("EnvVars") public Map<String, String> envVars;
    @JsonProperty("RuntimeConfig") public V1RawJSON runtimeConfig;
}
