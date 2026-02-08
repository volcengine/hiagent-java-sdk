package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

public class DownloadKeyResponse {
  /**
   * 文件 key
   * 示例: "filekey"
   */
  @SerializedName("Key")
  private String key = null;

  public DownloadKeyResponse() {
  }

  public DownloadKeyResponse(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }
}
