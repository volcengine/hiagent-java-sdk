package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

public class EvaTaskRunConfig {
    @Nullable
    private EvaTaskRunConfigInitiator Initiator;
    @Nullable
    private String AgenticTaskID;
    @Nullable
    private Boolean EnableAttribAnalysis;

    public EvaTaskRunConfig() {
    }

    public EvaTaskRunConfig(@Nullable EvaTaskRunConfigInitiator initiator, @Nullable String agenticTaskID, @Nullable Boolean enableAttribAnalysis) {
        Initiator = initiator;
        AgenticTaskID = agenticTaskID;
        EnableAttribAnalysis = enableAttribAnalysis;
    }

    @Nullable
    public EvaTaskRunConfigInitiator getInitiator() {
        return Initiator;
    }

    public void setInitiator(@Nullable EvaTaskRunConfigInitiator initiator) {
        Initiator = initiator;
    }

    @Nullable
    public String getAgenticTaskID() {
        return AgenticTaskID;
    }

    public void setAgenticTaskID(@Nullable String agenticTaskID) {
        AgenticTaskID = agenticTaskID;
    }

    @Nullable
    public Boolean getEnableAttribAnalysis() {
        return EnableAttribAnalysis;
    }

    public void setEnableAttribAnalysis(@Nullable Boolean enableAttribAnalysis) {
        EnableAttribAnalysis = enableAttribAnalysis;
    }
}
