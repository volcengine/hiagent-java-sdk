package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EvaTaskRulesConfig {
    private EvaTaskRuleSource Source;
    @Nullable
    private List<EvaTaskRuleItemConfig> Rules;
    @Nullable
    private EvaTaskRulesetItemConfig Ruleset;

    public EvaTaskRulesConfig() {
    }

    public EvaTaskRulesConfig(EvaTaskRuleSource source, @Nullable List<EvaTaskRuleItemConfig> rules, @Nullable EvaTaskRulesetItemConfig ruleset) {
        Source = source;
        Rules = rules;
        Ruleset = ruleset;
    }

    public EvaTaskRuleSource getSource() {
        return Source;
    }

    public void setSource(EvaTaskRuleSource source) {
        Source = source;
    }

    @Nullable
    public List<EvaTaskRuleItemConfig> getRules() {
        return Rules;
    }

    public void setRules(@Nullable List<EvaTaskRuleItemConfig> rules) {
        Rules = rules;
    }

    @Nullable
    public EvaTaskRulesetItemConfig getRuleset() {
        return Ruleset;
    }

    public void setRuleset(@Nullable EvaTaskRulesetItemConfig ruleset) {
        Ruleset = ruleset;
    }
}
