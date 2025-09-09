package com.volcengine.hiagent.api.model.eva.base;

import org.jetbrains.annotations.Nullable;

public class ModelAgentConfig {
    private double Temperature;
    private double TopP;
    private int MaxTokens;
    private int RoundsReserved;
    private int RagNum;
    private  String Strategy;
    private int MaxIterations;
    private boolean RagEnabled;
    private boolean ReasoningMode;
    private boolean ReasoningSwitch;
    @Nullable
    private  String ReasoningSwitchType;

    public ModelAgentConfig() {
    }

    public ModelAgentConfig(double temperature, double topP, int maxTokens, int roundsReserved, int ragNum, String strategy, int maxIterations, boolean ragEnabled, boolean reasoningMode, boolean reasoningSwitch, @Nullable String reasoningSwitchType) {
        Temperature = temperature;
        TopP = topP;
        MaxTokens = maxTokens;
        RoundsReserved = roundsReserved;
        RagNum = ragNum;
        Strategy = strategy;
        MaxIterations = maxIterations;
        RagEnabled = ragEnabled;
        ReasoningMode = reasoningMode;
        ReasoningSwitch = reasoningSwitch;
        ReasoningSwitchType = reasoningSwitchType;
    }

    public double getTemperature() {
        return Temperature;
    }

    public void setTemperature(double temperature) {
        Temperature = temperature;
    }

    public double getTopP() {
        return TopP;
    }

    public void setTopP(double topP) {
        TopP = topP;
    }

    public int getMaxTokens() {
        return MaxTokens;
    }

    public void setMaxTokens(int maxTokens) {
        MaxTokens = maxTokens;
    }

    public int getRoundsReserved() {
        return RoundsReserved;
    }

    public void setRoundsReserved(int roundsReserved) {
        RoundsReserved = roundsReserved;
    }

    public int getRagNum() {
        return RagNum;
    }

    public void setRagNum(int ragNum) {
        RagNum = ragNum;
    }

    public String getStrategy() {
        return Strategy;
    }

    public void setStrategy(String strategy) {
        Strategy = strategy;
    }

    public int getMaxIterations() {
        return MaxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        MaxIterations = maxIterations;
    }

    public boolean isRagEnabled() {
        return RagEnabled;
    }

    public void setRagEnabled(boolean ragEnabled) {
        RagEnabled = ragEnabled;
    }

    public boolean isReasoningMode() {
        return ReasoningMode;
    }

    public void setReasoningMode(boolean reasoningMode) {
        ReasoningMode = reasoningMode;
    }

    public boolean isReasoningSwitch() {
        return ReasoningSwitch;
    }

    public void setReasoningSwitch(boolean reasoningSwitch) {
        ReasoningSwitch = reasoningSwitch;
    }

    @Nullable
    public String getReasoningSwitchType() {
        return ReasoningSwitchType;
    }

    public void setReasoningSwitchType(@Nullable String reasoningSwitchType) {
        ReasoningSwitchType = reasoningSwitchType;
    }
}
