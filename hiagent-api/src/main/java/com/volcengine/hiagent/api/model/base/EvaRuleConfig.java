package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EvaRuleConfig {
    @Nullable
    private EvaRuleBuiltinCodeConfig BuiltinCodeJavascriptConfig;
    @Nullable
    private EvaRuleBuiltinCodeConfig BuiltinCodePythonConfig;
    @Nullable
    private EvaRuleBuiltinCommonConfig BuiltinBaseEqualsConfig;
    @Nullable
    private EvaRuleBuiltinCommonConfig BuiltinBaseNotEqualsConfig;
    @Nullable
    private EvaRuleBuiltinCommonConfig BuiltinBaseContainsConfig;
    @Nullable
    private EvaRuleBuiltinCommonConfig BuiltinBaseNotContainsConfig;
    @Nullable
    private EvaRuleBuiltinCommonConfig BuiltinBaseIContainsConfig;
    @Nullable
    private EvaRuleBuiltinCommonConfig BuiltinBaseNotIContainsConfig;
    @Nullable
    private EvaRuleBuiltinBaseRegexConfig BuiltinBaseRegexConfig;
    @Nullable
    private EvaRuleBuiltinBaseRegexConfig BuiltinBaseNotRegexConfig;
    @Nullable
    private EvaRuleBuiltinHumanPlainTextConfig BuiltinHumanPlainTextConfig;
    @Nullable
    private List<EvaRuleBuiltinHumanSelectionConfig> BuiltinHumanSelectionConfig;
    @Nullable
    private EvaRuleBuiltinHumanScoreConfig BuiltinHumanScoreConfig;
    @Nullable
    private EvaRuleBuiltinCustomPromptConfig BuiltinCustomPromptConfig;
    @Nullable
    private EvaRuleBuiltinCustomPromptConfig BuiltinCustomPromptJudgementConfig;
    @Nullable
    private EvaRuleBuiltinCustomPromptConfig BuiltinCustomPromptCorrectnessConfig;
    @Nullable
    private EvaRuleBuiltinCustomPromptConfig BuiltinCustomPromptCreativityConfig;
    @Nullable
    private EvaRuleBuiltinCustomPromptConfig BuiltinCustomPromptDepthThinkingConfig;
    @Nullable
    private EvaRuleBuiltinCustomPromptConfig BuiltinCustomPromptDetailAttentionConfig;
    @Nullable
    private EvaRuleBuiltinCustomPromptConfig BuiltinCustomPromptHelpfulnessConfig;
    @Nullable
    private EvaRuleBuiltinCustomPromptConfig BuiltinCustomPromptInsensitivityConfig;
    @Nullable
    private EvaRuleBuiltinCustomPromptConfig BuiltinCustomPromptRelevancyConfig;
    @Nullable
    private EvaRuleBuiltinCustomPromptConfig BuiltinCustomPromptControversyConfig;
    @Nullable
    private EvaRuleBuiltinCustomPromptConfig BuiltinCustomPromptCriminalityConfig;
    @Nullable
    private EvaRuleBuiltinCustomPromptConfig BuiltinCustomPromptHarmfulnessConfig;
    @Nullable
    private EvaRuleBuiltinCustomPromptConfig BuiltinCustomPromptMaliciousnessConfig;
    @Nullable
    private EvaRuleBuiltinCustomPromptConfig BuiltinCustomPromptGenderDiscriminationConfig;
    @Nullable
    private EvaRuleBuiltinEmptyConfig BuiltinPresetAccuracyConfig;
    @Nullable
    private EvaRuleBuiltinEmptyConfig BuiltinPresetF1Config;
    @Nullable
    private EvaRuleBuiltinEmptyConfig BuiltinPresetRouge1Config;
    @Nullable
    private EvaRuleBuiltinEmptyConfig BuiltinPresetRouge2Config;
    @Nullable
    private EvaRuleBuiltinEmptyConfig BuiltinPresetRougeLConfig;
    @Nullable
    private EvaRuleBuiltinEmptyConfig BuiltinPresetBLEUConfig;
    @Nullable
    private EvaRuleBuiltinEmptyConfig BuiltinPresetMETEORConfig;
    @Nullable
    private EvaRuleBuiltinEmptyConfig BuiltinPresetNISTConfig;
    @Nullable
    private EvaRuleBuiltinCustomRAGConfig BuiltinPresetRAGConfig;

    public EvaRuleConfig() {
    }

    public EvaRuleConfig(@Nullable EvaRuleBuiltinCodeConfig builtinCodeJavascriptConfig, @Nullable EvaRuleBuiltinCodeConfig builtinCodePythonConfig, @Nullable EvaRuleBuiltinCommonConfig builtinBaseEqualsConfig, @Nullable EvaRuleBuiltinCommonConfig builtinBaseNotEqualsConfig, @Nullable EvaRuleBuiltinCommonConfig builtinBaseContainsConfig, @Nullable EvaRuleBuiltinCommonConfig builtinBaseNotContainsConfig, @Nullable EvaRuleBuiltinCommonConfig builtinBaseIContainsConfig, @Nullable EvaRuleBuiltinCommonConfig builtinBaseNotIContainsConfig, @Nullable EvaRuleBuiltinBaseRegexConfig builtinBaseRegexConfig, @Nullable EvaRuleBuiltinBaseRegexConfig builtinBaseNotRegexConfig, @Nullable EvaRuleBuiltinHumanPlainTextConfig builtinHumanPlainTextConfig, @Nullable List<EvaRuleBuiltinHumanSelectionConfig> builtinHumanSelectionConfig, @Nullable EvaRuleBuiltinHumanScoreConfig builtinHumanScoreConfig, @Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptConfig, @Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptJudgementConfig, @Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptCorrectnessConfig, @Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptCreativityConfig, @Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptDepthThinkingConfig, @Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptDetailAttentionConfig, @Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptHelpfulnessConfig, @Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptInsensitivityConfig, @Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptRelevancyConfig, @Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptControversyConfig, @Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptCriminalityConfig, @Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptHarmfulnessConfig, @Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptMaliciousnessConfig, @Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptGenderDiscriminationConfig, @Nullable EvaRuleBuiltinEmptyConfig builtinPresetAccuracyConfig, @Nullable EvaRuleBuiltinEmptyConfig builtinPresetF1Config, @Nullable EvaRuleBuiltinEmptyConfig builtinPresetRouge1Config, @Nullable EvaRuleBuiltinEmptyConfig builtinPresetRouge2Config, @Nullable EvaRuleBuiltinEmptyConfig builtinPresetRougeLConfig, @Nullable EvaRuleBuiltinEmptyConfig builtinPresetBLEUConfig, @Nullable EvaRuleBuiltinEmptyConfig builtinPresetMETEORConfig, @Nullable EvaRuleBuiltinEmptyConfig builtinPresetNISTConfig, @Nullable EvaRuleBuiltinCustomRAGConfig builtinPresetRAGConfig) {
        BuiltinCodeJavascriptConfig = builtinCodeJavascriptConfig;
        BuiltinCodePythonConfig = builtinCodePythonConfig;
        BuiltinBaseEqualsConfig = builtinBaseEqualsConfig;
        BuiltinBaseNotEqualsConfig = builtinBaseNotEqualsConfig;
        BuiltinBaseContainsConfig = builtinBaseContainsConfig;
        BuiltinBaseNotContainsConfig = builtinBaseNotContainsConfig;
        BuiltinBaseIContainsConfig = builtinBaseIContainsConfig;
        BuiltinBaseNotIContainsConfig = builtinBaseNotIContainsConfig;
        BuiltinBaseRegexConfig = builtinBaseRegexConfig;
        BuiltinBaseNotRegexConfig = builtinBaseNotRegexConfig;
        BuiltinHumanPlainTextConfig = builtinHumanPlainTextConfig;
        BuiltinHumanSelectionConfig = builtinHumanSelectionConfig;
        BuiltinHumanScoreConfig = builtinHumanScoreConfig;
        BuiltinCustomPromptConfig = builtinCustomPromptConfig;
        BuiltinCustomPromptJudgementConfig = builtinCustomPromptJudgementConfig;
        BuiltinCustomPromptCorrectnessConfig = builtinCustomPromptCorrectnessConfig;
        BuiltinCustomPromptCreativityConfig = builtinCustomPromptCreativityConfig;
        BuiltinCustomPromptDepthThinkingConfig = builtinCustomPromptDepthThinkingConfig;
        BuiltinCustomPromptDetailAttentionConfig = builtinCustomPromptDetailAttentionConfig;
        BuiltinCustomPromptHelpfulnessConfig = builtinCustomPromptHelpfulnessConfig;
        BuiltinCustomPromptInsensitivityConfig = builtinCustomPromptInsensitivityConfig;
        BuiltinCustomPromptRelevancyConfig = builtinCustomPromptRelevancyConfig;
        BuiltinCustomPromptControversyConfig = builtinCustomPromptControversyConfig;
        BuiltinCustomPromptCriminalityConfig = builtinCustomPromptCriminalityConfig;
        BuiltinCustomPromptHarmfulnessConfig = builtinCustomPromptHarmfulnessConfig;
        BuiltinCustomPromptMaliciousnessConfig = builtinCustomPromptMaliciousnessConfig;
        BuiltinCustomPromptGenderDiscriminationConfig = builtinCustomPromptGenderDiscriminationConfig;
        BuiltinPresetAccuracyConfig = builtinPresetAccuracyConfig;
        BuiltinPresetF1Config = builtinPresetF1Config;
        BuiltinPresetRouge1Config = builtinPresetRouge1Config;
        BuiltinPresetRouge2Config = builtinPresetRouge2Config;
        BuiltinPresetRougeLConfig = builtinPresetRougeLConfig;
        BuiltinPresetBLEUConfig = builtinPresetBLEUConfig;
        BuiltinPresetMETEORConfig = builtinPresetMETEORConfig;
        BuiltinPresetNISTConfig = builtinPresetNISTConfig;
        BuiltinPresetRAGConfig = builtinPresetRAGConfig;
    }

    @Nullable
    public EvaRuleBuiltinCodeConfig getBuiltinCodeJavascriptConfig() {
        return BuiltinCodeJavascriptConfig;
    }

    public void setBuiltinCodeJavascriptConfig(@Nullable EvaRuleBuiltinCodeConfig builtinCodeJavascriptConfig) {
        BuiltinCodeJavascriptConfig = builtinCodeJavascriptConfig;
    }

    @Nullable
    public EvaRuleBuiltinCodeConfig getBuiltinCodePythonConfig() {
        return BuiltinCodePythonConfig;
    }

    public void setBuiltinCodePythonConfig(@Nullable EvaRuleBuiltinCodeConfig builtinCodePythonConfig) {
        BuiltinCodePythonConfig = builtinCodePythonConfig;
    }

    @Nullable
    public EvaRuleBuiltinCommonConfig getBuiltinBaseEqualsConfig() {
        return BuiltinBaseEqualsConfig;
    }

    public void setBuiltinBaseEqualsConfig(@Nullable EvaRuleBuiltinCommonConfig builtinBaseEqualsConfig) {
        BuiltinBaseEqualsConfig = builtinBaseEqualsConfig;
    }

    @Nullable
    public EvaRuleBuiltinCommonConfig getBuiltinBaseNotEqualsConfig() {
        return BuiltinBaseNotEqualsConfig;
    }

    public void setBuiltinBaseNotEqualsConfig(@Nullable EvaRuleBuiltinCommonConfig builtinBaseNotEqualsConfig) {
        BuiltinBaseNotEqualsConfig = builtinBaseNotEqualsConfig;
    }

    @Nullable
    public EvaRuleBuiltinCommonConfig getBuiltinBaseContainsConfig() {
        return BuiltinBaseContainsConfig;
    }

    public void setBuiltinBaseContainsConfig(@Nullable EvaRuleBuiltinCommonConfig builtinBaseContainsConfig) {
        BuiltinBaseContainsConfig = builtinBaseContainsConfig;
    }

    @Nullable
    public EvaRuleBuiltinCommonConfig getBuiltinBaseNotContainsConfig() {
        return BuiltinBaseNotContainsConfig;
    }

    public void setBuiltinBaseNotContainsConfig(@Nullable EvaRuleBuiltinCommonConfig builtinBaseNotContainsConfig) {
        BuiltinBaseNotContainsConfig = builtinBaseNotContainsConfig;
    }

    @Nullable
    public EvaRuleBuiltinCommonConfig getBuiltinBaseIContainsConfig() {
        return BuiltinBaseIContainsConfig;
    }

    public void setBuiltinBaseIContainsConfig(@Nullable EvaRuleBuiltinCommonConfig builtinBaseIContainsConfig) {
        BuiltinBaseIContainsConfig = builtinBaseIContainsConfig;
    }

    @Nullable
    public EvaRuleBuiltinCommonConfig getBuiltinBaseNotIContainsConfig() {
        return BuiltinBaseNotIContainsConfig;
    }

    public void setBuiltinBaseNotIContainsConfig(@Nullable EvaRuleBuiltinCommonConfig builtinBaseNotIContainsConfig) {
        BuiltinBaseNotIContainsConfig = builtinBaseNotIContainsConfig;
    }

    @Nullable
    public EvaRuleBuiltinBaseRegexConfig getBuiltinBaseRegexConfig() {
        return BuiltinBaseRegexConfig;
    }

    public void setBuiltinBaseRegexConfig(@Nullable EvaRuleBuiltinBaseRegexConfig builtinBaseRegexConfig) {
        BuiltinBaseRegexConfig = builtinBaseRegexConfig;
    }

    @Nullable
    public EvaRuleBuiltinBaseRegexConfig getBuiltinBaseNotRegexConfig() {
        return BuiltinBaseNotRegexConfig;
    }

    public void setBuiltinBaseNotRegexConfig(@Nullable EvaRuleBuiltinBaseRegexConfig builtinBaseNotRegexConfig) {
        BuiltinBaseNotRegexConfig = builtinBaseNotRegexConfig;
    }

    @Nullable
    public EvaRuleBuiltinHumanPlainTextConfig getBuiltinHumanPlainTextConfig() {
        return BuiltinHumanPlainTextConfig;
    }

    public void setBuiltinHumanPlainTextConfig(@Nullable EvaRuleBuiltinHumanPlainTextConfig builtinHumanPlainTextConfig) {
        BuiltinHumanPlainTextConfig = builtinHumanPlainTextConfig;
    }

    @Nullable
    public List<EvaRuleBuiltinHumanSelectionConfig> getBuiltinHumanSelectionConfig() {
        return BuiltinHumanSelectionConfig;
    }

    public void setBuiltinHumanSelectionConfig(@Nullable List<EvaRuleBuiltinHumanSelectionConfig> builtinHumanSelectionConfig) {
        BuiltinHumanSelectionConfig = builtinHumanSelectionConfig;
    }

    @Nullable
    public EvaRuleBuiltinHumanScoreConfig getBuiltinHumanScoreConfig() {
        return BuiltinHumanScoreConfig;
    }

    public void setBuiltinHumanScoreConfig(@Nullable EvaRuleBuiltinHumanScoreConfig builtinHumanScoreConfig) {
        BuiltinHumanScoreConfig = builtinHumanScoreConfig;
    }

    @Nullable
    public EvaRuleBuiltinCustomPromptConfig getBuiltinCustomPromptConfig() {
        return BuiltinCustomPromptConfig;
    }

    public void setBuiltinCustomPromptConfig(@Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptConfig) {
        BuiltinCustomPromptConfig = builtinCustomPromptConfig;
    }

    @Nullable
    public EvaRuleBuiltinCustomPromptConfig getBuiltinCustomPromptJudgementConfig() {
        return BuiltinCustomPromptJudgementConfig;
    }

    public void setBuiltinCustomPromptJudgementConfig(@Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptJudgementConfig) {
        BuiltinCustomPromptJudgementConfig = builtinCustomPromptJudgementConfig;
    }

    @Nullable
    public EvaRuleBuiltinCustomPromptConfig getBuiltinCustomPromptCorrectnessConfig() {
        return BuiltinCustomPromptCorrectnessConfig;
    }

    public void setBuiltinCustomPromptCorrectnessConfig(@Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptCorrectnessConfig) {
        BuiltinCustomPromptCorrectnessConfig = builtinCustomPromptCorrectnessConfig;
    }

    @Nullable
    public EvaRuleBuiltinCustomPromptConfig getBuiltinCustomPromptCreativityConfig() {
        return BuiltinCustomPromptCreativityConfig;
    }

    public void setBuiltinCustomPromptCreativityConfig(@Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptCreativityConfig) {
        BuiltinCustomPromptCreativityConfig = builtinCustomPromptCreativityConfig;
    }

    @Nullable
    public EvaRuleBuiltinCustomPromptConfig getBuiltinCustomPromptDepthThinkingConfig() {
        return BuiltinCustomPromptDepthThinkingConfig;
    }

    public void setBuiltinCustomPromptDepthThinkingConfig(@Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptDepthThinkingConfig) {
        BuiltinCustomPromptDepthThinkingConfig = builtinCustomPromptDepthThinkingConfig;
    }

    @Nullable
    public EvaRuleBuiltinCustomPromptConfig getBuiltinCustomPromptDetailAttentionConfig() {
        return BuiltinCustomPromptDetailAttentionConfig;
    }

    public void setBuiltinCustomPromptDetailAttentionConfig(@Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptDetailAttentionConfig) {
        BuiltinCustomPromptDetailAttentionConfig = builtinCustomPromptDetailAttentionConfig;
    }

    @Nullable
    public EvaRuleBuiltinCustomPromptConfig getBuiltinCustomPromptHelpfulnessConfig() {
        return BuiltinCustomPromptHelpfulnessConfig;
    }

    public void setBuiltinCustomPromptHelpfulnessConfig(@Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptHelpfulnessConfig) {
        BuiltinCustomPromptHelpfulnessConfig = builtinCustomPromptHelpfulnessConfig;
    }

    @Nullable
    public EvaRuleBuiltinCustomPromptConfig getBuiltinCustomPromptInsensitivityConfig() {
        return BuiltinCustomPromptInsensitivityConfig;
    }

    public void setBuiltinCustomPromptInsensitivityConfig(@Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptInsensitivityConfig) {
        BuiltinCustomPromptInsensitivityConfig = builtinCustomPromptInsensitivityConfig;
    }

    @Nullable
    public EvaRuleBuiltinCustomPromptConfig getBuiltinCustomPromptRelevancyConfig() {
        return BuiltinCustomPromptRelevancyConfig;
    }

    public void setBuiltinCustomPromptRelevancyConfig(@Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptRelevancyConfig) {
        BuiltinCustomPromptRelevancyConfig = builtinCustomPromptRelevancyConfig;
    }

    @Nullable
    public EvaRuleBuiltinCustomPromptConfig getBuiltinCustomPromptControversyConfig() {
        return BuiltinCustomPromptControversyConfig;
    }

    public void setBuiltinCustomPromptControversyConfig(@Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptControversyConfig) {
        BuiltinCustomPromptControversyConfig = builtinCustomPromptControversyConfig;
    }

    @Nullable
    public EvaRuleBuiltinCustomPromptConfig getBuiltinCustomPromptCriminalityConfig() {
        return BuiltinCustomPromptCriminalityConfig;
    }

    public void setBuiltinCustomPromptCriminalityConfig(@Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptCriminalityConfig) {
        BuiltinCustomPromptCriminalityConfig = builtinCustomPromptCriminalityConfig;
    }

    @Nullable
    public EvaRuleBuiltinCustomPromptConfig getBuiltinCustomPromptHarmfulnessConfig() {
        return BuiltinCustomPromptHarmfulnessConfig;
    }

    public void setBuiltinCustomPromptHarmfulnessConfig(@Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptHarmfulnessConfig) {
        BuiltinCustomPromptHarmfulnessConfig = builtinCustomPromptHarmfulnessConfig;
    }

    @Nullable
    public EvaRuleBuiltinCustomPromptConfig getBuiltinCustomPromptMaliciousnessConfig() {
        return BuiltinCustomPromptMaliciousnessConfig;
    }

    public void setBuiltinCustomPromptMaliciousnessConfig(@Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptMaliciousnessConfig) {
        BuiltinCustomPromptMaliciousnessConfig = builtinCustomPromptMaliciousnessConfig;
    }

    @Nullable
    public EvaRuleBuiltinCustomPromptConfig getBuiltinCustomPromptGenderDiscriminationConfig() {
        return BuiltinCustomPromptGenderDiscriminationConfig;
    }

    public void setBuiltinCustomPromptGenderDiscriminationConfig(@Nullable EvaRuleBuiltinCustomPromptConfig builtinCustomPromptGenderDiscriminationConfig) {
        BuiltinCustomPromptGenderDiscriminationConfig = builtinCustomPromptGenderDiscriminationConfig;
    }

    @Nullable
    public EvaRuleBuiltinEmptyConfig getBuiltinPresetAccuracyConfig() {
        return BuiltinPresetAccuracyConfig;
    }

    public void setBuiltinPresetAccuracyConfig(@Nullable EvaRuleBuiltinEmptyConfig builtinPresetAccuracyConfig) {
        BuiltinPresetAccuracyConfig = builtinPresetAccuracyConfig;
    }

    @Nullable
    public EvaRuleBuiltinEmptyConfig getBuiltinPresetF1Config() {
        return BuiltinPresetF1Config;
    }

    public void setBuiltinPresetF1Config(@Nullable EvaRuleBuiltinEmptyConfig builtinPresetF1Config) {
        BuiltinPresetF1Config = builtinPresetF1Config;
    }

    @Nullable
    public EvaRuleBuiltinEmptyConfig getBuiltinPresetRouge1Config() {
        return BuiltinPresetRouge1Config;
    }

    public void setBuiltinPresetRouge1Config(@Nullable EvaRuleBuiltinEmptyConfig builtinPresetRouge1Config) {
        BuiltinPresetRouge1Config = builtinPresetRouge1Config;
    }

    @Nullable
    public EvaRuleBuiltinEmptyConfig getBuiltinPresetRouge2Config() {
        return BuiltinPresetRouge2Config;
    }

    public void setBuiltinPresetRouge2Config(@Nullable EvaRuleBuiltinEmptyConfig builtinPresetRouge2Config) {
        BuiltinPresetRouge2Config = builtinPresetRouge2Config;
    }

    @Nullable
    public EvaRuleBuiltinEmptyConfig getBuiltinPresetRougeLConfig() {
        return BuiltinPresetRougeLConfig;
    }

    public void setBuiltinPresetRougeLConfig(@Nullable EvaRuleBuiltinEmptyConfig builtinPresetRougeLConfig) {
        BuiltinPresetRougeLConfig = builtinPresetRougeLConfig;
    }

    @Nullable
    public EvaRuleBuiltinEmptyConfig getBuiltinPresetBLEUConfig() {
        return BuiltinPresetBLEUConfig;
    }

    public void setBuiltinPresetBLEUConfig(@Nullable EvaRuleBuiltinEmptyConfig builtinPresetBLEUConfig) {
        BuiltinPresetBLEUConfig = builtinPresetBLEUConfig;
    }

    @Nullable
    public EvaRuleBuiltinEmptyConfig getBuiltinPresetMETEORConfig() {
        return BuiltinPresetMETEORConfig;
    }

    public void setBuiltinPresetMETEORConfig(@Nullable EvaRuleBuiltinEmptyConfig builtinPresetMETEORConfig) {
        BuiltinPresetMETEORConfig = builtinPresetMETEORConfig;
    }

    @Nullable
    public EvaRuleBuiltinEmptyConfig getBuiltinPresetNISTConfig() {
        return BuiltinPresetNISTConfig;
    }

    public void setBuiltinPresetNISTConfig(@Nullable EvaRuleBuiltinEmptyConfig builtinPresetNISTConfig) {
        BuiltinPresetNISTConfig = builtinPresetNISTConfig;
    }

    @Nullable
    public EvaRuleBuiltinCustomRAGConfig getBuiltinPresetRAGConfig() {
        return BuiltinPresetRAGConfig;
    }

    public void setBuiltinPresetRAGConfig(@Nullable EvaRuleBuiltinCustomRAGConfig builtinPresetRAGConfig) {
        BuiltinPresetRAGConfig = builtinPresetRAGConfig;
    }
}
