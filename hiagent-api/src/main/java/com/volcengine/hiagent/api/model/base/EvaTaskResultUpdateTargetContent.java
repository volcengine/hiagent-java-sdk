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

import java.util.List;

public class EvaTaskResultUpdateTargetContent {
    private EvaTargetType TargetType;
    private String TargetID;
    private List<EvaTaskResultTargetContentPair> Results;

    public EvaTaskResultUpdateTargetContent() {
    }

    public EvaTaskResultUpdateTargetContent(EvaTargetType targetType, String targetID, List<EvaTaskResultTargetContentPair> results) {
        TargetType = targetType;
        TargetID = targetID;
        Results = results;
    }

    public EvaTargetType getTargetType() {
        return TargetType;
    }

    public void setTargetType(EvaTargetType targetType) {
        TargetType = targetType;
    }

    public String getTargetID() {
        return TargetID;
    }

    public void setTargetID(String targetID) {
        TargetID = targetID;
    }

    public List<EvaTaskResultTargetContentPair> getResults() {
        return Results;
    }

    public void setResults(List<EvaTaskResultTargetContentPair> results) {
        Results = results;
    }
}
