package com.volcengine.hiagent.api.model;


import com.google.gson.annotations.SerializedName;

public class Conversation {

    @SerializedName("AppConversationID")
    private String appConversationID;

    @SerializedName("ConversationName")
    private String conversationName;

    @SerializedName("CreateTime")
    private String createTime;

    @SerializedName("LastChatTime")
    private String lastChatTime;

    @SerializedName("EmptyConversation")
    private Boolean emptyConversation;

    public String getAppConversationID() {
        return appConversationID;
    }

    public void setAppConversationID(String appConversationID) {
        this.appConversationID = appConversationID;
    }

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastChatTime() {
        return lastChatTime;
    }

    public void setLastChatTime(String lastChatTime) {
        this.lastChatTime = lastChatTime;
    }

    public Boolean getEmptyConversation() {
        return emptyConversation;
    }

    public void setEmptyConversation(Boolean emptyConversation) {
        this.emptyConversation = emptyConversation;
    }
}
