package com.volcengine.hiagent.api.model.eva.base;

import org.jetbrains.annotations.Nullable;

public class EvaTaskRuleParams {
    private String RuleID;
    @Nullable
    private EvaExecParam Param;

    public EvaTaskRuleParams() {
    }

    public EvaTaskRuleParams(String ruleID, @Nullable EvaExecParam param) {
        RuleID = ruleID;
        Param = param;
    }

    public String getRuleID() {
        return RuleID;
    }

    public void setRuleID(String ruleID) {
        RuleID = ruleID;
    }

    @Nullable
    public EvaExecParam getParam() {
        return Param;
    }

    public void setParam(@Nullable EvaExecParam param) {
        Param = param;
    }
}
