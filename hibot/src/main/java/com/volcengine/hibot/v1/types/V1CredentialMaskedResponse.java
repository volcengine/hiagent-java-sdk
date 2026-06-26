package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1CredentialMaskedResponse {
    @JsonProperty("ProviderID") public String providerId;
    @JsonProperty("OwnerType") public String ownerType;
    @JsonProperty("OwnerID") public String ownerId;
    @JsonProperty("Name") public String name;
    @JsonProperty("Description") public String description;
    @JsonProperty("Source") public String source;
    @JsonProperty("ProviderType") public String providerType;
    @JsonProperty("Config") public V1RawJSON config;
    @JsonProperty("Secrets") public List<V1CredentialMaskedSecretResponse> secrets;
    @JsonProperty("CreatedAt") public String createdAt;
    @JsonProperty("UpdatedAt") public String updatedAt;
}
