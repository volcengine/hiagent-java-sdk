package com.volcengine.hiagent.api.model.eva.base;

public class EvaTaskItemTarget {
    private EvaTaskTarget Target;
    private boolean IsDeleted;

    public EvaTaskItemTarget() {
    }

    public EvaTaskItemTarget(EvaTaskTarget target, boolean isDeleted) {
        Target = target;
        IsDeleted = isDeleted;
    }

    public EvaTaskTarget getTarget() {
        return Target;
    }

    public void setTarget(EvaTaskTarget target) {
        Target = target;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }
}
