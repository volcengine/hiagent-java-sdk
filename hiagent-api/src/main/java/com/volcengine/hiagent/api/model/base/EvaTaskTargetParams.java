package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EvaTaskTargetParams {
    @Nullable
    private List<EvaExecParam> Params;
    @Nullable
    private List<EvaTaskRuleParams> RuleParams;

    public EvaTaskTargetParams() {
    }

    public EvaTaskTargetParams(@Nullable List<EvaExecParam> params, @Nullable List<EvaTaskRuleParams> ruleParams) {
        Params = params;
        RuleParams = ruleParams;
    }

    @Nullable
    public List<EvaExecParam> getParams() {
        return Params;
    }

    public void setParams(@Nullable List<EvaExecParam> params) {
        Params = params;
    }

    @Nullable
    public List<EvaTaskRuleParams> getRuleParams() {
        return RuleParams;
    }

    public void setRuleParams(@Nullable List<EvaTaskRuleParams> ruleParams) {
        RuleParams = ruleParams;
    }
}