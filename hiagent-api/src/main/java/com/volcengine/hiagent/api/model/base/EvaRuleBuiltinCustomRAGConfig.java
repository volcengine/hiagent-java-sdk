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

import org.jetbrains.annotations.Nullable;

public class EvaRuleBuiltinCustomRAGConfig {
    private String LLMModelID;
    private String LLMModelName;
    private String LLMModelType;
    @Nullable
    private String EmbeddingsModelID;
    @Nullable
    private String EmbeddingsModelName;
    @Nullable
    private String EmbeddingsModelType;
    private ModelAgentConfig AgentConfig;

    public EvaRuleBuiltinCustomRAGConfig() {
    }

    public EvaRuleBuiltinCustomRAGConfig(String LLMModelID, String LLMModelName, String LLMModelType, @Nullable String embeddingsModelID, @Nullable String embeddingsModelName, @Nullable String embeddingsModelType, ModelAgentConfig agentConfig) {
        this.LLMModelID = LLMModelID;
        this.LLMModelName = LLMModelName;
        this.LLMModelType = LLMModelType;
        EmbeddingsModelID = embeddingsModelID;
        EmbeddingsModelName = embeddingsModelName;
        EmbeddingsModelType = embeddingsModelType;
        AgentConfig = agentConfig;
    }

    public String getLLMModelID() {
        return LLMModelID;
    }

    public void setLLMModelID(String LLMModelID) {
        this.LLMModelID = LLMModelID;
    }

    public String getLLMModelName() {
        return LLMModelName;
    }

    public void setLLMModelName(String LLMModelName) {
        this.LLMModelName = LLMModelName;
    }

    public String getLLMModelType() {
        return LLMModelType;
    }

    public void setLLMModelType(String LLMModelType) {
        this.LLMModelType = LLMModelType;
    }

    @Nullable
    public String getEmbeddingsModelID() {
        return EmbeddingsModelID;
    }

    public void setEmbeddingsModelID(@Nullable String embeddingsModelID) {
        EmbeddingsModelID = embeddingsModelID;
    }

    @Nullable
    public String getEmbeddingsModelName() {
        return EmbeddingsModelName;
    }

    public void setEmbeddingsModelName(@Nullable String embeddingsModelName) {
        EmbeddingsModelName = embeddingsModelName;
    }

    @Nullable
    public String getEmbeddingsModelType() {
        return EmbeddingsModelType;
    }

    public void setEmbeddingsModelType(@Nullable String embeddingsModelType) {
        EmbeddingsModelType = embeddingsModelType;
    }

    public ModelAgentConfig getAgentConfig() {
        return AgentConfig;
    }

    public void setAgentConfig(ModelAgentConfig agentConfig) {
        AgentConfig = agentConfig;
    }
}
