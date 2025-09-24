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

import com.volcengine.hiagent.api.model.base.EvaTaskResultUpdateTargetContent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExecEvaTaskRowGroupRequest {
    private String WorkspaceID;
    private String TaskID;
    private String RowID;
    @Nullable
    private List<EvaTaskResultUpdateTargetContent> TargetResults;

    public ExecEvaTaskRowGroupRequest() {
    }

    public ExecEvaTaskRowGroupRequest(String workspaceID, String taskID, String rowID, @Nullable List<EvaTaskResultUpdateTargetContent> targetResults) {
        WorkspaceID = workspaceID;
        TaskID = taskID;
        RowID = rowID;
        TargetResults = targetResults;
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

    public String getRowID() {
        return RowID;
    }

    public void setRowID(String rowID) {
        RowID = rowID;
    }

    @Nullable
    public List<EvaTaskResultUpdateTargetContent> getTargetResults() {
        return TargetResults;
    }

    public void setTargetResults(@Nullable List<EvaTaskResultUpdateTargetContent> targetResults) {
        TargetResults = targetResults;
    }
}
