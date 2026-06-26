package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1UsageDistribution {
    @JsonProperty("SessionChannels") public List<V1ChannelUsageBucket> sessionChannels;
    @JsonProperty("TopAgentsLast7d") public List<V1AgentUsageBucket> topAgentsLast7D;
}
