package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.model.AbstractResponse;

import java.util.List;

public class GetAppConfigPreviewResponse extends AbstractResponse {

    @SerializedName("TenantID")
    private String tenantID;

    @SerializedName("WorkspaceID")
    private String workspaceID;

    @SerializedName("VariableConfigs")
    private List<VariableConfig> variableConfigs;

    @SerializedName("Name")
    private String name;

    @SerializedName("OpenMessage")
    private String openMessage;

    @SerializedName("openQuery")
    private String OpenQuery;

    @SerializedName("Icon")
    private String icon;

    @SerializedName("Background")
    private String background;

    @SerializedName("SuggestEnabled")
    private Boolean SuggestEnabled;

    @SerializedName("Image")
    private String image;

    @SerializedName("AgentMode")
    private String agentMode;

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getWorkspaceID() {
        return workspaceID;
    }

    public void setWorkspaceID(String workspaceID) {
        this.workspaceID = workspaceID;
    }

    public List<VariableConfig> getVariableConfigs() {
        return variableConfigs;
    }

    public void setVariableConfigs(List<VariableConfig> variableConfigs) {
        this.variableConfigs = variableConfigs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenMessage() {
        return openMessage;
    }

    public void setOpenMessage(String openMessage) {
        this.openMessage = openMessage;
    }

    public String getOpenQuery() {
        return OpenQuery;
    }

    public void setOpenQuery(String openQuery) {
        OpenQuery = openQuery;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Boolean getSuggestEnabled() {
        return SuggestEnabled;
    }

    public void setSuggestEnabled(Boolean suggestEnabled) {
        SuggestEnabled = suggestEnabled;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAgentMode() {
        return agentMode;
    }

    public void setAgentMode(String agentMode) {
        this.agentMode = agentMode;
    }

    public static class VariableConfig {
        @SerializedName("Key")
        private String key;

        @SerializedName("Description")
        private String description;

        @SerializedName("ShowName")
        private String showName;

        @SerializedName("Required")
        private Boolean required;

        @SerializedName("VariableType")
        private String variableType;

        @SerializedName("EnumValues")
        private List<String> enumValues;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getShowName() {
            return showName;
        }

        public void setShowName(String showName) {
            this.showName = showName;
        }

        public Boolean getRequired() {
            return required;
        }

        public void setRequired(Boolean required) {
            this.required = required;
        }

        public String getVariableType() {
            return variableType;
        }

        public void setVariableType(String variableType) {
            this.variableType = variableType;
        }

        public List<String> getEnumValues() {
            return enumValues;
        }

        public void setEnumValues(List<String> enumValues) {
            this.enumValues = enumValues;
        }
    }
}
