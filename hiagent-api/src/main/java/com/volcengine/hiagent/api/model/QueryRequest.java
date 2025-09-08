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

public class QueryRequest {
    @SerializedName("WorkspaceID")
    private String workspaceID = null;

    @SerializedName("DatasetIds")
    private List<String> datasetIDs = null;

    @SerializedName("Keywords")
    private List<String> keywords = null;

    @SerializedName("TopK")
    private Integer topK = null;

    @SerializedName("ScoreThreshold")
    private Double scoreThreshold = null;

    @SerializedName("RerankID")
    private String rerankID = null;

    @SerializedName("Expand")
    private Boolean expand = null;

    @SerializedName("ExpandNum")
    private Integer expandNum = null;

    @SerializedName("Type")
    private Integer type = null;

    @SerializedName("RetrievalSearchMethod")
    private Integer retrievalSearchMethod = null;

    public String getWorkspaceID() {
        return workspaceID;
    }

    public List<String> getDatasetIDs() {
        return datasetIDs;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public Integer getTopK() {
        return topK;
    }

    public Double getScoreThreshold() {
        return scoreThreshold;
    }

    public String getRerankID() {
        return rerankID;
    }

    public Boolean getExpand() {
        return expand;
    }

    public Integer getExpandNum() {
        return expandNum;
    }

    public Integer getType() {
        return type;
    }

    public Integer getRetrievalSearchMethod() {
        return retrievalSearchMethod;
    }

    public void setWorkspaceID(String workspaceID) {
        this.workspaceID = workspaceID;
    }

    public void setDatasetIDs(List<String> datasetIDs) {
        this.datasetIDs = datasetIDs;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public void setTopK(Integer topK) {
        this.topK = topK;
    }

    public void setScoreThreshold(Double scoreThreshold) {
        this.scoreThreshold = scoreThreshold;
    }

    public void setRerankID(String rerankID) {
        this.rerankID = rerankID;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }

    public void setExpandNum(Integer expandNum) {
        this.expandNum = expandNum;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setRetrievalSearchMethod(Integer retrievalSearchMethod) {
        this.retrievalSearchMethod = retrievalSearchMethod;
    }
}
