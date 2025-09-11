package com.volcengine.hiagent.api.model;

import com.volcengine.hiagent.api.model.base.DatasetCaseItem;

import java.util.List;

public class ListEvaDatasetConversationsResponse {
    private List<DatasetCaseItem> Items;
    private long Total;
    private long AnnotatedTotal;

    public ListEvaDatasetConversationsResponse() {
    }

    public ListEvaDatasetConversationsResponse(List<DatasetCaseItem> items, long total, long annotatedTotal) {
        Items = items;
        Total = total;
        AnnotatedTotal = annotatedTotal;
    }

    public List<DatasetCaseItem> getItems() {
        return Items;
    }

    public void setItems(List<DatasetCaseItem> items) {
        Items = items;
    }

    public long getTotal() {
        return Total;
    }

    public void setTotal(long total) {
        Total = total;
    }

    public long getAnnotatedTotal() {
        return AnnotatedTotal;
    }

    public void setAnnotatedTotal(long annotatedTotal) {
        AnnotatedTotal = annotatedTotal;
    }
}
