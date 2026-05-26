package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1SkillVersion {
    @JsonProperty("ID") public String id;
    @JsonProperty("SkillID") public String skillId;
    @JsonProperty("Name") public String name;
    @JsonProperty("Version") public String version;
    @JsonProperty("Description") public String description;
    @JsonProperty("Source") public String source;
    @JsonProperty("ArtifactID") public String artifactId;
    @JsonProperty("Enabled") public Boolean enabled;
    @JsonProperty("CredentialProviderID") public String credentialProviderId;
    @JsonProperty("SlugID") public String slugId;
    @JsonProperty("CreatedAt") public String createdAt;
    /** Constraint is a SDK-side field, never sent to server. */
    @com.fasterxml.jackson.annotation.JsonIgnore public String constraint;
}
