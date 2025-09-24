package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

public class DeleteMessageRequest {

    @SerializedName("UserID")
    private String userID;

    @SerializedName("MessageID")
    private String messageID;

    @SerializedName("QueryID")
    private String queryID;
}
