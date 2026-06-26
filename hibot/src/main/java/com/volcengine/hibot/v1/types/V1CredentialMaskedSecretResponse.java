package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1CredentialMaskedSecretResponse {
    @JsonProperty("SecretID") public String secretId;
    @JsonProperty("ProviderID") public String providerId;
    @JsonProperty("KeyName") public String keyName;
    @JsonProperty("Description") public String description;
    @JsonProperty("SecretType") public String secretType;
    @JsonProperty("HasSecretValue") public Boolean hasSecretValue;
}
