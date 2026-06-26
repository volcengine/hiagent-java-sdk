package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1PageOutput {
    @JsonProperty("TotalCount") public Integer totalCount;
    @JsonProperty("TotalPage") public Integer totalPage;
}
