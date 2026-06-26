package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1UpdateSkillActionRequest {
    @JsonProperty("ID") public String id;
    @JsonProperty("SkillID") public String skillId;
    @JsonProperty("Version") public String version;
    @JsonProperty("WorkspaceID") public String workspaceId;
    @JsonProperty("Name") public String name;
    @JsonProperty("Description") public String description;
    @JsonProperty("Source") public String source;
    @JsonProperty("Enabled") public Boolean enabled;
    @JsonProperty("NewVersion") public String newVersion;
    @JsonProperty("CredentialConfig") public V1SkillCredentialInput credentialConfig;
    @JsonProperty("SlugID") public String slugId;
    @JsonProperty("BlobID") public String blobId;
}
