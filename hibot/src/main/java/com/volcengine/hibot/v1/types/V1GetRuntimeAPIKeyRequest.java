package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1GetRuntimeAPIKeyRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("AgentID") public String agentId;
    @JsonProperty("ID") public String id;
    @JsonProperty("UserInfo") public V1RuntimeAPIKeyUserInfo userInfo;
}
