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
