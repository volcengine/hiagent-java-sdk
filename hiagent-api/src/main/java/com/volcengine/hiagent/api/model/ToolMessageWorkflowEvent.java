package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.WorkflowEvent;

public class ToolMessageWorkflowEvent extends WorkflowEvent {

    @SerializedName("answer")
    private String answer;

    @SerializedName("message_title")
    private String messageTitle;

    @SerializedName("created_at")
    private Integer createdAt;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }
}
