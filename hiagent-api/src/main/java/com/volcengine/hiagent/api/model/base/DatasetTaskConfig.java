package com.volcengine.hiagent.api.model.base;

public class DatasetTaskConfig {
    private int DataStartIndex; 
    private int DataEndIndex; 
    private boolean UseLatestVersion;

    public DatasetTaskConfig() {
    }

    public DatasetTaskConfig(int dataStartIndex, int dataEndIndex, boolean useLatestVersion) {
        DataStartIndex = dataStartIndex;
        DataEndIndex = dataEndIndex;
        UseLatestVersion = useLatestVersion;
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
}
