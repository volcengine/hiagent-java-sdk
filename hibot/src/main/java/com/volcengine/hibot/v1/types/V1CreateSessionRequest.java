package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1CreateSessionRequest {
    @JsonProperty("Channel") public String channel;
    @JsonProperty("PeerKind") public String peerKind;
    @JsonProperty("PeerID") public String peerId;
    @JsonProperty("SessionKey") public String sessionKey;
    @JsonProperty("RiskLevel") public String riskLevel;
    @JsonProperty("Config") public V1SessionConfig config;
    @JsonProperty("AuthContext") public V1SessionAuthContext authContext;
    @JsonProperty("Metadata") public V1RawJSON metadata;
    @JsonProperty("ConversationID") public String conversationId;
}
