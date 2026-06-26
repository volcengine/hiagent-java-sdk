package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1CreateSkillRequest {
    @JsonProperty("SkillID") public String skillId;
    @JsonProperty("Name") public String name;
    @JsonProperty("Description") public String description;
    @JsonProperty("Source") public String source;
    @JsonProperty("BlobID") public String blobId;
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("Enabled") public Boolean enabled;
    @JsonProperty("Version") public String version;
    @JsonProperty("CredentialConfig") public V1SkillCredentialInput credentialConfig;
    @JsonProperty("SlugID") public String slugId;
}
