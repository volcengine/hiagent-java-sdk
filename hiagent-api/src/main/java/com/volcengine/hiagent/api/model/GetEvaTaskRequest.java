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
