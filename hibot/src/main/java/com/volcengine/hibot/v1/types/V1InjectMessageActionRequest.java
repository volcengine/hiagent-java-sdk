package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1InjectMessageActionRequest {
    @JsonProperty("SessionID") public String sessionId;
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("Payload") public V1InjectMessageRequest payload;
}
