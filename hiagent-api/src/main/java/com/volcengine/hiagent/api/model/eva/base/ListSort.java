package com.volcengine.hiagent.api.model.eva.base;

public class ListSort {
    private String SortBy;
    private String SortOrder;

    public ListSort() {
    }

    public ListSort(String sortBy, String sortOrder) {
        SortBy = sortBy;
        SortOrder = sortOrder;
    }

    public String getSortBy() {
        return SortBy;
    }

    public void setSortBy(String sortBy) {
        SortBy = sortBy;
    }

    public String getSortOrder() {
        return SortOrder;
    }

    public void setSortOrder(String sortOrder) {
        SortOrder = sortOrder;
    }
}
