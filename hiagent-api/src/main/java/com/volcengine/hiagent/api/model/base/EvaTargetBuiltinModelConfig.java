package com.volcengine.hiagent.api.model.base;

public class EvaTargetBuiltinModelConfig {
    private String ModelID; 
    private String ModelName; 
    private String ModelType; 
    private ModelAgentConfig ModelAgentConfig;

    public EvaTargetBuiltinModelConfig() {
    }

    public EvaTargetBuiltinModelConfig(String modelID, String modelName, String modelType, ModelAgentConfig modelAgentConfig) {
        ModelID = modelID;
        ModelName = modelName;
        ModelType = modelType;
        ModelAgentConfig = modelAgentConfig;
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
}
