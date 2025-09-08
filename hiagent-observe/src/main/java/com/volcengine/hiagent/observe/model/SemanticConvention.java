package com.volcengine.hiagent.observe.model;

import com.google.gson.annotations.SerializedName;

public enum SemanticConvention {
  @SerializedName("app_id")
  APP_ID("app_id"),

  @SerializedName("workspace_id")
  WORKSPACE_ID("workspace_id"),

  @SerializedName("user_id")
  USER_ID("user_id"),

  @SerializedName("tenant_id")
  TENANT_ID("tenant_id"),

  @SerializedName("conversation_id")
  CONVERSATION_ID("conversation_id"),

  @SerializedName("message_id")
  MESSAGE_ID("message_id"),

  @SerializedName("model_id")
  MODEL_ID("model_id"),

  @SerializedName("model_name")
  MODEL_NAME("model_name"),

  @SerializedName("model_provider")
  MODEL_PROVIDER("model_provider"),

  @SerializedName("node_id")
  NODE_ID("node_id"),

  @SerializedName("workflow_id")
  WORKFLOW_ID("workflow_id"),

  @SerializedName("top_p")
  TOP_P("top_p"),

  @SerializedName("temperature")
  TEMPERATURE("temperature"),

  @SerializedName("top_k")
  TOP_K("top_k"),

  @SerializedName("stream")
  STREAM("stream"),

  @SerializedName("span_type")
  SPAN_TYPE("span_type"),

  @SerializedName("request_id")
  REQUEST_ID("request_id"),

  @SerializedName("latency")
  LATENCY("latency"),

  @SerializedName("latency_first_resp")
  LATENCY_FIRST_RESP("latency_first_resp"),

  @SerializedName("input")
  INPUT("input"),

  @SerializedName("input_raw")
  INPUT_RAW("input_raw"),

  @SerializedName("input_tokens")
  INPUT_TOKENS("input_tokens"),

  @SerializedName("input_price")
  INPUT_PRICE("input_price"),

  @SerializedName("currency")
  CURRENCY("currency"),

  @SerializedName("output")
  OUTPUT("output"),

  @SerializedName("output_raw")
  OUTPUT_RAW("output_raw"),

  @SerializedName("output_tokens")
  OUTPUT_TOKENS("output_tokens"),

  @SerializedName("output_price")
  OUTPUT_PRICE("output_price"),

  @SerializedName("price_unit")
  PRICE_UNIT("price_unit");

  private final String value;

  SemanticConvention(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }
}
// APP_ID: str = "app_id"
// """
// APP_ID 是应用中的保留字，不允许设置，如果设置，将会在 collector 中被覆盖为当前自定义应用的 ID
// """

// WORKSPACE_ID: str = "workspace_id"
// """
// WORKSPACE_ID 是工作区的保留字，是 HiAgent 平台内工作区的唯一标识符，如果设置，将会在 collector
// 中被覆盖为当前自定义应用所在工作区的 ID
// """

// USER_ID: str = "user_id"
// """
// USER_ID 当前会话的用户 ID
// """

// TENANT_ID: str = "tenant_id"
// """
// TENANT_ID 是当前会话的租户 ID
// """

// CONVERSATION_ID: str = "conversation_id"
// """
// Conversation ID 是会话的保留字，是用户在一个有完整上下文的会话唯一标识符
// """

// MESSAGE_ID: str = "message_id"
// """
// Message ID 是会话中的保留字，是用户在一个问答中的唯一标识符
// """

// MODEL_ID: str = "model_id"
// """
// Model ID 是在对话中使用的模型的 ID
// """

// MODEL_NAME: str = "model_name"
// """
// Model Name 是在对话中使用的模型名称
// """

// MODEL_PROVIDER: str = "model_provider"
// """
// Model Provider 是模型提供者的保留字，是模型提供者的名称
// """

// NODE_ID: str = "node_id"
// """
// Node ID 如果在工作流中则是工作流的节点 ID
// """

// WORKFLOW_ID: str = "workflow_id"
// """
// Workflow ID 如果在有工作流中的工作流 ID
// """

// TOP_P: str = "top_p"
// """
// TOP_P 是模型生成的概率阈值
// """

// TEMPERATURE: str = "temperature"
// """
// TEMPERATURE 是模型生成的温度阈值
// """

// TOP_K: str = "top_k"
// """
// TOP_K 是知识库的召回数量阈值
// """

// STREAM: str = "stream"
// """
// STREAM 是否是流式返回
// """

// SPAN_TYPE: str = "span_type"
// """
// SPAN_TYPE 是 span 的类型，用于在页面上拆分不同的展示类型
// """

// REQUEST_ID: str = "request_id"
// """
// Request ID 是请求的唯一标识符
// """

// LATENCY: str = "latency"
// """
// LATENCY 是请求的延迟时间，毫秒为单位
// """

// LATENCT_FIRST_RESP: str = "latency_first_resp"
// """
// LATENCT_FIRST_RESP 是首 token 的延迟时间，毫秒为单位
// """

// INPUT: str = "input"
// """
// INPUT 是用户输入
// """

// INPUT_RAW: str = "input_raw"
// """
// INPUT_RAW 是用户输入的原始数据
// """

// INPUT_TOKENS: str = "input_tokens"
// """
// INPUT_TOKENS 是用户输入的 token 数目
// """

// INPUT_PRICE: str = "input_price"
// """
// INPUT_PRICE 是用户输入的 token 价格
// """

// CURRENCY: str = "currency"
// """
// CURRENCY 是用户输入的货币类型，可选：RMB，USD
// """

// OUTPUT: str = "output"
// """
// OUTPUT 是模型生成的结果
// """

// OUTPUT_RAW: str = "output_raw"
// """
// OUTPUT_RAW 是模型生成的结果的原始数据
// """

// OUTPUT_TOKENS: str = "output_tokens"
// """
// OUTPUT_TOKENS 是模型生成的结果的 token 数目
// """

// OUTPUT_PRICE: str = "output_price"
// """
// OUTPUT_PRICE 是模型生成的结果的价格
// """

// PRICE_UNIT: str = "price_unit"
// """
// PRICE_UNIT 是模型生成的结果的价格单位
// """
