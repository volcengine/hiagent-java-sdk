package com.volcengine.hiagent.api.model.base;

import com.google.gson.annotations.SerializedName;

public class ChatEvent {

    @SerializedName("event")
    private String event;

    @SerializedName("task_id")
    private String taskId;

    @SerializedName("id")
    private String id;

    @SerializedName("conversation_id")
    private String conversationId;

    @SerializedName("created_at")
    private Integer createdAt;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }
}
