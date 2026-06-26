package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1AgentMemoryManifestFile {
    @JsonProperty("Path") public String path;
    @JsonProperty("ArtifactID") public String artifactId;
    @JsonProperty("ContentSha256") public String contentSha256;
    @JsonProperty("Revision") public Long revision;
    @JsonProperty("SizeBytes") public Long sizeBytes;
}
