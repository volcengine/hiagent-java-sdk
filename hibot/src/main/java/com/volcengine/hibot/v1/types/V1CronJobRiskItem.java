package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1CronJobRiskItem {
    @JsonProperty("CronJobID") public String cronJobId;
    @JsonProperty("Name") public String name;
    @JsonProperty("AgentID") public String agentId;
    @JsonProperty("LastStatus") public String lastStatus;
    @JsonProperty("LastRunAt") public String lastRunAt;
}
