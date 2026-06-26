package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1CreateCronJobResponse {
    @JsonProperty("CronJobID") public String cronJobId;
    @JsonProperty("NextRunAt") public String nextRunAt;
    @JsonProperty("CreatedAt") public String createdAt;
}
