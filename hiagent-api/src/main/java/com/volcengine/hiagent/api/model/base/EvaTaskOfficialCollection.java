package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

public class EvaTaskOfficialCollection {
    @Nullable
    private String CollectionID;
    @Nullable
    private String CollectionName;

    public EvaTaskOfficialCollection() {
    }

    public EvaTaskOfficialCollection(@Nullable String collectionID, @Nullable String collectionName) {
        CollectionID = collectionID;
        CollectionName = collectionName;
    }

    @Nullable
    public String getCollectionID() {
        return CollectionID;
    }

    public void setCollectionID(@Nullable String collectionID) {
        CollectionID = collectionID;
    }

    @Nullable
    public String getCollectionName() {
        return CollectionName;
    }

    public void setCollectionName(@Nullable String collectionName) {
        CollectionName = collectionName;
    }
}
