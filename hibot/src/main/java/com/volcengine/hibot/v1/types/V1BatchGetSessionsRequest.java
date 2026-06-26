package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1BatchGetSessionsRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("SessionIDs") public List<String> sessionIDs;
}
