package com.volcengine.hiagent.api.model.eva.base;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Message {
    private String Role;
    @Nullable
    private List<CellContentPart> Content;

    public Message() {
    }

    public Message(String role, @Nullable List<CellContentPart> content) {
        Role = role;
        Content = content;
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
}
