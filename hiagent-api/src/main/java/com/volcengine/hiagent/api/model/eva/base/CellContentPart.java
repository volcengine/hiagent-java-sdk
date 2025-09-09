package com.volcengine.hiagent.api.model.eva.base;

import org.jetbrains.annotations.Nullable;

public class CellContentPart {
    private  String Type;
    @Nullable
    private  String Text;
    @Nullable
    private  FileInfo ImageUrl;
    @Nullable
    private  FileInfo FileUrl;

    public CellContentPart() {
    }

    public CellContentPart(String type, @Nullable String text, @Nullable FileInfo imageUrl, @Nullable FileInfo fileUrl) {
        Type = type;
        Text = text;
        ImageUrl = imageUrl;
        FileUrl = fileUrl;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Nullable
    public String getText() {
        return Text;
    }

    public void setText(@Nullable String text) {
        Text = text;
    }

    @Nullable
    public FileInfo getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(@Nullable FileInfo imageUrl) {
        ImageUrl = imageUrl;
    }

    @Nullable
    public FileInfo getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(@Nullable FileInfo fileUrl) {
        FileUrl = fileUrl;
    }
}
