package com.volcengine.hiagent.api.model.eva.base;

import java.util.List;

public class GetEvaTaskReportRule {
    private String RuleID;
    private List<GetEvaTaskReportRuleTarget> Targets;

    public GetEvaTaskReportRule() {
    }

    public GetEvaTaskReportRule(String ruleID, List<GetEvaTaskReportRuleTarget> targets) {
        RuleID = ruleID;
        Targets = targets;
    }

    public String getRuleID() {
        return RuleID;
    }

    public void setRuleID(String ruleID) {
        RuleID = ruleID;
    }

    public List<GetEvaTaskReportRuleTarget> getTargets() {
        return Targets;
    }

    public void setTargets(List<GetEvaTaskReportRuleTarget> targets) {
        Targets = targets;
    }
}
