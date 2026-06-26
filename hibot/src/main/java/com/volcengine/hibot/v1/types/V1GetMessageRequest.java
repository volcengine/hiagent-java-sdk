package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1GetMessageRequest {
    @JsonProperty("SessionID") public String sessionId;
    @JsonProperty("MessageID") public String messageId;
    @JsonProperty("WorkspaceID") public String workspaceId;
}
