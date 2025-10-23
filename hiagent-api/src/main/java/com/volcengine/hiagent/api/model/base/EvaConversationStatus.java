package com.volcengine.hiagent.api.model.base;

import com.google.gson.annotations.SerializedName;

/**
 * 评测评测任务结果状态类型枚举
 */
public enum EvaConversationStatus {
    /**
     * 成功状态
     */
    @SerializedName("Succeed")
    EvaConversationStatusSucceed("Succeed"),

    /**
     * 失败状态
     */
    @SerializedName("Failed")
    EvaConversationStatusFailed("Failed"),

    /**
     * 运行中状态
     */
    @SerializedName("Running")
    EvaConversationStatusRunning("Running"),

    /**
     * 待处理状态
     */
    @SerializedName("Pending")
    EvaConversationStatusPending("Pending"),

    /**
     * 排队中状态
     */
    @SerializedName("Queuing")
    EvaConversationStatusQueuing("Queuing"),

    /**
     * 已暂停状态
     */
    @SerializedName("Paused")
    EvaConversationStatusPaused("Paused");

    private final String value;

    EvaConversationStatus(String value) {
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