package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1Error {
    @JsonProperty("HTTPCode") public Integer httpCode;
    @JsonProperty("Code") public String code;
    @JsonProperty("Message") public String message;
}
