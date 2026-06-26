package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1RunCronJobNowResponse {
    @JsonProperty("CronJobID") public String cronJobId;
    @JsonProperty("Status") public String status;
    @JsonProperty("TriggerType") public String triggerType;
}
