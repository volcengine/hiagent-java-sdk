package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

public class MessageAnswerInfo {

    @SerializedName("Answer")
    private String answer;

    @SerializedName("MessageID")
    private String messageID;

    @SerializedName("CreatedTime")
    private int createdTime;

    @SerializedName("TaskID")
    private String taskID;

    @SerializedName("Like")
    private int like;

    @SerializedName("TotalTokens")
    private int totalTokens;

    @SerializedName("Latency")
    private Double latency;

    @SerializedName("TracingJsonStr")
    private String tracingJsonStr;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public int getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(int createdTime) {
        this.createdTime = createdTime;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(int totalTokens) {
        this.totalTokens = totalTokens;
    }

    public Double getLatency() {
        return latency;
    }

    public void setLatency(Double latency) {
        this.latency = latency;
    }

    public String getTracingJsonStr() {
        return tracingJsonStr;
    }

    public void setTracingJsonStr(String tracingJsonStr) {
        this.tracingJsonStr = tracingJsonStr;
    }
}
