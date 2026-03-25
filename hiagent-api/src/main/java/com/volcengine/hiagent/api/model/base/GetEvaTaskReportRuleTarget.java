// Copyright (c) 2024 Bytedance Ltd. and/or its affiliates
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class GetEvaTaskReportRuleTarget {
    private  String TargetID;
    @Nullable
    private  EvaTaskTarget TargetDetail;
    private double AvgScore;
    private double MaxScore;
    private double MinScore;
    private double PCT50Score;
    private double PCT90Score;
    private double PCT99Score;
    private double Percent;
    @Nullable
    private Map<Long,Double> ScoreMap;
    private long Duration;
    private long CostTokens;

    public GetEvaTaskReportRuleTarget() {
    }

    public GetEvaTaskReportRuleTarget(String targetID, @Nullable EvaTaskTarget targetDetail, double avgScore, double maxScore, double minScore, double PCT50Score, double PCT90Score, double PCT99Score, double percent, @Nullable Map<Long, Double> scoreMap, long duration, long costTokens) {
        TargetID = targetID;
        TargetDetail = targetDetail;
        AvgScore = avgScore;
        MaxScore = maxScore;
        MinScore = minScore;
        this.PCT50Score = PCT50Score;
        this.PCT90Score = PCT90Score;
        this.PCT99Score = PCT99Score;
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

    public double getMaxScore() {
        return MaxScore;
    }

    public void setMaxScore(double maxScore) {
        MaxScore = maxScore;
    }

    public double getMinScore() {
        return MinScore;
    }

    public void setMinScore(double minScore) {
        MinScore = minScore;
    }

    public double getPCT50Score() {
        return PCT50Score;
    }

    public void setPCT50Score(double PCT50Score) {
        this.PCT50Score = PCT50Score;
    }

    public double getPCT90Score() {
        return PCT90Score;
    }

    public void setPCT90Score(double PCT90Score) {
        this.PCT90Score = PCT90Score;
    }

    public double getPCT99Score() {
        return PCT99Score;
    }

    public void setPCT99Score(double PCT99Score) {
        this.PCT99Score = PCT99Score;
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
