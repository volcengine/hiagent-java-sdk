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
