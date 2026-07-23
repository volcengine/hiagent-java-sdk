package com.volcengine.hiagent.api.model.base;

import com.google.gson.annotations.SerializedName;

public enum EvaTaskRunConfigInitiator {
    /**
     * 平台发起
     */
    @SerializedName("Platform")
    EvaTaskRunConfigInitiatorPlatform("Platform"),

    /**
     * SDK 发起
     */
    @SerializedName("SDK")
    EvaTaskRunConfigInitiatorSDK("SDK");

    private final String value;

    EvaTaskRunConfigInitiator(String value) {
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
