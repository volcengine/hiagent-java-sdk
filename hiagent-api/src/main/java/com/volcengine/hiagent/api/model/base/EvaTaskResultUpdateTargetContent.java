package com.volcengine.hiagent.api.model.base;

import java.util.List;

public class EvaTaskResultUpdateTargetContent {
    private String TargetType;
    private String TargetID;
    private List<EvaTaskResultTargetContentPair> Results;

    public EvaTaskResultUpdateTargetContent() {
    }

    public EvaTaskResultUpdateTargetContent(String targetType, String targetID, List<EvaTaskResultTargetContentPair> results) {
        TargetType = targetType;
        TargetID = targetID;
        Results = results;
    }

    public String getTargetType() {
        return TargetType;
    }

    public void setTargetType(String targetType) {
        TargetType = targetType;
    }

    public String getTargetID() {
        return TargetID;
    }

    public void setTargetID(String targetID) {
        TargetID = targetID;
    }

    public List<EvaTaskResultTargetContentPair> getResults() {
        return Results;
    }

    public void setResults(List<EvaTaskResultTargetContentPair> results) {
        Results = results;
    }
}
