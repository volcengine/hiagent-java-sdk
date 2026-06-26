package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1AgentMCPInput {
    @JsonProperty("ID") public String id;
    @JsonProperty("Enabled") public Boolean enabled;
    @JsonProperty("ToolAllowlist") public List<String> toolAllowlist;
    @JsonProperty("ToolDenylist") public List<String> toolDenylist;
}
