package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1APIConfig {
    @JsonProperty("Chat") public V1CommonBoolSwitch chat;
    @JsonProperty("Responses") public V1CommonBoolSwitch responses;
}
