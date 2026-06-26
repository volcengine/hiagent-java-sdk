package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ParserConfig {
    @JsonProperty("InputFormat") public String inputFormat;
    @JsonProperty("OutputFormat") public String outputFormat;
}
