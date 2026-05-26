package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1Model {
    @JsonProperty("ID") public String id;
    @JsonProperty("Name") public String name;
    @JsonProperty("Type") public String type;
    @JsonProperty("Provider") public String provider;
    @JsonProperty("Spec") public String spec;
    @JsonProperty("ModelName") public String modelName;
    @JsonProperty("Description") public String description;
    @JsonProperty("CreateUserName") public String createUserName;
    @JsonProperty("CreateTime") public String createTime;
    @JsonProperty("DeleteAt") public String deleteAt;
    @JsonProperty("TenantId") public String tenantId;
    @JsonProperty("UpdateUserName") public String updateUserName;
    @JsonProperty("UpdateTime") public String updateTime;
    @JsonProperty("Status") public String status;
    @JsonProperty("FeaturesConfig") public JsonNode featuresConfig;
    @JsonProperty("Property") public JsonNode property;
    @JsonProperty("CredentialSchema") public JsonNode credentialSchema;
    @JsonProperty("Credential") public Map<String, String> credential;
}
