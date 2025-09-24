package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.ChatEvent;

public class ToolMessageChatEvent extends ChatEvent {
    @SerializedName("message_title")
    private String messageTitle;

    @SerializedName("answer")
    private String answer;

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
