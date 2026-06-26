package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1GetOverviewResponse {
    @JsonProperty("Summary") public V1KPISummary summary;
    @JsonProperty("RunHealth") public V1RunHealth runHealth;
    @JsonProperty("Usage") public V1UsageDistribution usage;
    @JsonProperty("Risk") public V1RiskAttention risk;
}
