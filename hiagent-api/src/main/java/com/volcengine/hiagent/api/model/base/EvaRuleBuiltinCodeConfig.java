package com.volcengine.hiagent.api.model.base;

public class EvaRuleBuiltinCodeConfig {
    private String Type;
    private String Code;

    public EvaRuleBuiltinCodeConfig() {
    }

    public EvaRuleBuiltinCodeConfig(String type, String code) {
        Type = type;
        Code = code;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
