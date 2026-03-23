package com.volcengine.hiagent.api.model.base;

public class EvaTaskRulesetItemConfig {
    private String RulesetID;

    public EvaTaskRulesetItemConfig() {
    }

    public EvaTaskRulesetItemConfig(String rulesetID) {
        RulesetID = rulesetID;
    }

    public String getRulesetID() {
        return RulesetID;
    }

    public void setRulesetID(String rulesetID) {
        RulesetID = rulesetID;
    }
}
