package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1GetRuntimeAPIKeyResponse {
    @JsonProperty("RawKey") public String rawKey;

    @Override
    public String toString() {
        return "V1GetRuntimeAPIKeyResponse{" +
                "rawKey='***'" +
                '}';
    }
}
