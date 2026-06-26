package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1RouteDecision {
    @JsonProperty("RoutingMode") public String routingMode;
    @JsonProperty("RoutedEnvID") public String routedEnvId;
    @JsonProperty("MatchedRuleID") public String matchedRuleId;
    @JsonProperty("Reason") public String reason;
}
