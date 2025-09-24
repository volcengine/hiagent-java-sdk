package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.ChatEvent;

public class AgentJumpChatEvent extends ChatEvent {

    @SerializedName("jump_reason")
    private String jumpReason;

    @SerializedName("jump_latency")
    private Double jumpLatency;

    @SerializedName("jump_to")
    private String jumpTo;

    @SerializedName("message_id")
    private String messageId;

    public String getJumpReason() {
        return jumpReason;
    }

    public void setJumpReason(String jumpReason) {
        this.jumpReason = jumpReason;
    }

    public Double getJumpLatency() {
        return jumpLatency;
    }

    public void setJumpLatency(Double jumpLatency) {
        this.jumpLatency = jumpLatency;
    }

    public String getJumpTo() {
        return jumpTo;
    }

    public void setJumpTo(String jumpTo) {
        this.jumpTo = jumpTo;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
