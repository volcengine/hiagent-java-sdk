package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Sent as the JSON body of {@code Download}, except {@code saveTo}, which stays
 * local. UP needs {@code Path} or {@code Sha256}.
 *
 * <p>UP's {@code Range} is deliberately not exposed: {@code download} replaces
 * {@code saveTo}, so resuming with {@code bytes=N-} would silently keep only
 * the tail. Range support belongs with streaming downloads, which can write at
 * an offset.
 */
public class DownloadRequest {
  /**
   * 下载 Key，由 DownloadKey 获取
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
   * 文件 SHA-256 的十六进制串，未设置 Path 时使用；发送前去空白并转小写
   */
  @SerializedName("Sha256")
  private String sha256 = null;

  /**
   * 文件 ID (可选)，用于取该关联记录的文件名
   */
  @SerializedName("ID")
  private String id = null;

  /**
   * 是否下载图片的压缩副本 (可选)；没有压缩副本时返回原文件
   */
  @SerializedName("ImageCompress")
  private Boolean imageCompress = null;

  /**
   * 下载文件名 (可选)；ID 对应的关联没有记录文件名时，用于响应的 Content-Disposition
   */
  @SerializedName("Filename")
  private String filename = null;

  /**
   * 本地保存路径，不会发送给服务端
   * 示例: "/path/to/file"
   */
  private transient String saveTo = null;

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

  public String getSha256() {
    return sha256;
  }

  public void setSha256(String sha256) {
    this.sha256 = sha256;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Boolean getImageCompress() {
    return imageCompress;
  }

  public void setImageCompress(Boolean imageCompress) {
    this.imageCompress = imageCompress;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public String getSaveTo() {
    return saveTo;
  }

  public void setSaveTo(String saveTo) {
    this.saveTo = saveTo;
  }
}
