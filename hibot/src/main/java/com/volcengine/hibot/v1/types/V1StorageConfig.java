package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1StorageConfig {
    @JsonProperty("AllowedPaths") public List<String> allowedPaths;
    @JsonProperty("ReadOnly") public Boolean readOnly;
}
