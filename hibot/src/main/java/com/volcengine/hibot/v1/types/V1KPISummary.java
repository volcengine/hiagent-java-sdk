package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1KPISummary {
    @JsonProperty("AgentTotal") public Long agentTotal;
    @JsonProperty("AgentWithChannel") public Long agentWithChannel;
    @JsonProperty("SkillTotal") public Long skillTotal;
    @JsonProperty("SkillReferenced") public Long skillReferenced;
    @JsonProperty("ChannelOnline") public Long channelOnline;
    @JsonProperty("ChannelTotal") public Long channelTotal;
    @JsonProperty("ChannelError") public Long channelError;
    @JsonProperty("ActiveSessionTotal") public Long activeSessionTotal;
    @JsonProperty("SessionCreatedLast24h") public Long sessionCreatedLast24H;
}
