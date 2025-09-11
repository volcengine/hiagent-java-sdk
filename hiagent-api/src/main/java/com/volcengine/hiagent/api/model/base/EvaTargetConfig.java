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
