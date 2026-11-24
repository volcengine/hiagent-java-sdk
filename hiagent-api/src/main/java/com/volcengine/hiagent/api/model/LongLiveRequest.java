package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

public class LongLiveRequest {
  /**
   * 文件路径
   * 示例: "/path/to/file"
   */
  @SerializedName("Path")
  private String path = null;

  /**
   * 文件 ID (必填)，删除文件时要用同一个 ID。序列化为 UP 契约中的 "ID"；
   * 反序列化同时接受旧版 SDK 写出的 "Id"
   * 示例: "wcxxxxxxxxxxxxxxxxxxx"
   */
  @SerializedName(value = "ID", alternate = {"Id"})
  private String id = null;

  /**
   * 文件名 (可选)，写入本次关联的元数据；含 UP 无法存储的字符时返回 400
   * 示例: "报告.pdf"
   */
  @SerializedName("Filename")
  private String filename = null;

  public LongLiveRequest() {
  }

  public LongLiveRequest(String path, String id) {
    this.path = path;
    this.id = id;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }
}
