package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.ChatEvent;

import java.util.List;
import java.util.Map;

public class QARetrieveEndChatEvent extends ChatEvent {

    @SerializedName("latency")
    private Double latency;

    @SerializedName("docs")
    private Map<String, String> docs;

    @SerializedName("query")
    private String query;

    @SerializedName("message_id")
    private String messageId;

    public Double getLatency() {
        return latency;
    }

    public void setLatency(Double latency) {
        this.latency = latency;
    }

    public Map<String, String> getDocs() {
        return docs;
    }

    public void setDocs(Map<String, String> docs) {
        this.docs = docs;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
