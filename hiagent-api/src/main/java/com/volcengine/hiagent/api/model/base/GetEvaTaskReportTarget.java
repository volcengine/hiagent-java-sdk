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
