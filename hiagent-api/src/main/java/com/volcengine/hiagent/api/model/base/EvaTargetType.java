package com.volcengine.hiagent.api.model.base;

import com.google.gson.annotations.SerializedName;

/**
 * 评测评测对象类型枚举
 */
public enum EvaTargetType {
    /**
     * 模型
     */
    @SerializedName("BuiltinModel")
    TargetTypeBuiltinModel("BuiltinModel"),

    /**
     * 智能体
     */
    @SerializedName("BuiltinAgent")
    TargetTypeBuiltinAgent("BuiltinAgent"),

    /**
     * 提示词
     */
    @SerializedName("BuiltinPrompt")
    TargetTypeBuiltinPrompt("BuiltinPrompt"),

    /**
     * 集成应用
     */
    @SerializedName("CustomAPP")
    TargetTypeCustomAPP("CustomAPP"),

    /**
     * 自定义
     */
    @SerializedName("Custom")
    TargetTypeCustom("Custom");

    private final String value;

    EvaTargetType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
