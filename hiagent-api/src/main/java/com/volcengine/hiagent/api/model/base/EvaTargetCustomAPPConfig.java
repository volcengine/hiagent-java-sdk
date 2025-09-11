package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

public class EvaTargetCustomAPPConfig {
    private String AppID;
    @Nullable
    private ModelAgentConfig ModelAgentConfig;

    public EvaTargetCustomAPPConfig() {
    }

    public EvaTargetCustomAPPConfig(String appID, @Nullable ModelAgentConfig modelAgentConfig) {
        AppID = appID;
        ModelAgentConfig = modelAgentConfig;
    }

    public String getAppID() {
        return AppID;
    }

    public void setAppID(String appID) {
        AppID = appID;
    }

    @Nullable
    public ModelAgentConfig getModelAgentConfig() {
        return ModelAgentConfig;
    }

    public void setModelAgentConfig(@Nullable ModelAgentConfig modelAgentConfig) {
        ModelAgentConfig = modelAgentConfig;
    }
}
