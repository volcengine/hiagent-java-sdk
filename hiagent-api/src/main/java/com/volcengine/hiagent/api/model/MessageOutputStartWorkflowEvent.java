package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.ChatEvent;
import com.volcengine.hiagent.api.model.base.WorkflowEvent;

public class MessageOutputStartWorkflowEvent extends WorkflowEvent {

    @SerializedName("think_message_id")
    private String thinkMessageId;

    public String getThinkMessageId() {
        return thinkMessageId;
    }

    public void setThinkMessageId(String thinkMessageId) {
        this.thinkMessageId = thinkMessageId;
    }
}
