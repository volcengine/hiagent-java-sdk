package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

public class GetConversationMessageRequest {

    @SerializedName("UserID")
    private String userID;

    @SerializedName("AppConversationID")
    private String appConversationID;

    @SerializedName("Limit")
    private Long limit;

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

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }
}
