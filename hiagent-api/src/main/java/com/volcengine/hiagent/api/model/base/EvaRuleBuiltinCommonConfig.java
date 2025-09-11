package com.volcengine.hiagent.api.model.base;

public class EvaRuleBuiltinCommonConfig {
    private String Content;

    public EvaRuleBuiltinCommonConfig() {
    }

    public EvaRuleBuiltinCommonConfig(String content) {
        Content = content;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
