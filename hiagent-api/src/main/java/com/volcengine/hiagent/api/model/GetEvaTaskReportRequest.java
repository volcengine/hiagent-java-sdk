package com.volcengine.hiagent.api.model;

public class GetEvaTaskReportRequest {
    private String WorkspaceID;
    private String TaskID;

    public GetEvaTaskReportRequest() {
    }

    public GetEvaTaskReportRequest(String workspaceID, String taskID) {
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
