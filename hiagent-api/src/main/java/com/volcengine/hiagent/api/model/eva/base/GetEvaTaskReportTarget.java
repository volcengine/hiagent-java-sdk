package com.volcengine.hiagent.api.model.eva.base;

import org.jetbrains.annotations.Nullable;

public class GetEvaTaskReportTarget {
    private String TargetID;
    @Nullable
    private EvaTaskTarget TargetDetail;
    private long Duration;
    private long CostTokens;
    private double AvgCostTokens;
    private double AvgDuration;
    private double AvgTTFT;

    public GetEvaTaskReportTarget() {
    }

    public GetEvaTaskReportTarget(String targetID, @Nullable EvaTaskTarget targetDetail, long duration, long costTokens, double avgCostTokens, double avgDuration, double avgTTFT) {
        TargetID = targetID;
        TargetDetail = targetDetail;
        Duration = duration;
        CostTokens = costTokens;
        AvgCostTokens = avgCostTokens;
        AvgDuration = avgDuration;
        AvgTTFT = avgTTFT;
    }

    public String getTargetID() {
        return TargetID;
    }

    public void setTargetID(String targetID) {
        TargetID = targetID;
    }

    @Nullable
    public EvaTaskTarget getTargetDetail() {
        return TargetDetail;
    }

    public void setTargetDetail(@Nullable EvaTaskTarget targetDetail) {
        TargetDetail = targetDetail;
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

    public double getAvgCostTokens() {
        return AvgCostTokens;
    }

    public void setAvgCostTokens(double avgCostTokens) {
        AvgCostTokens = avgCostTokens;
    }

    public double getAvgDuration() {
        return AvgDuration;
    }

    public void setAvgDuration(double avgDuration) {
        AvgDuration = avgDuration;
    }

    public double getAvgTTFT() {
        return AvgTTFT;
    }

    public void setAvgTTFT(double avgTTFT) {
        AvgTTFT = avgTTFT;
    }
}
