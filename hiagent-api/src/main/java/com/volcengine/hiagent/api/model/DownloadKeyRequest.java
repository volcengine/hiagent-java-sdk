package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

public class DownloadKeyRequest {
  /**
   * 文件路径
   * 示例: "/path/to/file"
   */
  @SerializedName("path")
  private String path = null;

  /**
   * 文件SHA256
   */
  @SerializedName("sha256")
  private String sha256 = null;

  public DownloadKeyRequest() {
  }

  public DownloadKeyRequest(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getSha256() {
    return sha256;
  }

  public void setSha256(String sha256) {
    this.sha256 = sha256;
  }
}
