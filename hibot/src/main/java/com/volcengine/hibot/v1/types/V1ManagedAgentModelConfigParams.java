package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class V1ManagedAgentModelConfigParams {
    @JsonProperty("ID") public String id;

    public V1ManagedAgentModelConfigParams() {}

    public V1ManagedAgentModelConfigParams(String id) {
        this.id = id;
    }
}
