package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1RiskAttention {
    @JsonProperty("AbnormalChannels") public List<V1ChannelRiskItem> abnormalChannels;
    @JsonProperty("FailedCronJobs") public List<V1CronJobRiskItem> failedCronJobs;
    @JsonProperty("FailedRunsLast24h") public Long failedRunsLast24H;
}
