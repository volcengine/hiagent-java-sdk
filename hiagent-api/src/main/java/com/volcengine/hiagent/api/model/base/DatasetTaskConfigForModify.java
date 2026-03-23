package com.volcengine.hiagent.api.model.base;


import org.jetbrains.annotations.Nullable;

public class DatasetTaskConfigForModify {
    private String ID;
    private String VersionID;
    private int DataStartIndex;
    private int DataEndIndex;
    private boolean UseLatestVersion;


    public DatasetTaskConfigForModify() {
    }

    public DatasetTaskConfigForModify(String ID, String versionID, int dataStartIndex, int dataEndIndex, boolean useLatestVersion) {
        DataStartIndex = dataStartIndex;
        DataEndIndex = dataEndIndex;
        UseLatestVersion = useLatestVersion;
        this.ID = ID;
        VersionID = versionID;
    }

    public int getDataStartIndex() {
        return DataStartIndex;
    }

    public void setDataStartIndex(int dataStartIndex) {
        DataStartIndex = dataStartIndex;
    }

    public int getDataEndIndex() {
        return DataEndIndex;
    }

    public void setDataEndIndex(int dataEndIndex) {
        DataEndIndex = dataEndIndex;
    }

    public boolean isUseLatestVersion() {
        return UseLatestVersion;
    }

    public void setUseLatestVersion(boolean useLatestVersion) {
        UseLatestVersion = useLatestVersion;
    }

    public String getVersionID() {
        return VersionID;
    }

    public void setVersionID(String versionID) {
        VersionID = versionID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
