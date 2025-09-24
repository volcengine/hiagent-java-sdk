package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.ChatEvent;
import com.volcengine.hiagent.api.model.base.WorkflowEvent;

public class MessageWorkflowEvent extends WorkflowEvent {

    @SerializedName("think_message_id")
    private String thinkMessageId;

    @SerializedName("answer")
    private String answer;

    @SerializedName("created_at")
    private Integer createdAt;

    public String getThinkMessageId() {
        return thinkMessageId;
    }

    public void setThinkMessageId(String thinkMessageId) {
        this.thinkMessageId = thinkMessageId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }
}
