// Copyright (c) 2024 Bytedance Ltd. and/or its affiliates
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EvaRule {
    private String TenantID;
    private String WorkspaceID;
    private String RuleID;
    private String Name;
    @Nullable
    private String Description;
    private String Type;
    private EvaRuleConfig Config;
    private String CreatedAt;
    private String UpdatedAt;
    private String CreatedBy;
    private String UpdatedBy;
    private boolean IsDeleted;
    @Nullable
    private String RuleVersionID;
    @Nullable
    private String RuleVersionName;
    @Nullable
    private List<String> Labels;

    public EvaRule() {
    }

    public EvaRule(String tenantID, String workspaceID, String ruleID, String name, @Nullable String description, String type, EvaRuleConfig config, String createdAt, String updatedAt, String createdBy, String updatedBy, boolean isDeleted, @Nullable String ruleVersionID, @Nullable String ruleVersionName, @Nullable List<String> labels) {
        TenantID = tenantID;
        WorkspaceID = workspaceID;
        RuleID = ruleID;
        Name = name;
        Description = description;
        Type = type;
        Config = config;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
        CreatedBy = createdBy;
        UpdatedBy = updatedBy;
        IsDeleted = isDeleted;
        RuleVersionID = ruleVersionID;
        RuleVersionName = ruleVersionName;
        Labels = labels;
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

    public String getRuleID() {
        return RuleID;
    }

    public void setRuleID(String ruleID) {
        RuleID = ruleID;
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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public EvaRuleConfig getConfig() {
        return Config;
    }

    public void setConfig(EvaRuleConfig config) {
        Config = config;
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

    @Nullable
    public String getRuleVersionID() {
        return RuleVersionID;
    }

    public void setRuleVersionID(@Nullable String ruleVersionID) {
        RuleVersionID = ruleVersionID;
    }

    @Nullable
    public String getRuleVersionName() {
        return RuleVersionName;
    }

    public void setRuleVersionName(@Nullable String ruleVersionName) {
        RuleVersionName = ruleVersionName;
    }

    @Nullable
    public List<String> getLabels() {
        return Labels;
    }

    public void setLabels(@Nullable List<String> labels) {
        Labels = labels;
    }
}
