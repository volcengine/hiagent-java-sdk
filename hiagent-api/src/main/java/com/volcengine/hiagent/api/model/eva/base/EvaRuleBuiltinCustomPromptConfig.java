package com.volcengine.hiagent.api.model.eva.base;

public class EvaRuleBuiltinCustomPromptConfig {
    private String Prompt;
    private String ModelID;
    private String ModelName;
    private String ModelType;
    private ModelAgentConfig AgentConfig;

    public EvaRuleBuiltinCustomPromptConfig() {
    }

    public EvaRuleBuiltinCustomPromptConfig(String prompt, String modelID, String modelName, String modelType, ModelAgentConfig agentConfig) {
        Prompt = prompt;
        ModelID = modelID;
        ModelName = modelName;
        ModelType = modelType;
        AgentConfig = agentConfig;
    }

    public String getPrompt() {
        return Prompt;
    }

    public void setPrompt(String prompt) {
        Prompt = prompt;
    }

    public String getModelID() {
        return ModelID;
    }

    public void setModelID(String modelID) {
        ModelID = modelID;
    }

    public String getModelName() {
        return ModelName;
    }

    public void setModelName(String modelName) {
        ModelName = modelName;
    }

    public String getModelType() {
        return ModelType;
    }

    public void setModelType(String modelType) {
        ModelType = modelType;
    }

    public ModelAgentConfig getAgentConfig() {
        return AgentConfig;
    }

    public void setAgentConfig(ModelAgentConfig agentConfig) {
        AgentConfig = agentConfig;
    }
}
