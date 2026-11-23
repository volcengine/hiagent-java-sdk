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
   * 文件的大小，单位为字节。UP 返回 int64，超过 2 GiB 的文件也能解析。
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

  public LongLiveResponse() {
  }

  public LongLiveResponse(String path, Integer size) {
    this.path = path;
    this.size = size == null ? null : size.longValue();
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  /**
   * @return the size in bytes
   * @throws ArithmeticException if the size does not fit an {@code int}
   * @deprecated UP returns a 64-bit size; use {@link #getSizeAsLong()}.
   */
  @Deprecated
  public Integer getSize() {
    return size == null ? null : Math.toIntExact(size);
  }

  /**
   * @deprecated use {@link #setSizeAsLong(Long)}.
   */
  @Deprecated
  public void setSize(Integer size) {
    this.size = size == null ? null : size.longValue();
  }

  public Long getSizeAsLong() {
    return size;
  }

  public void setSizeAsLong(Long size) {
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
