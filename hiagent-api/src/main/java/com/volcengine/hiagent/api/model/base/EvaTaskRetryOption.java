package com.volcengine.hiagent.api.model.base;

public class EvaTaskRetryOption {
    public boolean Inference;
    public boolean ResultEvaluate;
    public boolean SpanEvaluate;

    public EvaTaskRetryOption() {
    }

    public EvaTaskRetryOption(boolean resultEvaluate) {
        ResultEvaluate = resultEvaluate;
    }

    public boolean isResultEvaluate() {
        return ResultEvaluate;
    }

    public void setResultEvaluate(boolean resultEvaluate) {
        ResultEvaluate = resultEvaluate;
    }

    public boolean isInference() {
        return Inference;
    }

    public boolean isSpanEvaluate() {
        return SpanEvaluate;
    }
}
