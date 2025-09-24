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

import java.util.List;

public class QueryTriggerRunRecordsResponse extends AbstractResponse {

    @SerializedName("total")
    private Integer total;

    @SerializedName("records")
    private List<TriggerRunRecord> records;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<TriggerRunRecord> getRecords() {
        return records;
    }

    public void setRecords(List<TriggerRunRecord> records) {
        this.records = records;
    }

    public static class TriggerRunRecord {

        @SerializedName("RunID")
        private String runID;

        @SerializedName("Name")
        private String name;

        @SerializedName("TriggerConfig")
        private TriggerConfig triggerConfig;

        @SerializedName("Scene")
        private String scene;

        @SerializedName("Query")
        private String query;

        @SerializedName("Answer")
        private String answer;

        @SerializedName("UserID")
        private String userID;

        @SerializedName("Status")
        private String status;

        @SerializedName("Reason")
        private String reason;

        public String getRunID() {
            return runID;
        }

        public void setRunID(String runID) {
            this.runID = runID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public TriggerConfig getTriggerConfig() {
            return triggerConfig;
        }

        public void setTriggerConfig(TriggerConfig triggerConfig) {
            this.triggerConfig = triggerConfig;
        }

        public String getScene() {
            return scene;
        }

        public void setScene(String scene) {
            this.scene = scene;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    public static class TriggerConfig {

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

        @SerializedName("WebhookKey")
        private String webhookKey;

        @SerializedName("BearerToken")
        private String bearerToken;

    }
}
