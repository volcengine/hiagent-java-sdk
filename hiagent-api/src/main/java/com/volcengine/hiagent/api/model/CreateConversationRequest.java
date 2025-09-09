package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class CreateConversationRequest {
    @SerializedName("UserID")
    private String userID = null;

    @SerializedName("Inputs")
    private Map<String, String> inputs = null;

    public CreateConversationRequest() {
    }

    public CreateConversationRequest(String userID) {
        this.userID = userID;
    }

    public CreateConversationRequest(String userID, Map<String, String> inputs) {
        this.userID = userID;
        this.inputs = inputs;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Map<String, String> getInputs() {
        return inputs;
    }

    public void setInputs(Map<String, String> inputs) {
        this.inputs = inputs;
    }
}