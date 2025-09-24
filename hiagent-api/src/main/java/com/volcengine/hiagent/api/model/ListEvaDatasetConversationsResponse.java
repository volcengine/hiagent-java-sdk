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

import com.volcengine.hiagent.api.model.base.DatasetCaseItem;

import java.util.List;

public class ListEvaDatasetConversationsResponse {
    private List<DatasetCaseItem> Items;
    private long Total;
    private long AnnotatedTotal;

    public ListEvaDatasetConversationsResponse() {
    }

    public ListEvaDatasetConversationsResponse(List<DatasetCaseItem> items, long total, long annotatedTotal) {
        Items = items;
        Total = total;
        AnnotatedTotal = annotatedTotal;
    }

    public List<DatasetCaseItem> getItems() {
        return Items;
    }

    public void setItems(List<DatasetCaseItem> items) {
        Items = items;
    }

    public long getTotal() {
        return Total;
    }

    public void setTotal(long total) {
        Total = total;
    }

    public long getAnnotatedTotal() {
        return AnnotatedTotal;
    }

    public void setAnnotatedTotal(long annotatedTotal) {
        AnnotatedTotal = annotatedTotal;
    }
}
