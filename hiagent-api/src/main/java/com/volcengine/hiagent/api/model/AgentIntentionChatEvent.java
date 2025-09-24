package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.ChatEvent;

import java.util.Map;

public class AgentIntentionChatEvent extends ChatEvent {
    @SerializedName("message_id")
    private String messageId;
    @SerializedName("intention")
    private String intention;
    @SerializedName("data")
    private Map<String, String> data;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getIntention() {
        return intention;
    }

    public void setIntention(String intention) {
        this.intention = intention;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
