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
package com.volcengine.hiagent.api.model;

import com.volcengine.hiagent.api.model.base.DatasetTaskConfig;
import com.volcengine.hiagent.api.model.base.EvaTaskTarget;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CreateEvaTaskRequest {
    private String WorkspaceID;
    @Nullable
    private String TaskTemplateID;
    private String Name;
    @Nullable
    private String Description;
    private List<EvaTaskTarget> Targets;
    private String DatasetID;
    private String RulesetID;
    private boolean RunImmediately;
    @Nullable
    private DatasetTaskConfig DatasetConfig;
    @Nullable
    private String DatasetVersionID;

    public CreateEvaTaskRequest() {
    }

    public CreateEvaTaskRequest(String workspaceID, @Nullable String taskTemplateID, String name, @Nullable String description, List<EvaTaskTarget> targets, String datasetID, String rulesetID, boolean runImmediately, @Nullable DatasetTaskConfig datasetConfig, @Nullable String datasetVersionID) {
        WorkspaceID = workspaceID;
        TaskTemplateID = taskTemplateID;
        Name = name;
        Description = description;
        Targets = targets;
        DatasetID = datasetID;
        RulesetID = rulesetID;
        RunImmediately = runImmediately;
        DatasetConfig = datasetConfig;
        DatasetVersionID = datasetVersionID;
    }

    public String getWorkspaceID() {
        return WorkspaceID;
    }

    public void setWorkspaceID(String workspaceID) {
        WorkspaceID = workspaceID;
    }

    @Nullable
    public String getTaskTemplateID() {
        return TaskTemplateID;
    }

    public void setTaskTemplateID(@Nullable String taskTemplateID) {
        TaskTemplateID = taskTemplateID;
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

    public List<EvaTaskTarget> getTargets() {
        return Targets;
    }

    public void setTargets(List<EvaTaskTarget> targets) {
        Targets = targets;
    }

    public String getDatasetID() {
        return DatasetID;
    }

    public void setDatasetID(String datasetID) {
        DatasetID = datasetID;
    }

    public String getRulesetID() {
        return RulesetID;
    }

    public void setRulesetID(String rulesetID) {
        RulesetID = rulesetID;
    }

    public boolean isRunImmediately() {
        return RunImmediately;
    }

    public void setRunImmediately(boolean runImmediately) {
        RunImmediately = runImmediately;
    }

    @Nullable
    public DatasetTaskConfig getDatasetConfig() {
        return DatasetConfig;
    }

    public void setDatasetConfig(@Nullable DatasetTaskConfig datasetConfig) {
        DatasetConfig = datasetConfig;
    }

    @Nullable
    public String getDatasetVersionID() {
        return DatasetVersionID;
    }

    public void setDatasetVersionID(@Nullable String datasetVersionID) {
        DatasetVersionID = datasetVersionID;
    }
}
