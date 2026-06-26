package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1Ratio {
    @JsonProperty("Values") public List<String> values;
    @JsonProperty("Adaptive") public Boolean adaptive;
    @JsonProperty("Default") public String defaultValue;
}
