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
import com.volcengine.model.AbstractResponse;

public class QueryAppSkillAsyncTaskResponse extends AbstractResponse {

    @SerializedName("Infos")
    private AppSkillAsyncTaskInfo infos;

    public AppSkillAsyncTaskInfo getInfos() {
        return infos;
    }

    public void setInfos(AppSkillAsyncTaskInfo infos) {
        this.infos = infos;
    }

    public static class AppSkillAsyncTaskInfo {

        @SerializedName("TaskID")
        private String taskID;

        @SerializedName("Status")
        private String status;

        @SerializedName("OriginMessageID")
        private String originMessageID;

        @SerializedName("Reason")
        private String reason;

        @SerializedName("MessageID")
        private Boolean messageID;

        public String getTaskID() {
            return taskID;
        }

        public void setTaskID(String taskID) {
            this.taskID = taskID;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOriginMessageID() {
            return originMessageID;
        }

        public void setOriginMessageID(String originMessageID) {
            this.originMessageID = originMessageID;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public Boolean getMessageID() {
            return messageID;
        }

        public void setMessageID(Boolean messageID) {
            this.messageID = messageID;
        }
    }
}
