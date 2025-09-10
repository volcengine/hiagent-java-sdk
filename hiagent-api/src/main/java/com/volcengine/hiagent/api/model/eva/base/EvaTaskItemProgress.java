package com.volcengine.hiagent.api.model.eva.base;

public class EvaTaskItemProgress {
    private long Total;
    private long Completed;

    public EvaTaskItemProgress() {
    }

    public EvaTaskItemProgress(long total, long completed) {
        Total = total;
        Completed = completed;
    }

    public long getTotal() {
        return Total;
    }

    public void setTotal(long total) {
        Total = total;
    }

    public long getCompleted() {
        return Completed;
    }

    public void setCompleted(long completed) {
        Completed = completed;
    }
}
