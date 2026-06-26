package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1UpdateCronJobRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("CronJobID") public String cronJobId;
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
    @JsonProperty("UpdateFields") public List<String> updateFields;
    @JsonProperty("CorrelationID") public String correlationId;
}
