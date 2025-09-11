package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EvaTaskItemRuleset {
    private  String RulesetID;
    private  String RulesetName;
    private boolean IsDeleted;
    @Nullable
    private List<EvaTaskItemRulesetRule> Rules;

    public EvaTaskItemRuleset() {
    }

    public EvaTaskItemRuleset(String rulesetID, String rulesetName, boolean isDeleted, @Nullable List<EvaTaskItemRulesetRule> rules) {
        RulesetID = rulesetID;
        RulesetName = rulesetName;
        IsDeleted = isDeleted;
        Rules = rules;
    }

    public String getRulesetID() {
        return RulesetID;
    }

    public void setRulesetID(String rulesetID) {
        RulesetID = rulesetID;
    }

    public String getRulesetName() {
        return RulesetName;
    }

    public void setRulesetName(String rulesetName) {
        RulesetName = rulesetName;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    @Nullable
    public List<EvaTaskItemRulesetRule> getRules() {
        return Rules;
    }

    public void setRules(@Nullable List<EvaTaskItemRulesetRule> rules) {
        Rules = rules;
    }
}
