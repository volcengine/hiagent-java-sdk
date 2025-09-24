package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.ChatEvent;

public class MessageCostChatEvent extends ChatEvent {

    @SerializedName("input_tokens")
    private Integer inputTokens;

    @SerializedName("output_tokens")
    private Integer outputTokens;

    @SerializedName("start_time_first_resp")
    private Long startTimeFirstResp;

    @SerializedName("latency_first_resp")
    private Integer latencyFirstResp;

    @SerializedName("latency")
    private Double latency;

    public Integer getInputTokens() {
        return inputTokens;
    }

    public void setInputTokens(Integer inputTokens) {
        this.inputTokens = inputTokens;
    }

    public Integer getOutputTokens() {
        return outputTokens;
    }

    public void setOutputTokens(Integer outputTokens) {
        this.outputTokens = outputTokens;
    }

    public Long getStartTimeFirstResp() {
        return startTimeFirstResp;
    }

    public void setStartTimeFirstResp(Long startTimeFirstResp) {
        this.startTimeFirstResp = startTimeFirstResp;
    }

    public Integer getLatencyFirstResp() {
        return latencyFirstResp;
    }

    public void setLatencyFirstResp(Integer latencyFirstResp) {
        this.latencyFirstResp = latencyFirstResp;
    }

    public Double getLatency() {
        return latency;
    }

    public void setLatency(Double latency) {
        this.latency = latency;
    }
}
