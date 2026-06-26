package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ResourceDetail {
    @JsonProperty("ID") public String id;
    @JsonProperty("DirectoryID") public String directoryId;
    @JsonProperty("Name") public String name;
    @JsonProperty("ArtifactID") public String artifactId;
    @JsonProperty("Size") public Long size;
    @JsonProperty("Extension") public String extension;
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("UpdatedAt") public String updatedAt;
    @JsonProperty("CreatedBy") public String createdBy;
    @JsonProperty("UpdatedBy") public String updatedBy;
}
