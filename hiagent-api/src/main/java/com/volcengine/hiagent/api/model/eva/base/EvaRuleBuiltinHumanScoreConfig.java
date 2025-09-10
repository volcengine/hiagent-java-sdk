package com.volcengine.hiagent.api.model.eva.base;

public class EvaRuleBuiltinHumanScoreConfig {
    private long Max;
    private long Min;

    public EvaRuleBuiltinHumanScoreConfig() {
    }

    public EvaRuleBuiltinHumanScoreConfig(long max, long min) {
        Max = max;
        Min = min;
    }

    public long getMax() {
        return Max;
    }

    public void setMax(long max) {
        Max = max;
    }

    public long getMin() {
        return Min;
    }

    public void setMin(long min) {
        Min = min;
    }
}
