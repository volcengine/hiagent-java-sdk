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
