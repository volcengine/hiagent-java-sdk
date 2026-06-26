package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ListTraceSpansResponse {
    @JsonProperty("Items") public List<V1SpanSummary> items;
    @JsonProperty("NextScrollID") public String nextScrollId;
    @JsonProperty("Total") public Long total;
}
