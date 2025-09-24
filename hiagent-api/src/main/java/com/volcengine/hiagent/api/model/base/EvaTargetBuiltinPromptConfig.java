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
package com.volcengine.hiagent.api.model.base;

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
