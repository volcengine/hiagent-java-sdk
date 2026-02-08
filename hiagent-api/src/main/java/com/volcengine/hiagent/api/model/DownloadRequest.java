package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

public class DownloadRequest {
  /**
   * 文件 key
   * 示例: "filekey"
   */
  @SerializedName("Key")
  private String key = null;

  /**
   * 文件路径
   * 示例: "/path/to/file"
   */
  @SerializedName("Path")
  private String path = null;

  /**
   * 保存路径
   * 示例: "/path/to/file"
   */
  @SerializedName("SaveTo")
  private String saveTo = null;

  public DownloadRequest() {
  }

  public DownloadRequest(String key, String path, String saveTo) {
    this.key = key;
    this.path = path;
    this.saveTo = saveTo;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getSaveTo() {
    return saveTo;
  }

  public void setSaveTo(String saveTo) {
    this.saveTo = saveTo;
  }
}
