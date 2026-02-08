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
   * 文件 ID
   * 示例: "wcxxxxxxxxxxxxxxxxxxx"
   */
  @SerializedName("Id")
  private String id = null;

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
}
