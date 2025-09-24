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

import java.util.List;

public class ChatMessageInfo {

    @SerializedName("ConversationID")
    private String conversationID;

    @SerializedName("QueryID")
    private String queryID;

    @SerializedName("Query")
    private String query;

    @SerializedName("AnswerInfo")
    private MessageAnswerInfo answerInfo;

    @SerializedName("OtherAnswers")
    private List<MessageAnswerInfo> otherAnswers;

    @SerializedName("QueryExtends")
    private QueryExtendsInfo queryExtends;

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getQueryID() {
        return queryID;
    }

    public void setQueryID(String queryID) {
        this.queryID = queryID;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public MessageAnswerInfo getAnswerInfo() {
        return answerInfo;
    }

    public void setAnswerInfo(MessageAnswerInfo answerInfo) {
        this.answerInfo = answerInfo;
    }

    public List<MessageAnswerInfo> getOtherAnswers() {
        return otherAnswers;
    }

    public void setOtherAnswers(List<MessageAnswerInfo> otherAnswers) {
        this.otherAnswers = otherAnswers;
    }

    public QueryExtendsInfo getQueryExtends() {
        return queryExtends;
    }

    public void setQueryExtends(QueryExtendsInfo queryExtends) {
        this.queryExtends = queryExtends;
    }
}
