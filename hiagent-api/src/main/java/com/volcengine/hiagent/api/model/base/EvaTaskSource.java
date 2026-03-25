package com.volcengine.hiagent.api.model.base;

import com.google.gson.annotations.SerializedName;

public enum EvaTaskSource {
    /**
     * 评测任务类型：离线评测
     */
    @SerializedName("Dataset")
    EvaTaskSourceDataset("Dataset"),

    /**
     * 评测任务类型：在线评测
     */
    @SerializedName("Trace")
    EvaTaskSourceTrace("Trace"),
    ;

    EvaTaskSource(String value) {
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
