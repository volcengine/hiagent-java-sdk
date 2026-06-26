package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ListModelProvidersRequest {
    @JsonProperty("Page") public V1PageInput page;
    @JsonProperty("Filter") public V1ListProviderFilter filter;
    @JsonProperty("SortBy") public String sortBy;
    @JsonProperty("SortOrder") public String sortOrder;
}
