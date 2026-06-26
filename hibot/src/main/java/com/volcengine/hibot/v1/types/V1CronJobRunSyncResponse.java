package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1CronJobRunSyncResponse {
    @JsonProperty("ID") public String id;
    @JsonProperty("TenantID") public String tenantId;
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("AgentID") public String agentId;
    @JsonProperty("CronJobID") public String cronJobId;
    @JsonProperty("SessionID") public String sessionId;
    @JsonProperty("Prompt") public String prompt;
    @JsonProperty("Status") public String status;
    @JsonProperty("ScheduledAt") public String scheduledAt;
    @JsonProperty("TriggerType") public String triggerType;
    @JsonProperty("StartedAt") public String startedAt;
    @JsonProperty("FinishedAt") public String finishedAt;
    @JsonProperty("Result") public String result;
}
