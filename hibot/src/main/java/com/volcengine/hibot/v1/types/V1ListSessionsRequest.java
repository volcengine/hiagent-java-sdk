package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ListSessionsRequest {
    @JsonProperty("AgentID") public String agentId;
    @JsonProperty("Status") public String status;
    @JsonProperty("Channel") public String channel;
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("Page") public V1PageInput page;
    @JsonProperty("SessionKeys") public List<String> sessionKeys;
    @JsonProperty("UserID") public String userId;
}
