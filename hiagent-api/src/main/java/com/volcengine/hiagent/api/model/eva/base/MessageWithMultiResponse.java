package com.volcengine.hiagent.api.model.eva.base;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MessageWithMultiResponse {
    private String Role;
    @Nullable
    private List<CellContentPart> Content;
    @Nullable
    private List<List<CellContentPart>> Contents;

    public MessageWithMultiResponse() {
    }

    public MessageWithMultiResponse(String role, @Nullable List<CellContentPart> content, @Nullable List<List<CellContentPart>> contents) {
        Role = role;
        Content = content;
        Contents = contents;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    @Nullable
    public List<CellContentPart> getContent() {
        return Content;
    }

    public void setContent(@Nullable List<CellContentPart> content) {
        Content = content;
    }

    @Nullable
    public List<List<CellContentPart>> getContents() {
        return Contents;
    }

    public void setContents(@Nullable List<List<CellContentPart>> contents) {
        Contents = contents;
    }
}
