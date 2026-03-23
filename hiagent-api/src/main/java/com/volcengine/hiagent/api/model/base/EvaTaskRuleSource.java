package com.volcengine.hiagent.api.model.base;

import com.google.gson.annotations.SerializedName;

public enum EvaTaskRuleSource {
    /**
     * 评测规则来源: 评测规则
     */
    @SerializedName("rules")
    EvaTaskRuleSourceRules("rules"),

    /**
     * 评测规则来源: 评测规则集
     */
    @SerializedName("ruleset")
    EvaTaskRuleSourceRuleset("ruleset"),
    ;

    EvaTaskRuleSource(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    private final String value;

}
