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

public class ListEvaDatasetColumnsRequest {
    private String WorkspaceID;
    private String DatasetID;
    private String VersionID;
    private boolean UseLatestPublishedVersion;

    public ListEvaDatasetColumnsRequest() {
    }

    public ListEvaDatasetColumnsRequest(String workspaceID, String datasetID, String versionID, boolean useLatestPublishedVersion) {
        WorkspaceID = workspaceID;
        DatasetID = datasetID;
        VersionID = versionID;
        UseLatestPublishedVersion = useLatestPublishedVersion;
    }

    public String getWorkspaceID() {
        return WorkspaceID;
    }

    public void setWorkspaceID(String workspaceID) {
        WorkspaceID = workspaceID;
    }

    public String getDatasetID() {
        return DatasetID;
    }

    public void setDatasetID(String datasetID) {
        DatasetID = datasetID;
    }

    public String getVersionID() {
        return VersionID;
    }

    public void setVersionID(String versionID) {
        VersionID = versionID;
    }

    public boolean isUseLatestPublishedVersion() {
        return UseLatestPublishedVersion;
    }

    public void setUseLatestPublishedVersion(boolean useLatestPublishedVersion) {
        UseLatestPublishedVersion = useLatestPublishedVersion;
    }
}
