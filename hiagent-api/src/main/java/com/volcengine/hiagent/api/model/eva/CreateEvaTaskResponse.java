package com.volcengine.hiagent.api.model.eva;

public class CreateEvaTaskResponse {
    private String TaskID;

    public CreateEvaTaskResponse() {
    }

    public CreateEvaTaskResponse(String taskID) {
        TaskID = taskID;
    }

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String taskID) {
        TaskID = taskID;
    }
}
