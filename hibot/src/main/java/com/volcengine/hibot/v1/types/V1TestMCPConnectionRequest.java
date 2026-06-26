package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1TestMCPConnectionRequest {
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("Transport") public String transport;
    @JsonProperty("URL") public String url;
    @JsonProperty("Headers") public Map<String, String> headers;
    @JsonProperty("Env") public Map<String, String> env;
    @JsonProperty("Command") public String command;
    @JsonProperty("Args") public List<String> args;
    @JsonProperty("AuthType") public String authType;
    @JsonProperty("CredentialConfig") public V1MCPCredentialInput credentialConfig;
    @JsonProperty("Timeout") public Long timeout;

    @Override
    public String toString() {
        return "V1TestMCPConnectionRequest{" +
                "workspaceId='" + workspaceId + '\'' +
                ", transport='" + transport + '\'' +
                ", url='" + url + '\'' +
                ", headers='***'" +
                ", env='***'" +
                ", command='" + command + '\'' +
                ", args=" + args +
                ", authType='" + authType + '\'' +
                ", credentialConfig=" + credentialConfig +
                ", timeout=" + timeout +
                '}';
    }
}
