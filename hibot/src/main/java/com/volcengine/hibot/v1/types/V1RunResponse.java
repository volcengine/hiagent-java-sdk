package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1RunResponse {
    @JsonProperty("ID") public String id;
    @JsonProperty("TenantID") public String tenantId;
    @JsonProperty("AgentID") public String agentId;
    @JsonProperty("SessionID") public String sessionId;
    @JsonProperty("Status") public String status;
    @JsonProperty("Source") public String source;
    @JsonProperty("Input") public String input;
    @JsonProperty("Output") public String output;
    @JsonProperty("Iterations") public Integer iterations;
    @JsonProperty("ToolCalls") public Integer toolCalls;
    @JsonProperty("TokensInput") public Integer tokensInput;
    @JsonProperty("TokensOutput") public Integer tokensOutput;
    @JsonProperty("Metadata") public V1RawJSON metadata;
    @JsonProperty("StartedAt") public String startedAt;
    @JsonProperty("CompletedAt") public String completedAt;
    @JsonProperty("CreatedAt") public String createdAt;
}
