package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1InjectMessageRequest {
    @JsonProperty("Role") public String role;
    @JsonProperty("Content") public String content;
    @JsonProperty("ToolCalls") public V1RawJSON toolCalls;
    @JsonProperty("ToolResult") public V1RawJSON toolResult;
    @JsonProperty("Metadata") public V1RawJSON metadata;
}
