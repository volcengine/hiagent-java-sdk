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
import com.volcengine.hiagent.api.model.base.WorkflowEvent;

public class FlowInterruptedWorkflowEvent extends WorkflowEvent {

    @SerializedName("interrupted_msg")
    private String interruptedMsg;

    @SerializedName("interrupted_node_id")
    private String interruptedNodeId;

    @SerializedName("interrupted_type")
    private String interruptedType;

    public String getInterruptedMsg() {
        return interruptedMsg;
    }

    public void setInterruptedMsg(String interruptedMsg) {
        this.interruptedMsg = interruptedMsg;
    }

    public String getInterruptedNodeId() {
        return interruptedNodeId;
    }

    public void setInterruptedNodeId(String interruptedNodeId) {
        this.interruptedNodeId = interruptedNodeId;
    }

    public String getInterruptedType() {
        return interruptedType;
    }

    public void setInterruptedType(String interruptedType) {
        this.interruptedType = interruptedType;
    }
}
