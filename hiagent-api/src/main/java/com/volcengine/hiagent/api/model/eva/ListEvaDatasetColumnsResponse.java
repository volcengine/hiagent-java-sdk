package com.volcengine.hiagent.api.model.eva;

import com.volcengine.hiagent.api.model.eva.base.EvaDatasetColumn;

import java.util.List;

public class ListEvaDatasetColumnsResponse {
    private List<EvaDatasetColumn> Columns;

    public ListEvaDatasetColumnsResponse() {
    }

    public ListEvaDatasetColumnsResponse(List<EvaDatasetColumn> columns) {
        Columns = columns;
    }

    public List<EvaDatasetColumn> getColumns() {
        return Columns;
    }

    public void setColumns(List<EvaDatasetColumn> columns) {
        Columns = columns;
    }
}
