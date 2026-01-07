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
package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.ChatEvent;

import java.util.List;

public class KnowledgeDocumentMetadata extends ChatEvent {

    @SerializedName("score")
    private Float score;

    @SerializedName("dataset_id")
    private String datasetId;

    @SerializedName("segment_id")
    private String segmentId;

    @SerializedName("document_id")
    private String documentId;

    @SerializedName("dataset_name")
    private String datasetName;

    @SerializedName("document_url")
    private String documentUrl;

    @SerializedName("document_name")
    private String documentName;

    @SerializedName("document_type")
    private Integer documentType;

    @SerializedName("document_obs_url")
    private String documentObsUrl;

    @SerializedName("document_file_type")
    private Integer documentFileType;

    @SerializedName("orientations")
    private List<Orientation> orientations;

    @SerializedName("indexes")
    private List<Index> indexes;


    @SerializedName("video_audio_info")
    private VideoAudioInfo videoAudioInfo;

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Integer getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Integer documentType) {
        this.documentType = documentType;
    }

    public String getDocumentObsUrl() {
        return documentObsUrl;
    }

    public void setDocumentObsUrl(String documentObsUrl) {
        this.documentObsUrl = documentObsUrl;
    }

    public Integer getDocumentFileType() {
        return documentFileType;
    }

    public void setDocumentFileType(Integer documentFileType) {
        this.documentFileType = documentFileType;
    }

    public List<Orientation> getOrientations() {
        return orientations;
    }

    public void setOrientations(List<Orientation> orientations) {
        this.orientations = orientations;
    }

    public List<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }

    public VideoAudioInfo getVideoAudioInfo() {
        return videoAudioInfo;
    }

    public void setVideoAudioInfo(VideoAudioInfo videoAudioInfo) {
        this.videoAudioInfo = videoAudioInfo;
    }

    public static class Orientation {
        @SerializedName("page_number")
        private Integer pageNumber;

        @SerializedName("rects")
        private List<Float> rects;

        public Integer getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(Integer pageNumber) {
            this.pageNumber = pageNumber;
        }

        public List<Float> getRects() {
            return rects;
        }

        public void setRects(List<Float> rects) {
            this.rects = rects;
        }
    }

    public static class Index {
        @SerializedName("content")
        private String content;

        @SerializedName("score")
        private Float score;

        @SerializedName("is_multimodal")
        private Boolean isMultimodal;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Float getScore() {
            return score;
        }

        public void setScore(Float score) {
            this.score = score;
        }

        public Boolean getMultimodal() {
            return isMultimodal;
        }

        public void setMultimodal(Boolean multimodal) {
            isMultimodal = multimodal;
        }
    }

    public static class VideoAudioInfo {
        @SerializedName("duration")
        private Integer duration;

        @SerializedName("raw_file_url")
        private String rawFileUrl;

        @SerializedName("asr_file_url")
        private String asrFileUrl;

        @SerializedName("frame_urls")
        private List<String> frameUrls;

        @SerializedName("first_last_frame_urls")
        private List<String> firstLastFrameUrls;

        @SerializedName("speakers")
        private List<String> speakers;

        @SerializedName("asr_details")
        private List<AsrDetail> asrDetails;

        @SerializedName("start_time")
        private Float startTime;

        @SerializedName("end_time")
        private Float endTime;

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public String getRawFileUrl() {
            return rawFileUrl;
        }

        public void setRawFileUrl(String rawFileUrl) {
            this.rawFileUrl = rawFileUrl;
        }

        public String getAsrFileUrl() {
            return asrFileUrl;
        }

        public void setAsrFileUrl(String asrFileUrl) {
            this.asrFileUrl = asrFileUrl;
        }

        public List<String> getFrameUrls() {
            return frameUrls;
        }

        public void setFrameUrls(List<String> frameUrls) {
            this.frameUrls = frameUrls;
        }

        public List<String> getFirstLastFrameUrls() {
            return firstLastFrameUrls;
        }

        public void setFirstLastFrameUrls(List<String> firstLastFrameUrls) {
            this.firstLastFrameUrls = firstLastFrameUrls;
        }

        public List<String> getSpeakers() {
            return speakers;
        }

        public void setSpeakers(List<String> speakers) {
            this.speakers = speakers;
        }

        public List<AsrDetail> getAsrDetails() {
            return asrDetails;
        }

        public void setAsrDetails(List<AsrDetail> asrDetails) {
            this.asrDetails = asrDetails;
        }

        public Float getStartTime() {
            return startTime;
        }

        public void setStartTime(Float startTime) {
            this.startTime = startTime;
        }

        public Float getEndTime() {
            return endTime;
        }

        public void setEndTime(Float endTime) {
            this.endTime = endTime;
        }
    }

    public static class AsrDetail {
        @SerializedName("text")
        private String text;

        @SerializedName("start")
        private Float start;

        @SerializedName("end")
        private Float end;

        @SerializedName("speakers")
        private List<String> speakers;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Float getStart() {
            return start;
        }

        public void setStart(Float start) {
            this.start = start;
        }

        public Float getEnd() {
            return end;
        }

        public void setEnd(Float end) {
            this.end = end;
        }

        public List<String> getSpeakers() {
            return speakers;
        }

        public void setSpeakers(List<String> speakers) {
            this.speakers = speakers;
        }
    }

}
