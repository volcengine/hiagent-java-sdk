package com.volcengine.hibot.v1.types;

public final class V1UploadBlobParams {
    public String filename;
    public String contentType;

    public V1UploadBlobParams() {}

    public V1UploadBlobParams(String filename, String contentType) {
        this.filename = filename;
        this.contentType = contentType;
    }
}
