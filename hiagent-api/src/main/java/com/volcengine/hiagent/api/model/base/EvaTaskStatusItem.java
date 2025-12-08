package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

public class EvaTaskStatusItem {
    @Nullable
    private  String StartedAt;
    @Nullable
    private  String CompletedAt;
    private long Duration;
    private long CostTokens;
    @Nullable
    private EvaTaskItemProgress Progress;
    private  EvaTaskStatus Status;
    @Nullable
    private  String StatusMessage;

    public EvaTaskStatusItem() {
    }

    public EvaTaskStatusItem(@Nullable String startedAt, @Nullable String completedAt, long duration, long costTokens, @Nullable EvaTaskItemProgress progress, EvaTaskStatus status, @Nullable String statusMessage) {
        StartedAt = startedAt;
        CompletedAt = completedAt;
        Duration = duration;
        CostTokens = costTokens;
        Progress = progress;
        Status = status;
        StatusMessage = statusMessage;
    }

    @Nullable
    public String getStartedAt() {
        return StartedAt;
    }

    public void setStartedAt(@Nullable String startedAt) {
        StartedAt = startedAt;
    }

    @Nullable
    public String getCompletedAt() {
        return CompletedAt;
    }

    public void setCompletedAt(@Nullable String completedAt) {
        CompletedAt = completedAt;
    }

    public long getDuration() {
        return Duration;
    }

    public void setDuration(long duration) {
        Duration = duration;
    }

    public long getCostTokens() {
        return CostTokens;
    }

    public void setCostTokens(long costTokens) {
        CostTokens = costTokens;
    }

    @Nullable
    public EvaTaskItemProgress getProgress() {
        return Progress;
    }

    public void setProgress(@Nullable EvaTaskItemProgress progress) {
        Progress = progress;
    }

    public EvaTaskStatus getStatus() {
        return Status;
    }

    public void setStatus(EvaTaskStatus status) {
        Status = status;
    }

    @Nullable
    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(@Nullable String statusMessage) {
        StatusMessage = statusMessage;
    }
}
