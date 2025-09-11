package com.volcengine.hiagent.observe.model;

import com.google.gson.annotations.SerializedName;

public enum SemanticConvention {
  /**
   * APP_ID 是应用中的保留字，不允许设置，如果设置，将会在 collector 中被覆盖为当前自定义应用的 ID
   */
  @SerializedName("app_id")
  APP_ID("app_id"),

  /**
   * WORKSPACE_ID 是工作区的保留字，是 HiAgent 平台内工作区的唯一标识符，如果设置，将会在 collector
   * 中被覆盖为当前自定义应用所在工作区的 ID
   */
  @SerializedName("workspace_id")
  WORKSPACE_ID("workspace_id"),

  /**
   * USER_ID 当前会话的用户 ID
   */
  @SerializedName("user_id")
  USER_ID("user_id"),

  /**
   * TENANT_ID 是当前会话的租户 ID
   */
  @SerializedName("tenant_id")
  TENANT_ID("tenant_id"),

  /**
   * Conversation ID 是会话的保留字，是用户在一个有完整上下文的会话唯一标识符
   */
  @SerializedName("conversation_id")
  CONVERSATION_ID("conversation_id"),

  /**
   * Message ID 是会话中的保留字，是用户在一个问答中的唯一标识符
   */
  @SerializedName("message_id")
  MESSAGE_ID("message_id"),

  /**
   * Model ID 是在对话中使用的模型的 ID
   */
  @SerializedName("model_id")
  MODEL_ID("model_id"),

  /**
   * Model Name 是在对话中使用的模型名称
   */
  @SerializedName("model_name")
  MODEL_NAME("model_name"),

  /**
   * Start Time 是会话开始的时间
   */
  @SerializedName("start_time")
  START_TIME("start_time"),

  /**
   * End Time 是会话结束的时间
   */
  @SerializedName("end_time")
  END_TIME("end_time"),

  /**
   * Model Provider 是模型提供者的保留字，是模型提供者的名称
   */
  @SerializedName("model_provider")
  MODEL_PROVIDER("model_provider"),

  /**
   * Node ID 如果在工作流中则是工作流的节点 ID
   */
  @SerializedName("node_id")
  NODE_ID("node_id"),

  /**
   * Workflow ID 如果在有工作流中的工作流 ID
   */
  @SerializedName("workflow_id")
  WORKFLOW_ID("workflow_id"),

  /**
   * TOP_P 是模型生成的概率阈值
   */
  @SerializedName("top_p")
  TOP_P("top_p"),

  /**
   * TEMPERATURE 是模型生成的温度阈值
   */
  @SerializedName("temperature")
  TEMPERATURE("temperature"),

  /**
   * TOP_K 是知识库的召回数量阈值
   */
  @SerializedName("top_k")
  TOP_K("top_k"),

  /**
   * STREAM 是否是流式返回
   */
  @SerializedName("stream")
  STREAM("stream"),

  /**
   * SPAN_TYPE 是 span 的类型，用于在页面上拆分不同的展示类型
   */
  @SerializedName("span_type")
  SPAN_TYPE("span_type"),

  /**
   * Request ID 是请求的唯一标识符
   */
  @SerializedName("request_id")
  REQUEST_ID("request_id"),

  /**
   * LATENCY 是请求的延迟时间，毫秒为单位
   */
  @SerializedName("latency")
  LATENCY("latency"),

  /**
   * LATENCY_FIRST_RESP 是首 token 的延迟时间，毫秒为单位
   */
  @SerializedName("latency_first_resp")
  LATENCY_FIRST_RESP("latency_first_resp"),

  /**
   * INPUT 是用户输入
   */
  @SerializedName("input")
  INPUT("input"),

  /**
   * INPUT_RAW 是用户输入的原始数据
   */
  @SerializedName("input_raw")
  INPUT_RAW("input_raw"),

  /**
   * INPUT_TOKENS 是用户输入的 token 数目
   */
  @SerializedName("input_tokens")
  INPUT_TOKENS("input_tokens"),

  /**
   * INPUT_PRICE 是用户输入的 token 价格
   */
  @SerializedName("input_price")
  INPUT_PRICE("input_price"),

  /**
   * CURRENCY 是用户输入的货币类型，可选：RMB，USD
   */
  @SerializedName("currency")
  CURRENCY("currency"),

  /**
   * OUTPUT 是模型生成的结果
   */
  @SerializedName("output")
  OUTPUT("output"),

  /**
   * OUTPUT_RAW 是模型生成的结果的原始数据
   */
  @SerializedName("output_raw")
  OUTPUT_RAW("output_raw"),

  /**
   * OUTPUT_TOKENS 是模型生成的结果的 token 数目
   */
  @SerializedName("output_tokens")
  OUTPUT_TOKENS("output_tokens"),

  /**
   * OUTPUT_PRICE 是模型生成的结果的价格
   */
  @SerializedName("output_price")
  OUTPUT_PRICE("output_price"),

  /**
   * PRICE_UNIT 是模型生成的结果的价格单位
   */
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
