package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatRequest {

    @SerializedName("UserID")
    private String userID;

    @SerializedName("AppConversationID")
    private String appConversationID;

    @SerializedName("Query")
    private String query;

    @SerializedName("ResponseMode")
    private String responseMode;

    @SerializedName("QueryExtends")
    private QueryExtendsInfo queryExtends;

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

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getResponseMode() {
        return responseMode;
    }

    public void setResponseMode(String responseMode) {
        this.responseMode = responseMode;
    }

    public QueryExtendsInfo getQueryExtends() {
        return queryExtends;
    }

    public void setQueryExtends(QueryExtendsInfo queryExtends) {
        this.queryExtends = queryExtends;
    }
}
