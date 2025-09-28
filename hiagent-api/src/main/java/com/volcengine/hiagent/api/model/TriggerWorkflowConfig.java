// Copyright (c) 2024 Bytedance Ltd. and/or its affiliates
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

public class TriggerWorkflowConfig {

    @SerializedName("WorkflowID")
    private String workflowID;

    @SerializedName("InputData")
    private String inputData;

    @SerializedName("InputDataSchema")
    private String inputDataSchema;

    public String getWorkflowID() {
        return workflowID;
    }

    public void setWorkflowID(String workflowID) {
        this.workflowID = workflowID;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getInputDataSchema() {
        return inputDataSchema;
    }

    public void setInputDataSchema(String inputDataSchema) {
        this.inputDataSchema = inputDataSchema;
    }
}
