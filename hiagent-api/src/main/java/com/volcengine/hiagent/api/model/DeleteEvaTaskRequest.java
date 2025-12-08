package com.volcengine.hiagent.api.model;

public class DeleteEvaTaskRequest {
    public String WorkspaceID;
    public String TaskID;

    public DeleteEvaTaskRequest() {
    }

    public DeleteEvaTaskRequest(String workspaceID, String taskID) {
        WorkspaceID = workspaceID;
        TaskID = taskID;
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
}
