package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

public class UserVariables {

    @SerializedName("Name")
    private String name;

    @SerializedName("Description")
    private String description;

    @SerializedName("Scope")
    private String scope;

    @SerializedName("Value")
    private String value;

    @SerializedName("Default")
    private String defaultValue;

    @SerializedName("UpdateTime")
    private String updateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDefault() {
        return defaultValue;
    }

    public void setDefault(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
