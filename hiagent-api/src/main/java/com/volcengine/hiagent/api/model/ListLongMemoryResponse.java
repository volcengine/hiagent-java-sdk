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

public class ListLongMemoryResponse extends AbstractResponse {

    @SerializedName("Total")
    private Integer total;

    @SerializedName("Items")
    private List<LongMemoryItem> Items;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<LongMemoryItem> getItems() {
        return Items;
    }

    public void setItems(List<LongMemoryItem> items) {
        Items = items;
    }

    public static class LongMemoryItem {

        @SerializedName("MemoryID")
        private String memoryID;

        @SerializedName("Memory")
        private String memory;

        @SerializedName("MemoryVectorRawDim")
        private Integer memoryVectorRawDim;

        @SerializedName("CreateTimestamp")
        private Integer createTimestamp;

        @SerializedName("UpdateTimestamp")
        private Integer updateTimestamp;

        public String getMemoryID() {
            return memoryID;
        }

        public void setMemoryID(String memoryID) {
            this.memoryID = memoryID;
        }

        public String getMemory() {
            return memory;
        }

        public void setMemory(String memory) {
            this.memory = memory;
        }

        public Integer getMemoryVectorRawDim() {
            return memoryVectorRawDim;
        }

        public void setMemoryVectorRawDim(Integer memoryVectorRawDim) {
            this.memoryVectorRawDim = memoryVectorRawDim;
        }

        public Integer getCreateTimestamp() {
            return createTimestamp;
        }

        public void setCreateTimestamp(Integer createTimestamp) {
            this.createTimestamp = createTimestamp;
        }

        public Integer getUpdateTimestamp() {
            return updateTimestamp;
        }

        public void setUpdateTimestamp(Integer updateTimestamp) {
            this.updateTimestamp = updateTimestamp;
        }
    }
}
