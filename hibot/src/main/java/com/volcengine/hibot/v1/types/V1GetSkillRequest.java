package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1GetSkillRequest {
    @JsonProperty("ID") public String id;
    @JsonProperty("SkillID") public String skillId;
    @JsonProperty("Version") public String version;
    @JsonProperty("WorkspaceID") public String workspaceId;
}
