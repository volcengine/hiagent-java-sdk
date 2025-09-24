package com.volcengine.hiagent.api.model;


import com.google.gson.annotations.SerializedName;

public class FileInfo {

    @SerializedName("Path")
    private String path;

    @SerializedName("Name")
    private String name;

    @SerializedName("Size")
    private Long size;

    @SerializedName("Url")
    private String url;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
