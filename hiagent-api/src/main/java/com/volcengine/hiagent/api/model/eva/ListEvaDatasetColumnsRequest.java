package com.volcengine.hiagent.api.model.eva;

public class ListEvaDatasetColumnsRequest {
    private String WorkspaceID;
    private String DatasetID;
    private String VersionID;
    private boolean UseLatestPublishedVersion;

    public ListEvaDatasetColumnsRequest() {
    }

    public ListEvaDatasetColumnsRequest(String workspaceID, String datasetID, String versionID, boolean useLatestPublishedVersion) {
        WorkspaceID = workspaceID;
        DatasetID = datasetID;
        VersionID = versionID;
        UseLatestPublishedVersion = useLatestPublishedVersion;
    }

    public String getWorkspaceID() {
        return WorkspaceID;
    }

    public void setWorkspaceID(String workspaceID) {
        WorkspaceID = workspaceID;
    }

    public String getDatasetID() {
        return DatasetID;
    }

    public void setDatasetID(String datasetID) {
        DatasetID = datasetID;
    }

    public String getVersionID() {
        return VersionID;
    }

    public void setVersionID(String versionID) {
        VersionID = versionID;
    }

    public boolean isUseLatestPublishedVersion() {
        return UseLatestPublishedVersion;
    }

    public void setUseLatestPublishedVersion(boolean useLatestPublishedVersion) {
        UseLatestPublishedVersion = useLatestPublishedVersion;
    }
}
