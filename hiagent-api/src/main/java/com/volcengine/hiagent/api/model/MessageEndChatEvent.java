package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.ChatEvent;

import java.util.Map;

public class MessageEndChatEvent extends ChatEvent {

    @SerializedName("agent_configuration")
    private Map<String, Object> agentConfiguration;

    public Map<String, Object> getAgentConfiguration() {
        return agentConfiguration;
    }

    public void setAgentConfiguration(Map<String, Object> agentConfiguration) {
        this.agentConfiguration = agentConfiguration;
    }
}
