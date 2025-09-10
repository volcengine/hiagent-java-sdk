package com.volcengine.hiagent.api.model.eva.base;

public class EvaTaskItemRulesetRule {
    private String RuleID;
    private String Name;
    private String Type;
    private EvaRuleConfig Config;

    public EvaTaskItemRulesetRule() {
    }

    public EvaTaskItemRulesetRule(String ruleID, String name, String type, EvaRuleConfig config) {
        RuleID = ruleID;
        Name = name;
        Type = type;
        Config = config;
    }

    public String getRuleID() {
        return RuleID;
    }

    public void setRuleID(String ruleID) {
        RuleID = ruleID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public EvaRuleConfig getConfig() {
        return Config;
    }

    public void setConfig(EvaRuleConfig config) {
        Config = config;
    }
}
