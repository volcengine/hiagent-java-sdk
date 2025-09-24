package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.WorkflowEvent;

public class ToolMessageOutputStartWorkflowEvent extends WorkflowEvent {

    @SerializedName("message_title")
    private String messageTitle;

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }
}
