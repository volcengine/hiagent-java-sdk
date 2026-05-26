package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1Message {
    @JsonProperty("ID") public String id;
    @JsonProperty("SessionID") public String sessionId;
    @JsonProperty("RunID") public String runId;
    @JsonProperty("Role") public String role;
    @JsonProperty("Content") public String content;
    @JsonProperty("Visibility") public String visibility;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("Files") public List<V1MessageFile> files;
}
