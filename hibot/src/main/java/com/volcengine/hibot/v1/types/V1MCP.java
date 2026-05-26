package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1MCP {
    @JsonProperty("ID") public String id;
    @JsonProperty("Name") public String name;
    @JsonProperty("Description") public String description;
    @JsonProperty("Transport") public String transport;
    /** Note: server-side field is named URL while SDK exposes as "endpoint". */
    @JsonProperty("URL") public String endpoint;
    @JsonProperty("Headers") public Map<String, String> headers;
    @JsonProperty("Env") public Map<String, String> env;
    @JsonProperty("Command") public String command;
    @JsonProperty("Args") public List<String> args;
    @JsonProperty("AuthType") public String authType;
    @JsonProperty("CredentialProviderID") public String credentialProviderId;
    @JsonProperty("ToolAllowlist") public List<String> toolAllowlist;
    @JsonProperty("ToolDenylist") public List<String> toolDenylist;
    @JsonProperty("ToolPrefix") public String toolPrefix;
    @JsonProperty("Timeout") public Long timeout;
    @JsonProperty("Status") public String status;
    @JsonProperty("Source") public String source;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("UpdatedAt") public String updatedAt;
}
