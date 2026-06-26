package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1SessionConfig {
    @JsonProperty("Model") public String model;
    @JsonProperty("MaxTurns") public Integer maxTurns;
    @JsonProperty("ToolAllow") public List<String> toolAllow;
    @JsonProperty("ToolDeny") public List<String> toolDeny;
    @JsonProperty("MultiHolding") public Boolean multiHolding;
    @JsonProperty("Streamed") public Boolean streamed;
}
