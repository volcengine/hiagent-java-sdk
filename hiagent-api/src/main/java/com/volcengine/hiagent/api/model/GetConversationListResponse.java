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
