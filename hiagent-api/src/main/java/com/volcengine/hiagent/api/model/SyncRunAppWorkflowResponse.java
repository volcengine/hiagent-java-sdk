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
import java.util.Map;

public class SyncRunAppWorkflowResponse extends AbstractResponse {

    @SerializedName("runId")
    private String runId;

    @SerializedName("status")
    private String status;

    @SerializedName("nodes")
    private Map<String, WorkflowNode> nodes;

    @SerializedName("steps")
    private List<String> steps;

    @SerializedName("code")
    private Integer code;

    @SerializedName("message")
    private String message;

    @SerializedName("costMs")
    private Integer costMs;

    @SerializedName("output")
    private String output;

    @SerializedName("lastInterruptedNodeId")
    private String lastInterruptedNodeId;

    @SerializedName("checkpointExpireTimestamp")
    private Integer checkpointExpireTimestamp;

    @SerializedName("msg")
    private String msg;

    @SerializedName("costToken")
    private Integer costToken;

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, WorkflowNode> getNodes() {
        return nodes;
    }

    public void setNodes(Map<String, WorkflowNode> nodes) {
        this.nodes = nodes;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCostMs() {
        return costMs;
    }

    public void setCostMs(Integer costMs) {
        this.costMs = costMs;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getLastInterruptedNodeId() {
        return lastInterruptedNodeId;
    }

    public void setLastInterruptedNodeId(String lastInterruptedNodeId) {
        this.lastInterruptedNodeId = lastInterruptedNodeId;
    }

    public Integer getCheckpointExpireTimestamp() {
        return checkpointExpireTimestamp;
    }

    public void setCheckpointExpireTimestamp(Integer checkpointExpireTimestamp) {
        this.checkpointExpireTimestamp = checkpointExpireTimestamp;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCostToken() {
        return costToken;
    }

    public void setCostToken(Integer costToken) {
        this.costToken = costToken;
    }
}
