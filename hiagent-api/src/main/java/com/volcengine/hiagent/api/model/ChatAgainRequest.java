package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatAgainRequest {

    @SerializedName("UserID")
    private String userID;

    @SerializedName("AppConversationID")
    private String appConversationID;

    @SerializedName("MessageID")
    private String messageID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAppConversationID() {
        return appConversationID;
    }

    public void setAppConversationID(String appConversationID) {
        this.appConversationID = appConversationID;
    }
}
