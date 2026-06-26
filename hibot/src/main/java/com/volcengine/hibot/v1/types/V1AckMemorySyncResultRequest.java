package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1AckMemorySyncResultRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("AgentID") public String agentId;
    @JsonProperty("ManifestRevision") public Long manifestRevision;
    @JsonProperty("Success") public Boolean success;
    @JsonProperty("Message") public String message;
}
