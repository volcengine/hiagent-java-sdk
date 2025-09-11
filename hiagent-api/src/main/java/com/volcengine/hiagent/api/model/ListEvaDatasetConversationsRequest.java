package com.volcengine.hiagent.api.model;

import com.volcengine.hiagent.api.model.base.ListDatasetDataFilter;
import com.volcengine.hiagent.api.model.base.ListSort;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ListEvaDatasetConversationsRequest {
    private String WorkspaceID;
    private String DatasetID;
    @Nullable
    private String VersionID;
    private boolean UseLatestPublishedVersion;
    @Nullable
    private ListDatasetDataFilter Filter;
    @Nullable
    private List<ListSort> Sorts;
    private long PageNumber;
    private long PageSize;
    private long Offset;

    public ListEvaDatasetConversationsRequest() {
    }

    public ListEvaDatasetConversationsRequest(String workspaceID, String datasetID, @Nullable String versionID, boolean useLatestPublishedVersion, @Nullable ListDatasetDataFilter filter, @Nullable List<ListSort> sorts, long pageNumber, long pageSize, long offset) {
        WorkspaceID = workspaceID;
        DatasetID = datasetID;
        VersionID = versionID;
        UseLatestPublishedVersion = useLatestPublishedVersion;
        Filter = filter;
        Sorts = sorts;
        PageNumber = pageNumber;
        PageSize = pageSize;
        Offset = offset;
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

    @Nullable
    public ListDatasetDataFilter getFilter() {
        return Filter;
    }

    public void setFilter(@Nullable ListDatasetDataFilter filter) {
        Filter = filter;
    }

    @Nullable
    public List<ListSort> getSorts() {
        return Sorts;
    }

    public void setSorts(@Nullable List<ListSort> sorts) {
        Sorts = sorts;
    }

    public long getPageNumber() {
        return PageNumber;
    }

    public void setPageNumber(long pageNumber) {
        PageNumber = pageNumber;
    }

    public long getPageSize() {
        return PageSize;
    }

    public void setPageSize(long pageSize) {
        PageSize = pageSize;
    }

    public long getOffset() {
        return Offset;
    }

    public void setOffset(long offset) {
        Offset = offset;
    }
}
