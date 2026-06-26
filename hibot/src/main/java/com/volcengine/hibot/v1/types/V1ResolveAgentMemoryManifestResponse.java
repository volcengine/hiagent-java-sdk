package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ResolveAgentMemoryManifestResponse {
    @JsonProperty("AgentID") public String agentId;
    @JsonProperty("ManifestRevision") public Long manifestRevision;
    @JsonProperty("Stores") public List<V1AgentMemoryManifestStore> stores;
}
