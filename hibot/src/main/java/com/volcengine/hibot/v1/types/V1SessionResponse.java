package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1SessionResponse {
    @JsonProperty("ID") public String id;
    @JsonProperty("AgentID") public String agentId;
    @JsonProperty("SessionKey") public String sessionKey;
    @JsonProperty("Status") public String status;
    @JsonProperty("Channel") public String channel;
    @JsonProperty("PeerKind") public String peerKind;
    @JsonProperty("PeerID") public String peerId;
    @JsonProperty("RiskLevel") public String riskLevel;
    @JsonProperty("Config") public V1SessionConfig config;
    @JsonProperty("AuthContext") public V1SessionAuthContext authContext;
    @JsonProperty("MessageCount") public Integer messageCount;
    @JsonProperty("LastMessageAt") public String lastMessageAt;
    @JsonProperty("Summary") public String summary;
    @JsonProperty("Metadata") public V1RawJSON metadata;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("UpdatedAt") public String updatedAt;
    @JsonProperty("ArchivedAt") public String archivedAt;
    @JsonProperty("DeletedAt") public String deletedAt;
    @JsonProperty("LastMessageContent") public String lastMessageContent;
}
