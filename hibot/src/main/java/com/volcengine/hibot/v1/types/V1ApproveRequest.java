package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ApproveRequest {
    @JsonProperty("SessionID") public String sessionId;
    @JsonProperty("RunID") public String runId;
    @JsonProperty("ApprovalRequestID") public String approvalRequestId;
    @JsonProperty("ChoiceID") public String choiceId;
    @JsonProperty("WorkspaceID") public String workspaceId;
}
