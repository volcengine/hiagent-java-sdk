package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1RuntimeAPIKeyResponse {
    @JsonProperty("ID") public String id;
    @JsonProperty("AgentID") public String agentId;
    @JsonProperty("KeyMask") public String keyMask;
    @JsonProperty("Description") public String description;
    @JsonProperty("ExpiresAt") public String expiresAt;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("UpdatedAt") public String updatedAt;
}
