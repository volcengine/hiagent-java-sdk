package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1MCPResponse {
    @JsonProperty("ID") public String id;
    @JsonProperty("Name") public String name;
    @JsonProperty("Description") public String description;
    @JsonProperty("Transport") public String transport;
    @JsonProperty("URL") public String url;
    @JsonProperty("Headers") public Map<String, String> headers = new LinkedHashMap<>();
    @JsonProperty("Env") public Map<String, String> env = new LinkedHashMap<>();
    @JsonProperty("Command") public String command;
    @JsonProperty("Args") public List<String> args = new ArrayList<>();
    @JsonProperty("AuthType") public String authType;
    @JsonProperty("CredentialProviderID") public String credentialProviderId;
    @JsonProperty("ToolAllowlist") public List<String> toolAllowlist = new ArrayList<>();
    @JsonProperty("ToolDenylist") public List<String> toolDenylist = new ArrayList<>();
    @JsonProperty("ToolPrefix") public String toolPrefix;
    @JsonProperty("Timeout") public Long timeout;
    @JsonProperty("Status") public String status;
    @JsonProperty("Source") public String source;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("UpdatedAt") public String updatedAt;
    @JsonProperty("AgentIDs") public List<String> agentIDs = new ArrayList<>();
    @JsonProperty("Credential") public V1CredentialMaskedResponse credential;

    @Override
    public String toString() {
        return "V1MCPResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", transport='" + transport + '\'' +
                ", url='" + url + '\'' +
                ", headers='***'" +
                ", env='***'" +
                ", command='" + command + '\'' +
                ", args=" + args +
                ", authType='" + authType + '\'' +
                ", credentialProviderId='" + credentialProviderId + '\'' +
                ", toolAllowlist=" + toolAllowlist +
                ", toolDenylist=" + toolDenylist +
                ", toolPrefix='" + toolPrefix + '\'' +
                ", timeout=" + timeout +
                ", status='" + status + '\'' +
                ", source='" + source + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", agentIDs=" + agentIDs +
                ", credential=" + credential +
                '}';
    }
}
