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

public class BlockingChatResponse extends AbstractResponse {

    @SerializedName("event")
    private String event;

    @SerializedName("task_id")
    private Object taskId;

    @SerializedName("conversation_id")
    private String conversationId;

    @SerializedName("answer")
    private String answer;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("think_messages")
    private List<String> thinkMessages;

    @SerializedName("tool_messages")
    private List<String> toolMessages;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Object getTaskId() {
        return taskId;
    }

    public void setTaskId(Object taskId) {
        this.taskId = taskId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getThinkMessages() {
        return thinkMessages;
    }

    public void setThinkMessages(List<String> thinkMessages) {
        this.thinkMessages = thinkMessages;
    }

    public List<String> getToolMessages() {
        return toolMessages;
    }

    public void setToolMessages(List<String> toolMessages) {
        this.toolMessages = toolMessages;
    }
}
