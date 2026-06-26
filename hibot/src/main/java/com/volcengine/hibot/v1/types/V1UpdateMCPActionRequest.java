package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1UpdateMCPActionRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("ID") public String id;
    @JsonProperty("Name") public String name;
    @JsonProperty("Description") public String description;
    @JsonProperty("Transport") public String transport;
    @JsonProperty("URL") public String url;
    @JsonProperty("Headers") public Map<String, String> headers;
    @JsonProperty("Env") public Map<String, String> env;
    @JsonProperty("Command") public String command;
    @JsonProperty("Args") public List<String> args;
    @JsonProperty("AuthType") public String authType;
    @JsonProperty("CredentialConfig") public V1MCPCredentialInput credentialConfig;
    @JsonProperty("ToolAllowlist") public List<String> toolAllowlist;
    @JsonProperty("ToolDenylist") public List<String> toolDenylist;
    @JsonProperty("ToolPrefix") public String toolPrefix;
    @JsonProperty("Timeout") public Long timeout;
    @JsonProperty("Status") public String status;
    @JsonProperty("Source") public String source;

    @Override
    public String toString() {
        return "V1UpdateMCPActionRequest{" +
                "workspaceId='" + workspaceId + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", transport='" + transport + '\'' +
                ", url='" + url + '\'' +
                ", headers='***'" +
                ", env='***'" +
                ", command='" + command + '\'' +
                ", args=" + args +
                ", authType='" + authType + '\'' +
                ", credentialConfig=" + credentialConfig +
                ", toolAllowlist=" + toolAllowlist +
                ", toolDenylist=" + toolDenylist +
                ", toolPrefix='" + toolPrefix + '\'' +
                ", timeout=" + timeout +
                ", status='" + status + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
