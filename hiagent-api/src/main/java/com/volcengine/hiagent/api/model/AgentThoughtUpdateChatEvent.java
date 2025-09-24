package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.ChatEvent;

public class AgentThoughtUpdateChatEvent extends ChatEvent {

    @SerializedName("position")
    private Integer position;

    @SerializedName("observation")
    private String observation;

    @SerializedName("latency")
    private Double latency;

    @SerializedName("tool_latency")
    private Double toolLatency;

    @SerializedName("thought_latency")
    private Double thoughtLatency;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Double getLatency() {
        return latency;
    }

    public void setLatency(Double latency) {
        this.latency = latency;
    }

    public Double getToolLatency() {
        return toolLatency;
    }

    public void setToolLatency(Double toolLatency) {
        this.toolLatency = toolLatency;
    }

    public Double getThoughtLatency() {
        return thoughtLatency;
    }

    public void setThoughtLatency(Double thoughtLatency) {
        this.thoughtLatency = thoughtLatency;
    }
}
