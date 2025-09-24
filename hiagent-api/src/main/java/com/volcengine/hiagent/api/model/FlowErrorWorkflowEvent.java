package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.WorkflowEvent;

public class FlowErrorWorkflowEvent extends WorkflowEvent {

    @SerializedName("error_message")
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }
}
