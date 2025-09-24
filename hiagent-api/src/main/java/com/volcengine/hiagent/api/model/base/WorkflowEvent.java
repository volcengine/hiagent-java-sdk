package com.volcengine.hiagent.api.model.base;

import com.google.gson.annotations.SerializedName;

public class WorkflowEvent {

    @SerializedName("event")
    private String event;

    @SerializedName("task_id")
    private String taskId;

    @SerializedName("id")
    private String id;

    @SerializedName("run_id")
    private String runId;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }
}
