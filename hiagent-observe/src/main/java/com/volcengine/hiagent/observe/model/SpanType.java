package com.volcengine.hiagent.observe.model;

import com.google.gson.annotations.SerializedName;

public enum SpanType {
  // START 表示开始调用模型或者工作流
  @SerializedName("start")
  START("start"),

  // END 停止调用模型或者工作流
  @SerializedName("end")
  END("end"),

  // LLM 大模型调用节点
  @SerializedName("core_llm")
  CORE_LLM("core_llm"),

  // QA 代表问答库调用节点
  @SerializedName("qa")
  QA("qa"),

  // KNOWLEDGE 代表知识库调用节点
  @SerializedName("knowledge")
  KNOWLEDGE("knowledge"),

  // WORKFLOW 代表工作流的调用节点
  @SerializedName("workflow")
  WORKFLOW("workflow"),

  // WORKFLOW_NODE 代表工作流中的节点调用
  @SerializedName("workflow_node")
  WORKFLOW_NODE("workflow_node"),

  // RERANK 代表 rerank 模型调用节点
  @SerializedName("core_rerank")
  CORE_RERANK("core_rerank"),

  // EMBEDDINGS 代表向量化模型调用节点
  @SerializedName("core_embeddings")
  CORE_EMBEDDINGS("core_embeddings"),

  // TOOL 代表工具调用节点
  @SerializedName("tool")
  TOOL("tool"),

  // TERMINOLOGY 代表术语库调用节点
  @SerializedName("terminology")
  TERMINOLOGY("terminology"),

  // DATABASE 代表数据库调用节点
  @SerializedName("database")
  DATABASE("database");

  private final String value;

  SpanType(String value) {
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
