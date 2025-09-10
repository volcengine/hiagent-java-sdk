package com.volcengine.hiagent.api.model.eva;

import org.jetbrains.annotations.Nullable;

public class GetEvaTaskRequest {
    private String WorkspaceID;
    @Nullable
    private String TaskID;
    @Nullable
    private String TaskName;

    public GetEvaTaskRequest() {
    }

    public GetEvaTaskRequest(String workspaceID, @Nullable String taskID, @Nullable String taskName) {
        WorkspaceID = workspaceID;
        TaskID = taskID;
        TaskName = taskName;
    }

    public String getWorkspaceID() {
        return WorkspaceID;
    }

    public void setWorkspaceID(String workspaceID) {
        WorkspaceID = workspaceID;
    }

    @Nullable
    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(@Nullable String taskID) {
        TaskID = taskID;
    }

    @Nullable
    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(@Nullable String taskName) {
        TaskName = taskName;
    }
}
