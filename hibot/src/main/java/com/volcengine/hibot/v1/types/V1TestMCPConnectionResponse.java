package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1TestMCPConnectionResponse {
    @JsonProperty("Success") public Boolean success;
    @JsonProperty("Error") public String error;
    @JsonProperty("ToolCount") public Integer toolCount;
    @JsonProperty("Tools") public List<V1MCPToolResponse> tools;
}
