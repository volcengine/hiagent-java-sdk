package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1SkillCredentialInput {
    @JsonProperty("Name") public String name;
    @JsonProperty("Description") public String description;
    @JsonProperty("Source") public String source;
    @JsonProperty("ProviderType") public String providerType;
    @JsonProperty("Config") public V1RawJSON config;
    @JsonProperty("Secrets") public List<V1CredentialSecretInput> secrets;

    @Override
    public String toString() {
        return "V1SkillCredentialInput{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", source='" + source + '\'' +
                ", providerType='" + providerType + '\'' +
                ", config=" + config +
                ", secrets=" + secrets +
                '}';
    }
}
