package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1TraceSummary {
    @JsonProperty("TraceID") public String traceId;
    @JsonProperty("Name") public String name;
    @JsonProperty("StartTime") public String startTime;
    @JsonProperty("EndTime") public String endTime;
    @JsonProperty("DurationMs") public Long durationMs;
    @JsonProperty("AgentID") public String agentId;
    @JsonProperty("AgentName") public String agentName;
    @JsonProperty("StatusCode") public String statusCode;
    @JsonProperty("ServiceName") public String serviceName;
    @JsonProperty("Attributes") public V1TraceAttributes attributes;
}
