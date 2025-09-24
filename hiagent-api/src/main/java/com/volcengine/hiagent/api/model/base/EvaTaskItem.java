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

public class EvaTaskItem {
    private String TenantID;
    private String WorkspaceID;
    private String TaskID;
    private String Name;
    @Nullable
    private String Description;
    @Nullable
    private EvaTaskItemTaskTemplate TaskTemplate;
    private String TargetType;
    private EvaTaskItemRuleset Ruleset;
    private EvaTaskItemDataset Dataset;
    @Nullable
    private java.util.List<EvaTaskItemTarget> Targets;
    @Nullable
    private String StartedAt;
    @Nullable
    private String CompletedAt;
    private long Duration;
    private long CostTokens;
    @Nullable
    private EvaTaskItemProgress Progress;
    private String Status;
    @Nullable
    private String StatusMessage;
    private String CreatedAt;
    private String UpdatedAt;
    private String CreatedBy;
    private String UpdatedBy;

    public EvaTaskItem() {
    }

    public EvaTaskItem(String tenantID, String workspaceID, String taskID, String name, @Nullable String description, @Nullable EvaTaskItemTaskTemplate taskTemplate, String targetType, EvaTaskItemRuleset ruleset, EvaTaskItemDataset dataset, @Nullable List<EvaTaskItemTarget> targets, @Nullable String startedAt, @Nullable String completedAt, long duration, long costTokens, @Nullable EvaTaskItemProgress progress, String status, @Nullable String statusMessage, String createdAt, String updatedAt, String createdBy, String updatedBy) {
        TenantID = tenantID;
        WorkspaceID = workspaceID;
        TaskID = taskID;
        Name = name;
        Description = description;
        TaskTemplate = taskTemplate;
        TargetType = targetType;
        Ruleset = ruleset;
        Dataset = dataset;
        Targets = targets;
        StartedAt = startedAt;
        CompletedAt = completedAt;
        Duration = duration;
        CostTokens = costTokens;
        Progress = progress;
        Status = status;
        StatusMessage = statusMessage;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
        CreatedBy = createdBy;
        UpdatedBy = updatedBy;
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

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String taskID) {
        TaskID = taskID;
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

    @Nullable
    public EvaTaskItemTaskTemplate getTaskTemplate() {
        return TaskTemplate;
    }

    public void setTaskTemplate(@Nullable EvaTaskItemTaskTemplate taskTemplate) {
        TaskTemplate = taskTemplate;
    }

    public String getTargetType() {
        return TargetType;
    }

    public void setTargetType(String targetType) {
        TargetType = targetType;
    }

    public EvaTaskItemRuleset getRuleset() {
        return Ruleset;
    }

    public void setRuleset(EvaTaskItemRuleset ruleset) {
        Ruleset = ruleset;
    }

    public EvaTaskItemDataset getDataset() {
        return Dataset;
    }

    public void setDataset(EvaTaskItemDataset dataset) {
        Dataset = dataset;
    }

    @Nullable
    public List<EvaTaskItemTarget> getTargets() {
        return Targets;
    }

    public void setTargets(@Nullable List<EvaTaskItemTarget> targets) {
        Targets = targets;
    }

    @Nullable
    public String getStartedAt() {
        return StartedAt;
    }

    public void setStartedAt(@Nullable String startedAt) {
        StartedAt = startedAt;
    }

    @Nullable
    public String getCompletedAt() {
        return CompletedAt;
    }

    public void setCompletedAt(@Nullable String completedAt) {
        CompletedAt = completedAt;
    }

    public long getDuration() {
        return Duration;
    }

    public void setDuration(long duration) {
        Duration = duration;
    }

    public long getCostTokens() {
        return CostTokens;
    }

    public void setCostTokens(long costTokens) {
        CostTokens = costTokens;
    }

    @Nullable
    public EvaTaskItemProgress getProgress() {
        return Progress;
    }

    public void setProgress(@Nullable EvaTaskItemProgress progress) {
        Progress = progress;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Nullable
    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(@Nullable String statusMessage) {
        StatusMessage = statusMessage;
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
}
