package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

public class EvaTargetBuiltinAgentConfig {
    private  String AppID;
    private  String Type;
    @Nullable
    private  String VersionName;
    @Nullable
    private  String VersionID;
    private boolean UseLatestPublishedVersion;

    public EvaTargetBuiltinAgentConfig() {
    }

    public EvaTargetBuiltinAgentConfig(String appID, String type, @Nullable String versionName, @Nullable String versionID, boolean useLatestPublishedVersion) {
        AppID = appID;
        Type = type;
        VersionName = versionName;
        VersionID = versionID;
        UseLatestPublishedVersion = useLatestPublishedVersion;
    }

    public String getAppID() {
        return AppID;
    }

    public void setAppID(String appID) {
        AppID = appID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Nullable
    public String getVersionName() {
        return VersionName;
    }

    public void setVersionName(@Nullable String versionName) {
        VersionName = versionName;
    }

    @Nullable
    public String getVersionID() {
        return VersionID;
    }

    public void setVersionID(@Nullable String versionID) {
        VersionID = versionID;
    }

    public boolean isUseLatestPublishedVersion() {
        return UseLatestPublishedVersion;
    }

    public void setUseLatestPublishedVersion(boolean useLatestPublishedVersion) {
        UseLatestPublishedVersion = useLatestPublishedVersion;
    }
}
