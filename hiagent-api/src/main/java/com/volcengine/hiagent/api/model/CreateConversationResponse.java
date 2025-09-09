package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.model.AbstractResponse;

public class CreateConversationResponse extends AbstractResponse {
    @SerializedName("Conversation")
    private Conversation conversation = null;

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public static class Conversation {
        @SerializedName("AppConversationID")
        private String appConversationID = null;

        @SerializedName("ConversationName")
        private String conversationName = null;

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
    }
}