package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1DeleteCronJobRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("CronJobID") public String cronJobId;
    @JsonProperty("CancelRunning") public Boolean cancelRunning;
    @JsonProperty("CorrelationID") public String correlationId;
}
