package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

public class EvaRuleset {
    private String TenantID;
    private String WorkspaceID;
    private String RulesetID;
    private String Name;
    @Nullable
    private String Description;
    private long RuleCount;
    private String CreatedAt;
    private String UpdatedAt;
    private String CreatedBy;
    private String UpdatedBy;
    private boolean IsDeleted;

    public EvaRuleset() {
    }

    public EvaRuleset(String tenantID, String workspaceID, String rulesetID, String name, @Nullable String description, long ruleCount, String createdAt, String updatedAt, String createdBy, String updatedBy, boolean isDeleted) {
        TenantID = tenantID;
        WorkspaceID = workspaceID;
        RulesetID = rulesetID;
        Name = name;
        Description = description;
        RuleCount = ruleCount;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
        CreatedBy = createdBy;
        UpdatedBy = updatedBy;
        IsDeleted = isDeleted;
    }

    public String getTenantID() {
        return TenantID;
    }

    public void setTenantID(String tenantID) {
        TenantID = tenantID;
    }

    public String getWorkspaceID() {
        return WorkspaceID;
    }

    public void setWorkspaceID(String workspaceID) {
        WorkspaceID = workspaceID;
    }

    public String getRulesetID() {
        return RulesetID;
    }

    public void setRulesetID(String rulesetID) {
        RulesetID = rulesetID;
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

    public long getRuleCount() {
        return RuleCount;
    }

    public void setRuleCount(long ruleCount) {
        RuleCount = ruleCount;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }
}
