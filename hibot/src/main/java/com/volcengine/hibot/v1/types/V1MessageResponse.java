package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1MessageResponse {
    @JsonProperty("ID") public String id;
    @JsonProperty("SessionID") public String sessionId;
    @JsonProperty("RunID") public String runId;
    @JsonProperty("Role") public String role;
    @JsonProperty("Content") public String content;
    @JsonProperty("ToolCalls") public V1RawJSON toolCalls;
    @JsonProperty("ToolResult") public V1RawJSON toolResult;
    @JsonProperty("Metadata") public V1RawJSON metadata;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("Visibility") public String visibility;
    @JsonProperty("Files") public List<V1MessageFile> files;
    @JsonProperty("EventType") public String eventType;
    @JsonProperty("Payload") public V1RawJSON payload;
    @JsonProperty("Sequence") public Long sequence;
    @JsonProperty("PayloadJSON") public String payloadJson;
    @JsonProperty("TokenCount") public Long tokenCount;
    @JsonProperty("TraceID") public String traceId;
}
