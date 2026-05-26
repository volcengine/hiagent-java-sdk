package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1Session {
    @JsonProperty("ID") public String id;
    @JsonProperty("AgentID") public String agentId;
    @JsonProperty("SessionKey") public String sessionKey;
    @JsonProperty("Status") public String status;
    @JsonProperty("Channel") public String channel;
    @JsonProperty("PeerKind") public String peerKind;
    @JsonProperty("PeerID") public String peerId;
    @JsonProperty("RiskLevel") public String riskLevel;
    @JsonProperty("MessageCount") public Integer messageCount;
    @JsonProperty("LastMessageAt") public String lastMessageAt;
    @JsonProperty("LastMessageContent") public String lastMessageContent;
    @JsonProperty("Summary") public String summary;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("UpdatedAt") public String updatedAt;
    @JsonProperty("ArchivedAt") public String archivedAt;
}
