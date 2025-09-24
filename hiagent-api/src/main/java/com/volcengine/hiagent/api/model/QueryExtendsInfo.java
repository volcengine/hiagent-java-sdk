package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QueryExtendsInfo {

    @SerializedName("Files")
    private List<FileInfo> files;

    public List<FileInfo> getFiles() {
        return files;
    }

    public void setFiles(List<FileInfo> files) {
        this.files = files;
    }
}
