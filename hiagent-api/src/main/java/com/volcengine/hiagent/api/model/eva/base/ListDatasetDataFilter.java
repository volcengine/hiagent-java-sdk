package com.volcengine.hiagent.api.model.eva.base;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ListDatasetDataFilter {
    private boolean IsAnnotated;
    @Nullable
    private List<String> DatasetCaseIDs;

    public ListDatasetDataFilter() {
    }

    public ListDatasetDataFilter(boolean isAnnotated, @Nullable List<String> datasetCaseIDs) {
        IsAnnotated = isAnnotated;
        DatasetCaseIDs = datasetCaseIDs;
    }

    public boolean isAnnotated() {
        return IsAnnotated;
    }

    public void setAnnotated(boolean annotated) {
        IsAnnotated = annotated;
    }

    @Nullable
    public List<String> getDatasetCaseIDs() {
        return DatasetCaseIDs;
    }

    public void setDatasetCaseIDs(@Nullable List<String> datasetCaseIDs) {
        DatasetCaseIDs = datasetCaseIDs;
    }
}
