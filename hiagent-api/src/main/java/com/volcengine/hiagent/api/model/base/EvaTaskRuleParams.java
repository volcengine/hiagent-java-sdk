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

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public class EvaTaskRuleParams {
    @JsonProperty("RuleID")
    private String RuleID;
    @JsonProperty("RuleVersionID")
    private String RuleVersionID;
    @Nullable
    @JsonProperty("Param")
    private EvaExecParam Param;

    public EvaTaskRuleParams() {
    }

    public EvaTaskRuleParams(String ruleID,String ruleVersionID, @Nullable EvaExecParam param) {
        RuleID = ruleID;
        RuleVersionID = ruleVersionID;
        Param = param;
    }

    public String getRuleID() {
        return RuleID;
    }

    public void setRuleID(String ruleID) {
        RuleID = ruleID;
    }

    public String getRuleVersionID() {
        return RuleVersionID;
    }

    public void setRuleVersionID(String ruleVersionID) {
        RuleVersionID = ruleVersionID;
    }

    @Nullable
    public EvaExecParam getParam() {
        return Param;
    }

    public void setParam(@Nullable EvaExecParam param) {
        Param = param;
    }
}
