package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1SpanResource {
    @JsonProperty("ServiceName") public String serviceName;
    @JsonProperty("ServiceVersion") public String serviceVersion;
    @JsonProperty("TelemetrySdkName") public String telemetrySdkName;
    @JsonProperty("TelemetrySdkVersion") public String telemetrySdkVersion;
    @JsonProperty("HostName") public String hostName;
    @JsonProperty("DeploymentEnvironment") public String deploymentEnvironment;
}
