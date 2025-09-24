package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

public class GetConversationListRequest {

    @SerializedName("UserID")
    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
