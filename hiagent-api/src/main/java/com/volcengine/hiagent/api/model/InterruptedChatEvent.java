package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.ChatEvent;

public class InterruptedChatEvent extends ChatEvent {
    @SerializedName("message_id")
    private String messageId;
    @SerializedName("interrupted_msg")
    private String interruptedMsg;
    @SerializedName("interrupted_type")
    private String interruptedType;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getInterruptedMsg() {
        return interruptedMsg;
    }

    public void setInterruptedMsg(String interruptedMsg) {
        this.interruptedMsg = interruptedMsg;
    }

    public String getInterruptedType() {
        return interruptedType;
    }

    public void setInterruptedType(String interruptedType) {
        this.interruptedType = interruptedType;
    }
}
