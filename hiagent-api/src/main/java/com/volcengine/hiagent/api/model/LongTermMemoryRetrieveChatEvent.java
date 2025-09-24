package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.ChatEvent;

import java.util.List;

public class LongTermMemoryRetrieveChatEvent extends ChatEvent {

    @SerializedName("message_id")
    private String messageId;

    @SerializedName("query")
    private String query;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
