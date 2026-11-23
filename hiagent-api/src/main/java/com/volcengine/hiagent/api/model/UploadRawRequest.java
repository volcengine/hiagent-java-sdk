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
   * 关联的过期时间 (可选)，Go duration 格式，如 "15h"、"30m"；
   * 不设置、空串、"0" 或 "-1" 表示不过期
   * 示例: "15h"
   */
  @SerializedName("Expire")
  @Nullable
  private String expire = null;

  /**
   * 文件 ID (必填，不能为空串)，删除文件时要用同一个 ID
   * 示例: "wcxxxxxxxxxxxxxxxxxxx"
   */
  @SerializedName("Id")
  private String id = null;

  /**
   * 文件的 MIME 类型 (可选)，作为 Content-Type 请求头发送，不设置时为 "application/octet-stream"。
   * 建议填文件的真实类型：UP 按内容只存一份类型，同一内容最后一次上传声明的类型会覆盖之前的值
   * 示例: "image/png"
   */
  @SerializedName("ContentType")
  @Nullable
  private String contentType = null;

  /**
   * 文件 SHA-256 的十六进制串 (可选)；不设置时 SDK 先读一遍文件计算，发送前去空白并转小写
   * 示例: "84add60e154d3625fa5424bfb98bdc5782a078853a4a2c0845b906985ebceb8c"
   */
  @SerializedName("Sha256")
  private String sha256 = null;

  /**
   * 文件名 (可选)，写入本次关联的元数据；含 UP 无法存储的字符时返回 400
   * 示例: "报告.pdf"
   */
  @SerializedName("Filename")
  @Nullable
  private String filename = null;

  /**
   * 是否为图片额外保存一份压缩副本 (可选)；只对 UP 配置的图片类型和大小生效
   */
  @SerializedName("ImageCompress")
  @Nullable
  private Boolean imageCompress = null;

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

  @Nullable
  public String getFilename() {
    return filename;
  }

  public void setFilename(@Nullable String filename) {
    this.filename = filename;
  }

  @Nullable
  public Boolean getImageCompress() {
    return imageCompress;
  }

  public void setImageCompress(@Nullable Boolean imageCompress) {
    this.imageCompress = imageCompress;
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
