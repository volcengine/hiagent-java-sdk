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

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class DatasetCaseItem {
    @Nullable
    private String DatasetCaseID;
    @Nullable
    private Map<String,Cell> Data;
    @Nullable
    private List<Map<String,Cell>> RepeatedData;
    private boolean IsAnnotated;

    public DatasetCaseItem() {
    }

    public DatasetCaseItem(@Nullable String datasetCaseID, @Nullable Map<String, Cell> data, @Nullable List<Map<String, Cell>> repeatedData, boolean isAnnotated) {
        DatasetCaseID = datasetCaseID;
        Data = data;
        RepeatedData = repeatedData;
        IsAnnotated = isAnnotated;
    }

    @Nullable
    public String getDatasetCaseID() {
        return DatasetCaseID;
    }

    public void setDatasetCaseID(@Nullable String datasetCaseID) {
        DatasetCaseID = datasetCaseID;
    }

    @Nullable
    public Map<String, Cell> getData() {
        return Data;
    }

    public void setData(@Nullable Map<String, Cell> data) {
        Data = data;
    }

    @Nullable
    public List<Map<String, Cell>> getRepeatedData() {
        return RepeatedData;
    }

    public void setRepeatedData(@Nullable List<Map<String, Cell>> repeatedData) {
        RepeatedData = repeatedData;
    }

    public boolean isAnnotated() {
        return IsAnnotated;
    }

    public void setAnnotated(boolean annotated) {
        IsAnnotated = annotated;
    }
}
