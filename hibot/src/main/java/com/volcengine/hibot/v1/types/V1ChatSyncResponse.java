package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ChatSyncResponse {
    @JsonProperty("Message") public String message;
    @JsonProperty("TokenCount") public Long tokenCount;
    @JsonProperty("Files") public List<V1MessageFile> files;
}
