package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1CronJobRunResponse {
    @JsonProperty("StartedAt") public String startedAt;
    @JsonProperty("FinishedAt") public String finishedAt;
    @JsonProperty("Status") public String status;
    @JsonProperty("TriggerType") public String triggerType;
    @JsonProperty("DeliveryChannel") public String deliveryChannel;
    @JsonProperty("Result") public String result;
}
