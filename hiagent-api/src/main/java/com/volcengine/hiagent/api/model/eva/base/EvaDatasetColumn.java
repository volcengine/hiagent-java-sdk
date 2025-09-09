package com.volcengine.hiagent.api.model.eva.base;

import org.jetbrains.annotations.Nullable;

public class EvaDatasetColumn {
    private String ID;
    private String Name;
    @Nullable
    private String Description;
    private String Schema;

    public EvaDatasetColumn() {
    }

    public EvaDatasetColumn(String ID, String name, @Nullable String description, String schema) {
        this.ID = ID;
        Name = name;
        Description = description;
        Schema = schema;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Nullable
    public String getDescription() {
        return Description;
    }

    public void setDescription(@Nullable String description) {
        Description = description;
    }

    public String getSchema() {
        return Schema;
    }

    public void setSchema(String schema) {
        Schema = schema;
    }
}
