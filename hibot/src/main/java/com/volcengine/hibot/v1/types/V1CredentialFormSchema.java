package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1CredentialFormSchema {
    @JsonProperty("Name") public String name;
    @JsonProperty("Label") public String label;
    @JsonProperty("Type") public String type;
    @JsonProperty("Required") public Boolean required;
    @JsonProperty("Default") public String defaultValue;
    @JsonProperty("Placeholder") public String placeholder;
    @JsonProperty("Options") public List<String> options;
    @JsonProperty("Tips") public String tips;
    @JsonProperty("MaxLength") public Integer maxLength;
    @JsonProperty("Format") public String format;
}
