package com.volcengine.hiagent.api.model;

import com.volcengine.hiagent.api.model.base.EvaTaskRetryOption;

public class RetryEvaTaskRequest {
    public String WorkspaceID;
    public String TaskID;
    public EvaTaskRetryOption Option;

    public RetryEvaTaskRequest() {
    }

    public RetryEvaTaskRequest(String workspaceID, String taskID, EvaTaskRetryOption option) {
        WorkspaceID = workspaceID;
        TaskID = taskID;
        Option = option;
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

    public EvaTaskRetryOption getOption() {
        return Option;
    }

    public void setOption(EvaTaskRetryOption option) {
        Option = option;
    }
}
