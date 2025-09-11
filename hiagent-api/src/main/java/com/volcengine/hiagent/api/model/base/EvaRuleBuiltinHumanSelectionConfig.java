package com.volcengine.hiagent.api.model.base;

public class EvaRuleBuiltinHumanSelectionConfig {
    private String Color;
    private String Text;

    public EvaRuleBuiltinHumanSelectionConfig() {
    }

    public EvaRuleBuiltinHumanSelectionConfig(String color, String text) {
        Color = color;
        Text = text;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
