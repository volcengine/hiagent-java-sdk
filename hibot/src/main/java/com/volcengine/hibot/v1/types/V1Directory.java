package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1Directory {
    @JsonProperty("ID") public String id;
    @JsonProperty("Name") public String name;
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("UpdatedAt") public String updatedAt;
    @JsonProperty("CreatedBy") public String createdBy;
    @JsonProperty("UpdatedBy") public String updatedBy;
    @JsonProperty("ResourceCount") public Long resourceCount;
}
