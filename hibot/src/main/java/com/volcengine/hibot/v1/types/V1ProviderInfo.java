package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ProviderInfo {
    @JsonProperty("ID") public String id;
    @JsonProperty("Type") public String type;
    @JsonProperty("Provider") public String provider;
    @JsonProperty("ModelName") public String modelName;
    @JsonProperty("FeaturesConfig") public List<String> featuresConfig;
    @JsonProperty("Property") public V1ModelProperty property;
    @JsonProperty("CredentialSchema") public V1ModelCredentialSchema credentialSchema;
    @JsonProperty("CreateUserName") public String createUserName;
    @JsonProperty("CreateTime") public String createTime;
    @JsonProperty("UpdateUserName") public String updateUserName;
    @JsonProperty("UpdateTime") public String updateTime;
    @JsonProperty("TenantId") public String tenantId;
}
