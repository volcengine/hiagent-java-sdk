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
import com.volcengine.hiagent.api.model.base.ChatEvent;

import java.util.List;

public class KnowledgeToolOutput extends ChatEvent {

    @SerializedName("outputList")
    private List<KnowledgeDocument> outputList;

    @SerializedName("response_for_model")
    private String responseForModel;

    @SerializedName("filter")
    private String filter;

    public List<KnowledgeDocument> getOutputList() {
        return outputList;
    }

    public void setOutputList(List<KnowledgeDocument> outputList) {
        this.outputList = outputList;
    }

    public String getResponseForModel() {
        return responseForModel;
    }

    public void setResponseForModel(String responseForModel) {
        this.responseForModel = responseForModel;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
