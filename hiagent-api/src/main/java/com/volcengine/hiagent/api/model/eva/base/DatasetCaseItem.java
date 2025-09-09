package com.volcengine.hiagent.api.model.eva.base;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class DatasetCaseItem {
    @Nullable
    private String DatasetCaseID;
    @Nullable
    private Map<String,Cell> Data;
    @Nullable
    private List<Map<String,Cell>> RepeatedData;
    private boolean IsAnnotated;

    public DatasetCaseItem() {
    }

    public DatasetCaseItem(@Nullable String datasetCaseID, @Nullable Map<String, Cell> data, @Nullable List<Map<String, Cell>> repeatedData, boolean isAnnotated) {
        DatasetCaseID = datasetCaseID;
        Data = data;
        RepeatedData = repeatedData;
        IsAnnotated = isAnnotated;
    }

    @Nullable
    public String getDatasetCaseID() {
        return DatasetCaseID;
    }

    public void setDatasetCaseID(@Nullable String datasetCaseID) {
        DatasetCaseID = datasetCaseID;
    }

    @Nullable
    public Map<String, Cell> getData() {
        return Data;
    }

    public void setData(@Nullable Map<String, Cell> data) {
        Data = data;
    }

    @Nullable
    public List<Map<String, Cell>> getRepeatedData() {
        return RepeatedData;
    }

    public void setRepeatedData(@Nullable List<Map<String, Cell>> repeatedData) {
        RepeatedData = repeatedData;
    }

    public boolean isAnnotated() {
        return IsAnnotated;
    }

    public void setAnnotated(boolean annotated) {
        IsAnnotated = annotated;
    }
}
