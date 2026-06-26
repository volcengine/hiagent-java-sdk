package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ParseSkillRequest {
    @JsonProperty("BlobID") public String blobId;
    @JsonProperty("WorkspaceID") public String workspaceId;
}
