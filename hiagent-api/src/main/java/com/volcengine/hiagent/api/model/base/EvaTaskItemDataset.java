package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EvaTaskItemDataset {
    private  String DatasetID;
    private  String DatasetName;
    @Nullable
    private List<EvaTaskItemDatasetColumn> Columns;
    private boolean IsDeleted;
    @Nullable
    private  String DatasetSource;
    @Nullable
    private  DatasetTaskConfig DatasetConfig;
    private long SourceDataTotal;
    @Nullable
    private  EvaTaskOfficialCollection OfficialCollection;
    @Nullable
    private  String DatasetVersionID;

    public EvaTaskItemDataset() {
    }

    public EvaTaskItemDataset(String datasetID, String datasetName, @Nullable List<EvaTaskItemDatasetColumn> columns, boolean isDeleted, @Nullable String datasetSource, @Nullable DatasetTaskConfig datasetConfig, long sourceDataTotal, @Nullable EvaTaskOfficialCollection officialCollection, @Nullable String datasetVersionID) {
        DatasetID = datasetID;
        DatasetName = datasetName;
        Columns = columns;
        IsDeleted = isDeleted;
        DatasetSource = datasetSource;
        DatasetConfig = datasetConfig;
        SourceDataTotal = sourceDataTotal;
        OfficialCollection = officialCollection;
        DatasetVersionID = datasetVersionID;
    }

    public String getDatasetID() {
        return DatasetID;
    }

    public void setDatasetID(String datasetID) {
        DatasetID = datasetID;
    }

    public String getDatasetName() {
        return DatasetName;
    }

    public void setDatasetName(String datasetName) {
        DatasetName = datasetName;
    }

    @Nullable
    public List<EvaTaskItemDatasetColumn> getColumns() {
        return Columns;
    }

    public void setColumns(@Nullable List<EvaTaskItemDatasetColumn> columns) {
        Columns = columns;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    @Nullable
    public String getDatasetSource() {
        return DatasetSource;
    }

    public void setDatasetSource(@Nullable String datasetSource) {
        DatasetSource = datasetSource;
    }

    @Nullable
    public DatasetTaskConfig getDatasetConfig() {
        return DatasetConfig;
    }

    public void setDatasetConfig(@Nullable DatasetTaskConfig datasetConfig) {
        DatasetConfig = datasetConfig;
    }

    public long getSourceDataTotal() {
        return SourceDataTotal;
    }

    public void setSourceDataTotal(long sourceDataTotal) {
        SourceDataTotal = sourceDataTotal;
    }

    @Nullable
    public EvaTaskOfficialCollection getOfficialCollection() {
        return OfficialCollection;
    }

    public void setOfficialCollection(@Nullable EvaTaskOfficialCollection officialCollection) {
        OfficialCollection = officialCollection;
    }

    @Nullable
    public String getDatasetVersionID() {
        return DatasetVersionID;
    }

    public void setDatasetVersionID(@Nullable String datasetVersionID) {
        DatasetVersionID = datasetVersionID;
    }
}
