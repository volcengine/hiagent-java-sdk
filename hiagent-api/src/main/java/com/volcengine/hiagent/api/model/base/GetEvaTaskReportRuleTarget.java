package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class GetEvaTaskReportRuleTarget {
    private  String TargetID;
    @Nullable
    private  EvaTaskTarget TargetDetail;
    private double AvgScore;
    private double Percent;
    @Nullable
    private Map<Long,Double> ScoreMap;
    private long Duration;
    private long CostTokens;

    public GetEvaTaskReportRuleTarget() {
    }

    public GetEvaTaskReportRuleTarget(String targetID, @Nullable EvaTaskTarget targetDetail, double avgScore, double percent, @Nullable Map<Long, Double> scoreMap, long duration, long costTokens) {
        TargetID = targetID;
        TargetDetail = targetDetail;
        AvgScore = avgScore;
        Percent = percent;
        ScoreMap = scoreMap;
        Duration = duration;
        CostTokens = costTokens;
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

    public double getAvgScore() {
        return AvgScore;
    }

    public void setAvgScore(double avgScore) {
        AvgScore = avgScore;
    }

    public double getPercent() {
        return Percent;
    }

    public void setPercent(double percent) {
        Percent = percent;
    }

    @Nullable
    public Map<Long, Double> getScoreMap() {
        return ScoreMap;
    }

    public void setScoreMap(@Nullable Map<Long, Double> scoreMap) {
        ScoreMap = scoreMap;
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
}
