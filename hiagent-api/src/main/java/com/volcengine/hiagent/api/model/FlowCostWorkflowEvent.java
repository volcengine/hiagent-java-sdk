package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.WorkflowEvent;

public class FlowCostWorkflowEvent extends WorkflowEvent {

    @SerializedName("cost_tokens")
    private Integer costTokens;

    @SerializedName("latency")
    private Integer latency;

    public Integer getCostTokens() {
        return costTokens;
    }

    public void setCostTokens(Integer costTokens) {
        this.costTokens = costTokens;
    }

    public Integer getLatency() {
        return latency;
    }

    public void setLatency(Integer latency) {
        this.latency = latency;
    }
}
