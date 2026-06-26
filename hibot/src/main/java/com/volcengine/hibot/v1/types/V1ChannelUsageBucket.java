package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ChannelUsageBucket {
    @JsonProperty("ChannelID") public String channelId;
    @JsonProperty("ChannelName") public String channelName;
    @JsonProperty("SessionCount") public Long sessionCount;
}
