package com.volcengine.hiagent.api.model.base;

import com.google.gson.annotations.SerializedName;

/**
 * 评测任务状态枚举
 */
public enum EvaTaskStatus {
    /**
     * 等待状态
     */
    @SerializedName("Waiting")
    EvaTaskStatusWaiting("Waiting"),
    
    /**
     * 待处理状态
     */
    @SerializedName("Pending")
    EvaTaskStatusPending("Pending"),
    
    /**
     * 运行中状态
     */
    @SerializedName("Running")
    EvaTaskStatusRunning("Running"),
    
    /**
     * 成功状态
     */
    @SerializedName("Succeed")
    EvaTaskStatusSucceed("Succeed"),
    
    /**
     * 部分成功状态
     */
    @SerializedName("PartialSucceed")
    EvaTaskStatusPartialSucceed("PartialSucceed"),
    
    /**
     * 失败状态
     */
    @SerializedName("Failed")
    EvaTaskStatusFailed("Failed"),
    
    /**
     * 取消中状态
     */
    @SerializedName("Cancelling")
    EvaTaskStatusCancelling("Cancelling"),
    
    /**
     * 已取消状态
     */
    @SerializedName("Cancelled")
    EvaTaskStatusCancelled("Cancelled"),
    
    /**
     * 暂停中状态
     */
    @SerializedName("Pausing")
    EvaTaskStatusPausing("Pausing"),
    
    /**
     * 已暂停状态
     */
    @SerializedName("Paused")
    EvaTaskStatusPaused("Paused");

    private final String value;

    EvaTaskStatus(String value) {
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
