package com.volcengine.hiagent.api.model;

public enum StreamingWorkflowEventType {
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
