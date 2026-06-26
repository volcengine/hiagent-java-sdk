package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ChannelRiskItem {
    @JsonProperty("ChannelID") public String channelId;
    @JsonProperty("ChannelName") public String channelName;
    @JsonProperty("Status") public String status;
    @JsonProperty("AgentID") public String agentId;
    @JsonProperty("UpdatedAt") public String updatedAt;
}
