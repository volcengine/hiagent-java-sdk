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

public class AgentThoughtEndChatEvent extends ChatEvent {

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
