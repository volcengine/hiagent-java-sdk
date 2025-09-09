package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.model.AbstractResponse;

import java.util.List;

public class QueryResponse extends AbstractResponse {
    @SerializedName("Results")
    private List<Result> results = null;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public static class Result {
        @SerializedName("DatasetID")
        private String datasetID = null;

        @SerializedName("DatasetName")
        private String datasetName = null;

        @SerializedName("DocumentID")
        private String documentID = null;

        @SerializedName("DocumentName")
        private String documentName = null;

        @SerializedName("SegmentID")
        private String segmentID = null;

        @SerializedName("Content")
        private String content = null;

        @SerializedName("Score")
        private Double score = null;

        public String getDatasetID() {
            return datasetID;
        }

        public String getDatasetName() {
            return datasetName;
        }

        public String getDocumentID() {
            return documentID;
        }

        public String getDocumentName() {
            return documentName;
        }

        public String getSegmentID() {
            return segmentID;
        }

        public String getContent() {
            return content;
        }

        public Double getScore() {
            return score;
        }

        public void setDatasetID(String datasetID) {
            this.datasetID = datasetID;
        }

        public void setDatasetName(String datasetName) {
            this.datasetName = datasetName;
        }

        public void setDocumentID(String documentID) {
            this.documentID = documentID;
        }

        public void setDocumentName(String documentName) {
            this.documentName = documentName;
        }

        public void setSegmentID(String segmentID) {
            this.segmentID = segmentID;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setScore(Double score) {
            this.score = score;
        }
    }
}
