package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ChannelResponse {
    @JsonProperty("ID") public String id;
    @JsonProperty("AgentID") public String agentId;
    @JsonProperty("Name") public String name;
    @JsonProperty("ChannelType") public String channelType;
    @JsonProperty("Config") public V1RawJSON config;
    @JsonProperty("Status") public String status;
    @JsonProperty("AssignedGatewayID") public String assignedGatewayId;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("UpdatedAt") public String updatedAt;
    @JsonProperty("CreatedBy") public String createdBy;
    @JsonProperty("UpdatedBy") public String updatedBy;
    @JsonProperty("FeishuConfig") public V1FeishuChannelConfig feishuConfig;
    @JsonProperty("WeComConfig") public V1WeComChannelConfig weComConfig;
}
