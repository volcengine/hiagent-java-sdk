package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ChannelUpsertInput {
    @JsonProperty("Name") public String name;
    @JsonProperty("ChannelType") public String channelType;
    @JsonProperty("DmPolicy") public String dmPolicy;
    @JsonProperty("GroupPolicy") public String groupPolicy;
    @JsonProperty("Allowlist") public List<String> allowlist;
    @JsonProperty("FeishuConfig") public V1FeishuChannelConfig feishuConfig;
    @JsonProperty("WeComConfig") public V1WeComChannelConfig weComConfig;
}
