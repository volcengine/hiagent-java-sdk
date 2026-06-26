package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1SkillResponse {
    @JsonProperty("ID") public String id;
    @JsonProperty("SkillID") public String skillId;
    @JsonProperty("Name") public String name;
    @JsonProperty("Description") public String description;
    @JsonProperty("Source") public String source;
    @JsonProperty("Version") public String version;
    @JsonProperty("ArtifactID") public String artifactId;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("UpdatedAt") public String updatedAt;
    @JsonProperty("Enabled") public Boolean enabled;
    @JsonProperty("CredentialProviderID") public String credentialProviderId;
    @JsonProperty("SlugID") public String slugId;
    @JsonProperty("Credential") public V1CredentialMaskedResponse credential;
}
