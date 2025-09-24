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

import com.volcengine.hiagent.api.model.base.GetEvaTaskReportRule;
import com.volcengine.hiagent.api.model.base.GetEvaTaskReportTarget;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GetEvaTaskReportResponse {
    @Nullable
    private List<GetEvaTaskReportRule> Rules;
    @Nullable
    private  List<GetEvaTaskReportTarget> Targets;
    private  String CreatedAt;
    private  String UpdatedAt;
    private  String CreatedBy;
    private  String UpdatedBy;

    public GetEvaTaskReportResponse() {
    }

    public GetEvaTaskReportResponse(@Nullable List<GetEvaTaskReportRule> rules, @Nullable List<GetEvaTaskReportTarget> targets, String createdAt, String updatedAt, String createdBy, String updatedBy) {
        Rules = rules;
        Targets = targets;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
        CreatedBy = createdBy;
        UpdatedBy = updatedBy;
    }

    @Nullable
    public List<GetEvaTaskReportRule> getRules() {
        return Rules;
    }

    public void setRules(@Nullable List<GetEvaTaskReportRule> rules) {
        Rules = rules;
    }

    @Nullable
    public List<GetEvaTaskReportTarget> getTargets() {
        return Targets;
    }

    public void setTargets(@Nullable List<GetEvaTaskReportTarget> targets) {
        Targets = targets;
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
