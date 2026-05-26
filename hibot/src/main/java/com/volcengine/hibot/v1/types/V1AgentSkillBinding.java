package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1AgentSkillBinding {
    @JsonProperty("ID") public String id;
    @JsonProperty("Enabled") public Boolean enabled;
}
