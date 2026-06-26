package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1RunHourlyBucket {
    @JsonProperty("BucketStart") public String bucketStart;
    @JsonProperty("Succeeded") public Long succeeded;
    @JsonProperty("Failed") public Long failed;
    @JsonProperty("Cancelled") public Long cancelled;
}
