package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1SpanEvent {
    @JsonProperty("Name") public String name;
    @JsonProperty("Time") public String time;
    @JsonProperty("Attributes") public String attributes;
}
