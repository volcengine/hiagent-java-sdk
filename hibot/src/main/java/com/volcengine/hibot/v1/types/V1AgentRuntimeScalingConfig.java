package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1AgentRuntimeScalingConfig {
    @JsonProperty("MinReplicas") public Integer minReplicas;
    @JsonProperty("MaxReplicas") public Integer maxReplicas;
    @JsonProperty("TargetConcurrencyPerReplica") public Integer targetConcurrencyPerReplica;
    @JsonProperty("IdleScaleToZeroAfterSeconds") public Long idleScaleToZeroAfterSeconds;
    @JsonProperty("ScaleDownStabilizationWindowSeconds") public Long scaleDownStabilizationWindowSeconds;
}
