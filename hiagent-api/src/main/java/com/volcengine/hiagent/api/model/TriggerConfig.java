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

import java.util.List;

public class TriggerConfig {

    @SerializedName("Name")
    private String name;

    @SerializedName("Type")
    private String type;

    @SerializedName("CronExpr")
    private String cronExpr;

    @SerializedName("ShowCronExpr")
    private Boolean showCronExpr;

    @SerializedName("TaskType")
    private String taskType;

    @SerializedName("PromptConfig")
    private TriggerPromptConfig promptConfig;

    @SerializedName("ToolConfig")
    private TriggerToolConfig toolConfig;

    @SerializedName("WorkflowConfig")
    private TriggerWorkflowConfig workflowConfig;

    @SerializedName("WebhookKey")
    private String webhookKey;

    @SerializedName("BearerToken")
    private String bearerToken;

    @SerializedName("NodeParameters")
    private List<NodeParameters> nodeParameters;

}
