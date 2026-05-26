package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ModelProvider {
    @JsonProperty("ID") public String id;
    @JsonProperty("Type") public String type;
    @JsonProperty("Provider") public String provider;
    @JsonProperty("ModelName") public String modelName;
    @JsonProperty("FeaturesConfig") public JsonNode featuresConfig;
    @JsonProperty("Property") public JsonNode property;
    @JsonProperty("CredentialSchema") public JsonNode credentialSchema;
    @JsonProperty("CreateUserName") public String createUserName;
    @JsonProperty("CreateTime") public String createTime;
    @JsonProperty("UpdateUserName") public String updateUserName;
    @JsonProperty("UpdateTime") public String updateTime;
    @JsonProperty("TenantId") public String tenantId;
}
