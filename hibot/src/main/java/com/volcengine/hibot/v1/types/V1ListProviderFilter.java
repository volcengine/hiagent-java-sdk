package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ListProviderFilter {
    @JsonProperty("Provider") public String provider;
    @JsonProperty("Type") public String type;
    @JsonProperty("ModelName") public String modelName;
    @JsonProperty("Features") public List<String> features;
}
