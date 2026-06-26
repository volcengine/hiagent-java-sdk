package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1AgentConfig {
    @JsonProperty("Evolution") public V1AgentEvolutionConfig evolution;
    @JsonProperty("Security") public V1AgentSecurityConfig security;
    @JsonProperty("Scaling") public V1AgentRuntimeScalingConfig scaling;
    @JsonProperty("ChannelAccess") public V1AgentChannelAccessConfig channelAccess;
}
