package com.volcengine.hiagent.api.model.eva.base;

public class EvaTargetBuiltinPromptConfig {
    private  String ModelID; 
    private  String ModelName; 
    private  String ModelType; 
    private  ModelAgentConfig ModelAgentConfig; 
    private  String PromptID; 
    private  String PromptName;

    public EvaTargetBuiltinPromptConfig() {
    }

    public EvaTargetBuiltinPromptConfig(String modelID, String modelName, String modelType, ModelAgentConfig modelAgentConfig, String promptID, String promptName) {
        ModelID = modelID;
        ModelName = modelName;
        ModelType = modelType;
        ModelAgentConfig = modelAgentConfig;
        PromptID = promptID;
        PromptName = promptName;
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

    public ModelAgentConfig getModelAgentConfig() {
        return ModelAgentConfig;
    }

    public void setModelAgentConfig(ModelAgentConfig modelAgentConfig) {
        ModelAgentConfig = modelAgentConfig;
    }

    public String getPromptID() {
        return PromptID;
    }

    public void setPromptID(String promptID) {
        PromptID = promptID;
    }

    public String getPromptName() {
        return PromptName;
    }

    public void setPromptName(String promptName) {
        PromptName = promptName;
    }
}
