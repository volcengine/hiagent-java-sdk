package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

public class DeleteRequest {
  /**
   * 文件 ID
   * 示例: "wcxxxxxxxxxxxxxxxxxxx"
   */
  @SerializedName("Id")
  private String id = null;

  /**
   * 文件的哈希值
   * 示例: "sha256hashvalue"
   */
  @SerializedName("Sha256")
  private String sha256 = null;

  public DeleteRequest() {
  }

  public DeleteRequest(String id, String sha256) {
    this.id = id;
    this.sha256 = sha256;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSha256() {
    return sha256;
  }

  public void setSha256(String sha256) {
    this.sha256 = sha256;
  }
}
