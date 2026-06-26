package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ReasoningEffort {
    @JsonProperty("Types") public List<String> types;
    @JsonProperty("DefaultType") public String defaultType;
}
