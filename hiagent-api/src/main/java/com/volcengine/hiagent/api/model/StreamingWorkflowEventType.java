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

public enum StreamingWorkflowEventType {
    /**
     * workflow event type enum
     */
    ToolMessageOutputStart("tool_message_output_start"),
    ToolMessage("tool_message"),
    ToolMessageOutputEnd("tool_message_output_end"),
    MessageOutputStart("message_output_start"),
    Message("message"),
    MessageOutputEnd("message_output_end"),
    FlowInterrupted("flow_interrupted"),
    FlowStart("flow_start"),
    FlowCost("flow_cost"),
    FlowEnd("flow_end"),
    FlowError("flow_error");

    private final String value;

    StreamingWorkflowEventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static StreamingWorkflowEventType fromValue(String value) {
        for (StreamingWorkflowEventType streamingWorkflowEventType : StreamingWorkflowEventType.values()) {
            if (streamingWorkflowEventType.getValue().equals(value)) {
                return streamingWorkflowEventType;
            }
        }
        return null;
    }

}
