package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

public class LongLiveResponse {
  /**
   * 文件路径
   * 示例: "/path/to/file"
   */
  @SerializedName("Path")
  private String path = null;

  /**
   * 文件的大小，单位为字节
   * 示例: 1024
   */
  @SerializedName("Size")
  private Integer size = null;

  public LongLiveResponse() {
  }

  public LongLiveResponse(String path, Integer size) {
    this.path = path;
    this.size = size;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }
}
