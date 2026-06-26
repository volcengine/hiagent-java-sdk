package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1AgentRuntimeStatus {
    @JsonProperty("Status") public String status;
    @JsonProperty("Ready") public Boolean ready;
    @JsonProperty("Reason") public String reason;
    @JsonProperty("Message") public String message;
    @JsonProperty("Replicas") public Integer replicas;
    @JsonProperty("ReadyReplicas") public Integer readyReplicas;
    @JsonProperty("DesiredReplicas") public Integer desiredReplicas;
    @JsonProperty("Instances") public List<V1AgentRuntimeInstanceStatus> instances;
    @JsonProperty("UpdatedAt") public String updatedAt;
}
