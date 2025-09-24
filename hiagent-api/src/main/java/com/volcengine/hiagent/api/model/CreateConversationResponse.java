// Copyright (c) 2024 Bytedance Ltd. and/or its affiliates
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.model.AbstractResponse;

public class CreateConversationResponse extends AbstractResponse {

    @SerializedName("Conversation")
    private Conversation conversation;

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public static class Conversation {

        @SerializedName("AppConversationID")
        private String appConversationID;

        @SerializedName("ConversationName")
        private String conversationName;

        @SerializedName("CreateTime")
        private String createTime;

        @SerializedName("LastChatTime")
        private String lastChatTime;

        @SerializedName("EmptyConversation")
        private Boolean emptyConversation;

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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getLastChatTime() {
            return lastChatTime;
        }

        public void setLastChatTime(String lastChatTime) {
            this.lastChatTime = lastChatTime;
        }

        public Boolean getEmptyConversation() {
            return emptyConversation;
        }

        public void setEmptyConversation(Boolean emptyConversation) {
            this.emptyConversation = emptyConversation;
        }
    }
}
