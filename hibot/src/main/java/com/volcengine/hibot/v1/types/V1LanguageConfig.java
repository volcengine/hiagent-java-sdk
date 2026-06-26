package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1LanguageConfig {
    @JsonProperty("Preset") public Boolean preset;
    @JsonProperty("Language") public String language;
    @JsonProperty("Name") public String name;
}
