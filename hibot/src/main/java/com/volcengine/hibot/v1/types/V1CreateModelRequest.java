package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1CreateModelRequest {
    @JsonProperty("ID") public String id;
    @JsonProperty("Name") public String name;
    @JsonProperty("Description") public String description;
    @JsonProperty("Type") public String type;
    @JsonProperty("CredentialSchema") public V1ModelCredentialSchema credentialSchema;
    @JsonProperty("Credential") public Map<String, String> credential;
    @JsonProperty("FeaturesConfig") public List<String> featuresConfig;
    @JsonProperty("Property") public V1ModelProperty property;
    @JsonProperty("Provider") public String provider;
    @JsonProperty("Spec") public String spec;
    @JsonProperty("ModelName") public String modelName;
}
