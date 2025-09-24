package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

public class ClearMessageRequest {

    @SerializedName("UserID")
    private String userID;

    @SerializedName("AppConversationID")
    private String appConversationID;

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
