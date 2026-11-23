package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Sent as the {@code Path} and {@code Sha256} query parameters; set at least one of them.
 */
public class DownloadKeyRequest {
  /**
   * 文件路径，即上传响应里的 Path
   * 示例: "/path/to/file"
   */
  @SerializedName("Path")
  private String path = null;

  /**
   * 文件 SHA-256 的十六进制串；发送前去空白并转小写
   */
  @SerializedName("Sha256")
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
