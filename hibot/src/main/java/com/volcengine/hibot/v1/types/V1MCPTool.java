package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1MCPTool {
    @JsonProperty("Name") public String name;
    @JsonProperty("Description") public String description;
}
