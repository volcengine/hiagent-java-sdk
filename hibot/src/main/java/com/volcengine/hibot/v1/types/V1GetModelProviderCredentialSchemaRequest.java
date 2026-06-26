package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1GetModelProviderCredentialSchemaRequest {
    @JsonProperty("Provider") public String provider;
    @JsonProperty("Spec") public String spec;
    @JsonProperty("Type") public String type;
    @JsonProperty("Features") public List<String> features;
}
