package com.volcengine.hiagent.api.model.base;

public class EvaTaskRuleItemConfig {
    private String RuleID;
    private String RuleVersionID;

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

    public EvaTaskRuleItemConfig(String ruleID, String ruleVersionID) {
        RuleID = ruleID;
        RuleVersionID = ruleVersionID;
    }

    public EvaTaskRuleItemConfig() {
    }
}
