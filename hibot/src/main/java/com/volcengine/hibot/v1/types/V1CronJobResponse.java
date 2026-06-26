package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1CronJobResponse {
    @JsonProperty("ID") public String id;
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("AgentID") public String agentId;
    @JsonProperty("Name") public String name;
    @JsonProperty("Description") public String description;
    @JsonProperty("Prompt") public String prompt;
    @JsonProperty("ScheduleType") public String scheduleType;
    @JsonProperty("ScheduleValue") public String scheduleValue;
    @JsonProperty("ScheduleTimezone") public String scheduleTimezone;
    @JsonProperty("DeliveryChannel") public String deliveryChannel;
    @JsonProperty("DeliveryTo") public String deliveryTo;
    @JsonProperty("Enabled") public Boolean enabled;
    @JsonProperty("Source") public String source;
    @JsonProperty("NextRunAt") public String nextRunAt;
    @JsonProperty("LastStatus") public String lastStatus;
    @JsonProperty("LastRunAt") public String lastRunAt;
    @JsonProperty("LastRunID") public String lastRunId;
    @JsonProperty("Config") public V1RawJSON config;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("UpdatedAt") public String updatedAt;
}
