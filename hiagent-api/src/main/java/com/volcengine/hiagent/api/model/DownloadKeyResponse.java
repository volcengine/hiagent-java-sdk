package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

public class DownloadKeyResponse {
  /**
   * 临时下载 Key，有效期 10 分钟
   * 示例: "filekey"
   */
  @SerializedName("Key")
  private String key = null;

  /**
   * 文件大小（字节）
   * 示例: 1024
   */
  @SerializedName("Size")
  private Long size = null;

  /**
   * UP 记录的文件 MIME 类型
   * 示例: "application/pdf"
   */
  @SerializedName("ContentType")
  private String contentType = null;

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

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }
}
