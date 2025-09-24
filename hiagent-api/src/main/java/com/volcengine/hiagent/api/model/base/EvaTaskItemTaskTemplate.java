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
package com.volcengine.hiagent.api.model.base;

public class EvaTaskItemTaskTemplate {
    private String TaskTemplateID;
    private String TaskTemplateName;
    private boolean IsDeleted;

    public EvaTaskItemTaskTemplate() {
    }

    public EvaTaskItemTaskTemplate(String taskTemplateID, String taskTemplateName, boolean isDeleted) {
        TaskTemplateID = taskTemplateID;
        TaskTemplateName = taskTemplateName;
        IsDeleted = isDeleted;
    }

    public String getTaskTemplateID() {
        return TaskTemplateID;
    }

    public void setTaskTemplateID(String taskTemplateID) {
        TaskTemplateID = taskTemplateID;
    }

    public String getTaskTemplateName() {
        return TaskTemplateName;
    }

    public void setTaskTemplateName(String taskTemplateName) {
        TaskTemplateName = taskTemplateName;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }
}
