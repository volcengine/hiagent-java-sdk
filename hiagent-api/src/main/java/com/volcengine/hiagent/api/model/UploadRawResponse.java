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

public class UploadRawResponse {
  /**
   * 文件在对象存储的真实位置
   * 示例: "/upload/full/xx/xx/xxxxxxx"
   */
  @SerializedName("Path")
  private String path = null;

  /**
   * 文件大小（字节）
   * 示例: 1024
   */
  @SerializedName("Size")
  private Long size = null;

  /**
   * 文件的哈希值
   * 示例: "84add60e154d3625fa5424bfb98bdc5782a078853a4a2c0845b906985ebceb8c"
   */
  @SerializedName("Sha256")
  private String sha256 = null;

  /**
   * 文件短链
   * 示例: "JAVwLYB3XVr"
   */
  @SerializedName("ShortLink")
  private String shortLink = null;

  public UploadRawResponse() {
  }

  public UploadRawResponse(String path, Long size, String sha256, String shortLink) {
    this.path = path;
    this.size = size;
    this.sha256 = sha256;
    this.shortLink = shortLink;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public String getSha256() {
    return sha256;
  }

  public void setSha256(String sha256) {
    this.sha256 = sha256;
  }

  public String getShortLink() {
    return shortLink;
  }

  public void setShortLink(String shortLink) {
    this.shortLink = shortLink;
  }
}
