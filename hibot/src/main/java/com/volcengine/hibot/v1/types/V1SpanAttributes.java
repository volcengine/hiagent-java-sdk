package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1SpanAttributes {
    @JsonProperty("MessageRole") public String messageRole;
    @JsonProperty("HttpMethod") public String httpMethod;
    @JsonProperty("HttpUrl") public String httpUrl;
    @JsonProperty("HttpStatusCode") public Long httpStatusCode;
    @JsonProperty("DbName") public String dbName;
    @JsonProperty("MessagingSystem") public String messagingSystem;
    @JsonProperty("GenAiSystem") public String genAiSystem;
    @JsonProperty("GenAiType") public String genAiType;
    @JsonProperty("GenAiRequestModel") public String genAiRequestModel;
    @JsonProperty("GenAiResponseModel") public String genAiResponseModel;
    @JsonProperty("GenAiOperationName") public String genAiOperationName;
    @JsonProperty("GenAiConversationId") public String genAiConversationId;
    @JsonProperty("GenAiProviderName") public String genAiProviderName;
    @JsonProperty("GenAiInputMessages") public String genAiInputMessages;
    @JsonProperty("GenAiOutputMessages") public String genAiOutputMessages;
    @JsonProperty("GenAiSystemInstructions") public String genAiSystemInstructions;
    @JsonProperty("GenAiToolDefinitions") public String genAiToolDefinitions;
    @JsonProperty("GenAiToolCallArguments") public String genAiToolCallArguments;
    @JsonProperty("GenAiToolCallResult") public String genAiToolCallResult;
    @JsonProperty("GenAiRequestTemperature") public Double genAiRequestTemperature;
    @JsonProperty("GenAiRequestTopP") public Double genAiRequestTopP;
    @JsonProperty("GenAiRequestMaxTokens") public Long genAiRequestMaxTokens;
    @JsonProperty("GenAiRequestFrequencyPenalty") public Double genAiRequestFrequencyPenalty;
    @JsonProperty("GenAiRequestPresencePenalty") public Double genAiRequestPresencePenalty;
    @JsonProperty("GenAiRequestSeed") public Long genAiRequestSeed;
    @JsonProperty("GenAiRetrievalQueryText") public String genAiRetrievalQueryText;
    @JsonProperty("GenAiRetrievalDocuments") public String genAiRetrievalDocuments;
    @JsonProperty("GenAiDataSourceId") public String genAiDataSourceId;
    @JsonProperty("GenAiUsageInputTokens") public Long genAiUsageInputTokens;
    @JsonProperty("GenAiUsageInputTokensCost") public Double genAiUsageInputTokensCost;
    @JsonProperty("GenAiUsageOutputTokens") public Long genAiUsageOutputTokens;
    @JsonProperty("GenAiUsageOutputTokensCost") public Double genAiUsageOutputTokensCost;
    @JsonProperty("GenAiUsageTotalTokens") public Long genAiUsageTotalTokens;
    @JsonProperty("GenAiUsageTotalTokensCost") public Double genAiUsageTotalTokensCost;
    @JsonProperty("GenAiUsageCacheCreationInputTokens") public Long genAiUsageCacheCreationInputTokens;
    @JsonProperty("GenAiUsageCacheReadInputTokens") public Long genAiUsageCacheReadInputTokens;
    @JsonProperty("GenAiInputRaw") public String genAiInputRaw;
    @JsonProperty("GenAiOutputRaw") public String genAiOutputRaw;
}
