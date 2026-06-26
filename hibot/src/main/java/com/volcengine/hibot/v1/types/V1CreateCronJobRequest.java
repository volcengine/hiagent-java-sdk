package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1CreateCronJobRequest {
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
    @JsonProperty("Config") public V1RawJSON config;
    @JsonProperty("CorrelationID") public String correlationId;
}
