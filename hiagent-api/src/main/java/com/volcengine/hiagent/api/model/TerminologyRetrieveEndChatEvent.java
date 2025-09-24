package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.ChatEvent;

import java.util.List;
import java.util.Map;

public class TerminologyRetrieveEndChatEvent extends ChatEvent {

    @SerializedName("message_id")
    private String messageId;

    @SerializedName("docs")
    private Map<String, String> docs;

    @SerializedName("latency")
    private Double latency;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Map<String, String> getDocs() {
        return docs;
    }

    public void setDocs(Map<String, String> docs) {
        this.docs = docs;
    }

    public Double getLatency() {
        return latency;
    }

    public void setLatency(Double latency) {
        this.latency = latency;
    }
}
