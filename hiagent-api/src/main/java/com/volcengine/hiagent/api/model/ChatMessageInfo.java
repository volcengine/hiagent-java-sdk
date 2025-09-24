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
