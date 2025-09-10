package com.volcengine.hiagent.api.model.eva.base;

public class EvaTaskItemDatasetColumn {
    private String ColumnID;
    private String ColumnName;

    public EvaTaskItemDatasetColumn() {
    }

    public EvaTaskItemDatasetColumn(String columnID, String columnName) {
        ColumnID = columnID;
        ColumnName = columnName;
    }

    public String getColumnID() {
        return ColumnID;
    }

    public void setColumnID(String columnID) {
        ColumnID = columnID;
    }

    public String getColumnName() {
        return ColumnName;
    }

    public void setColumnName(String columnName) {
        ColumnName = columnName;
    }
}

