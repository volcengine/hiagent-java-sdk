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

public class ListDatasetDataFilter {
    private boolean IsAnnotated;
    @Nullable
    private List<String> DatasetCaseIDs;

    public ListDatasetDataFilter() {
    }

    public ListDatasetDataFilter(boolean isAnnotated, @Nullable List<String> datasetCaseIDs) {
        IsAnnotated = isAnnotated;
        DatasetCaseIDs = datasetCaseIDs;
    }

    public boolean isAnnotated() {
        return IsAnnotated;
    }

    public void setAnnotated(boolean annotated) {
        IsAnnotated = annotated;
    }

    @Nullable
    public List<String> getDatasetCaseIDs() {
        return DatasetCaseIDs;
    }

    public void setDatasetCaseIDs(@Nullable List<String> datasetCaseIDs) {
        DatasetCaseIDs = datasetCaseIDs;
    }
}
