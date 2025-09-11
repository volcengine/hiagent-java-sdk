package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

public class EvaTaskTarget {
    private String Type;
    private String TargetID;
    private String TargetName;
    @Nullable
    public String TargetIcon;
    private EvaTargetConfig TargetConfig;
    private long QPS;
    @Nullable
    private EvaTaskTargetParams Params;

    public EvaTaskTarget() {
    }

    public EvaTaskTarget(String type, String targetID, String targetName, @Nullable String targetIcon, EvaTargetConfig targetConfig, long QPS, @Nullable EvaTaskTargetParams params) {
        Type = type;
        TargetID = targetID;
        TargetName = targetName;
        TargetIcon = targetIcon;
        TargetConfig = targetConfig;
        this.QPS = QPS;
        Params = params;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTargetID() {
        return TargetID;
    }

    public void setTargetID(String targetID) {
        TargetID = targetID;
    }

    public String getTargetName() {
        return TargetName;
    }

    public void setTargetName(String targetName) {
        TargetName = targetName;
    }

    @Nullable
    public String getTargetIcon() {
        return TargetIcon;
    }

    public void setTargetIcon(@Nullable String targetIcon) {
        TargetIcon = targetIcon;
    }

    public EvaTargetConfig getTargetConfig() {
        return TargetConfig;
    }

    public void setTargetConfig(EvaTargetConfig targetConfig) {
        TargetConfig = targetConfig;
    }

    public long getQPS() {
        return QPS;
    }

    public void setQPS(long QPS) {
        this.QPS = QPS;
    }

    @Nullable
    public EvaTaskTargetParams getParams() {
        return Params;
    }

    public void setParams(@Nullable EvaTaskTargetParams params) {
        Params = params;
    }
}
