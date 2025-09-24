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

public class EvaTargetConfig {
    @Nullable
    private  EvaTargetBuiltinModelConfig BuiltinModelConfig;
    @Nullable
    private  EvaTargetBuiltinAgentConfig BuiltinAgentConfig;
    @Nullable
    private  EvaTargetBuiltinPromptConfig BuiltinPromptConfig;
    @Nullable
    private  EvaTargetCustomAPPConfig CustomAPPConfig;

    public EvaTargetConfig() {
    }

    public EvaTargetConfig(@Nullable EvaTargetBuiltinModelConfig builtinModelConfig, @Nullable EvaTargetBuiltinAgentConfig builtinAgentConfig, @Nullable EvaTargetBuiltinPromptConfig builtinPromptConfig, @Nullable EvaTargetCustomAPPConfig customAPPConfig) {
        BuiltinModelConfig = builtinModelConfig;
        BuiltinAgentConfig = builtinAgentConfig;
        BuiltinPromptConfig = builtinPromptConfig;
        CustomAPPConfig = customAPPConfig;
    }

    @Nullable
    public EvaTargetBuiltinModelConfig getBuiltinModelConfig() {
        return BuiltinModelConfig;
    }

    public void setBuiltinModelConfig(@Nullable EvaTargetBuiltinModelConfig builtinModelConfig) {
        BuiltinModelConfig = builtinModelConfig;
    }

    @Nullable
    public EvaTargetBuiltinAgentConfig getBuiltinAgentConfig() {
        return BuiltinAgentConfig;
    }

    public void setBuiltinAgentConfig(@Nullable EvaTargetBuiltinAgentConfig builtinAgentConfig) {
        BuiltinAgentConfig = builtinAgentConfig;
    }

    @Nullable
    public EvaTargetBuiltinPromptConfig getBuiltinPromptConfig() {
        return BuiltinPromptConfig;
    }

    public void setBuiltinPromptConfig(@Nullable EvaTargetBuiltinPromptConfig builtinPromptConfig) {
        BuiltinPromptConfig = builtinPromptConfig;
    }

    @Nullable
    public EvaTargetCustomAPPConfig getCustomAPPConfig() {
        return CustomAPPConfig;
    }

    public void setCustomAPPConfig(@Nullable EvaTargetCustomAPPConfig customAPPConfig) {
        CustomAPPConfig = customAPPConfig;
    }
}
