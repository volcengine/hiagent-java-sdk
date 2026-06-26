package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1SpanSummary {
    @JsonProperty("TraceID") public String traceId;
    @JsonProperty("SpanID") public String spanId;
    @JsonProperty("ParentSpanID") public String parentSpanId;
    @JsonProperty("Name") public String name;
    @JsonProperty("Kind") public String kind;
    @JsonProperty("StartTime") public String startTime;
    @JsonProperty("EndTime") public String endTime;
    @JsonProperty("DurationMs") public Long durationMs;
    @JsonProperty("StatusCode") public String statusCode;
    @JsonProperty("StatusMessage") public String statusMessage;
    @JsonProperty("ServiceName") public String serviceName;
}
