package com.volcengine.hiagent.api.model;

import com.volcengine.hiagent.api.model.base.EvaTaskResultUpdateTargetContent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExecEvaTaskRowGroupRequest {
    private String WorkspaceID;
    private String TaskID;
    private String RowID;
    @Nullable
    private List<EvaTaskResultUpdateTargetContent> TargetResults;

    public ExecEvaTaskRowGroupRequest() {
    }

    public ExecEvaTaskRowGroupRequest(String workspaceID, String taskID, String rowID, @Nullable List<EvaTaskResultUpdateTargetContent> targetResults) {
        WorkspaceID = workspaceID;
        TaskID = taskID;
        RowID = rowID;
        TargetResults = targetResults;
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

    public String getRowID() {
        return RowID;
    }

    public void setRowID(String rowID) {
        RowID = rowID;
    }

    @Nullable
    public List<EvaTaskResultUpdateTargetContent> getTargetResults() {
        return TargetResults;
    }

    public void setTargetResults(@Nullable List<EvaTaskResultUpdateTargetContent> targetResults) {
        TargetResults = targetResults;
    }
}
