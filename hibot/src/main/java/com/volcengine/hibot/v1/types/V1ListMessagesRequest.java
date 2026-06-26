package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ListMessagesRequest {
    @JsonProperty("SessionID") public String sessionId;
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("Visibility") public String visibility;
    @JsonProperty("Page") public V1PageInput page;
    @JsonProperty("DisplayMode") public String displayMode;
    @JsonProperty("BaseMessageID") public String baseMessageId;
}
