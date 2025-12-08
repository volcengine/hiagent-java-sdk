package com.volcengine.hiagent.api.model;

import com.volcengine.hiagent.api.model.base.EvaTaskStatus;
import org.jetbrains.annotations.Nullable;

public class UpdateEvaTaskRequest {
    public String WorkspaceID;
    public String TaskID;
    @Nullable
    public String Description;
    @Nullable
    public EvaTaskStatus Status;

    public UpdateEvaTaskRequest() {
    }

    public UpdateEvaTaskRequest(String workspaceID, String taskID, @Nullable String description, @Nullable EvaTaskStatus status) {
        WorkspaceID = workspaceID;
        TaskID = taskID;
        Description = description;
        Status = status;
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

    @Nullable
    public String getDescription() {
        return Description;
    }

    public void setDescription(@Nullable String description) {
        Description = description;
    }

    @Nullable
    public EvaTaskStatus getStatus() {
        return Status;
    }

    public void setStatus(@Nullable EvaTaskStatus status) {
        Status = status;
    }
}
