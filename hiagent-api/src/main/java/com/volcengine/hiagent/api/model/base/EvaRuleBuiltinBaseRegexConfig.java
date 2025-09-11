package com.volcengine.hiagent.api.model.base;

public class EvaRuleBuiltinBaseRegexConfig {
    private String Regex;

    public EvaRuleBuiltinBaseRegexConfig() {
    }

    public EvaRuleBuiltinBaseRegexConfig(String regex) {
        Regex = regex;
    }

    public String getRegex() {
        return Regex;
    }

    public void setRegex(String regex) {
        Regex = regex;
    }
}
