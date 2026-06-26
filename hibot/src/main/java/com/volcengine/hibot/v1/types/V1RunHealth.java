package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1RunHealth {
    @JsonProperty("RunningCount") public Long runningCount;
    @JsonProperty("Last24hTotal") public Long last24HTotal;
    @JsonProperty("Last24hSuccessRate") public Double last24HSuccessRate;
    @JsonProperty("Last24hAvgDurationMs") public Long last24HAvgDurationMs;
    @JsonProperty("Last24hFailed") public Long last24HFailed;
    @JsonProperty("HourlyBuckets") public List<V1RunHourlyBucket> hourlyBuckets;
}
