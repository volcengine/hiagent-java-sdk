package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1TraceAttributes {
    @JsonProperty("RawInput") public String rawInput;
    @JsonProperty("InputAttachments") public String inputAttachments;
    @JsonProperty("OutputAttachments") public String outputAttachments;
    @JsonProperty("GenAiUsageInputTokens") public Long genAiUsageInputTokens;
    @JsonProperty("GenAiUsageOutputTokens") public Long genAiUsageOutputTokens;
    @JsonProperty("GenAiUsageInputTokensCost") public Double genAiUsageInputTokensCost;
    @JsonProperty("GenAiUsageOutputTokensCost") public Double genAiUsageOutputTokensCost;
    @JsonProperty("GenAiSessionID") public String genAiSessionId;
    @JsonProperty("GenAiInputRaw") public String genAiInputRaw;
    @JsonProperty("GenAiOutputRaw") public String genAiOutputRaw;
}
