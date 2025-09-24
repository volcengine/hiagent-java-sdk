package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.model.AbstractResponse;

import java.util.List;

public class GetConversationListResponse extends AbstractResponse {

    @SerializedName("ConversationList")
    private List<ConversationInfo> conversationList;

    public List<ConversationInfo> getConversationList() {
        return conversationList;
    }

    public void setConversationList(List<ConversationInfo> conversationList) {
        this.conversationList = conversationList;
    }

    public static class ConversationInfo {
        @SerializedName("AppConversationID")
        private String appConversationID;

        @SerializedName("ConversationID")
        private String conversationID;

        @SerializedName("ConversationName")
        private String conversationName;

        public String getAppConversationID() {
            return appConversationID;
        }

        public void setAppConversationID(String appConversationID) {
            this.appConversationID = appConversationID;
        }

        public String getConversationID() {
            return conversationID;
        }

        public void setConversationID(String conversationID) {
            this.conversationID = conversationID;
        }

        public String getConversationName() {
            return conversationName;
        }

        public void setConversationName(String conversationName) {
            this.conversationName = conversationName;
        }
    }
}
