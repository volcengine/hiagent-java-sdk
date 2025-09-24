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
import com.volcengine.hiagent.api.model.base.ChatEvent;

public class AgentJumpChatEvent extends ChatEvent {

    @SerializedName("jump_reason")
    private String jumpReason;

    @SerializedName("jump_latency")
    private Double jumpLatency;

    @SerializedName("jump_to")
    private String jumpTo;

    @SerializedName("message_id")
    private String messageId;

    public String getJumpReason() {
        return jumpReason;
    }

    public void setJumpReason(String jumpReason) {
        this.jumpReason = jumpReason;
    }

    public Double getJumpLatency() {
        return jumpLatency;
    }

    public void setJumpLatency(Double jumpLatency) {
        this.jumpLatency = jumpLatency;
    }

    public String getJumpTo() {
        return jumpTo;
    }

    public void setJumpTo(String jumpTo) {
        this.jumpTo = jumpTo;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
