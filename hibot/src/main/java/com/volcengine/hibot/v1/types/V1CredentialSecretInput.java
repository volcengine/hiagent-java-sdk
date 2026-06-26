package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1CredentialSecretInput {
    @JsonProperty("SecretID") public String secretId;
    @JsonProperty("KeyName") public String keyName;
    @JsonProperty("Description") public String description;
    @JsonProperty("SecretType") public String secretType;
    @JsonProperty("SecretValue") public String secretValue;

    @Override
    public String toString() {
        return "V1CredentialSecretInput{" +
                "secretId='" + mask(secretId) + '\'' +
                ", keyName='" + mask(keyName) + '\'' +
                ", description='" + description + '\'' +
                ", secretType='" + secretType + '\'' +
                ", secretValue='***'" +
                '}';
    }

    private static String mask(String s) {
        if (s == null || s.length() <= 4) return "***";
        return s.substring(0, 2) + "***" + s.substring(s.length() - 2);
    }
}
