// Copyright (c) 2024 Bytedance Ltd. and/or its affiliates
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

public class UploadRawRequest {
  /**
   * 文件的过期时间 (可选)
   * 示例: "15h"
   */
  @SerializedName("Expire")
  @Nullable
  private String expire = null;

  /**
   * 文件 ID (必填)
   * 示例: "wcxxxxxxxxxxxxxxxxxxx"
   */
  @SerializedName("Id")
  private String id = null;

  /**
   * content type
   * 示例: "application/octet-stream"
   */
  @SerializedName("ContentType")
  @Nullable
  private String contentType = null;

  /**
   * 文件的哈希值
   * 示例: "sha256hashvalue"
   */
  @SerializedName("Sha256")
  private String sha256 = null;

  public UploadRawRequest() {
  }

  public UploadRawRequest(String id, String sha256) {
    this.id = id;
    this.sha256 = sha256;
  }

  public UploadRawRequest(@Nullable String expire, String id, @Nullable String contentType, String sha256) {
    this.expire = expire;
    this.id = id;
    this.contentType = contentType;
    this.sha256 = sha256;
  }

  @Nullable
  public String getExpire() {
    return expire;
  }

  public void setExpire(@Nullable String expire) {
    this.expire = expire;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Nullable
  public String getContentType() {
    return contentType;
  }

  public void setContentType(@Nullable String contentType) {
    this.contentType = contentType;
  }

  public String getSha256() {
    return sha256;
  }

  public void setSha256(String sha256) {
    this.sha256 = sha256;
  }
}
// class UploadRawRequest(BaseModel):
// Expire: str = Field(
// title="文件的过期时间", description="文件的过期时间", example="15h"
// )
// Id: str = Field(
// title="文件 ID", description="文件 ID", example="wcxxxxxxxxxxxxxxxxxxx"
// )
// ContentType: str = Field(
// title="content type",
// description="content type",
// example="application/octet-stream",
// )
// Sha256: str = Field(
// title="文件的哈希值", description="文件的哈希值", example="sha256hashvalue"
// )
