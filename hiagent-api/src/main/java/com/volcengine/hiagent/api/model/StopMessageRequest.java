package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

public class StopMessageRequest {

    @SerializedName("UserID")
    private String userID;

    @SerializedName("TaskID")
    private String taskID;

    @SerializedName("MessageID")
    private String messageID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
}
