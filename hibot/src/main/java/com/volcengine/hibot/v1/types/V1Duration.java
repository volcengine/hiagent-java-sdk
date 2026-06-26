package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1Duration {
    @JsonProperty("Min") public Integer min;
    @JsonProperty("Max") public Integer max;
    @JsonProperty("Default") public Integer defaultValue;
    @JsonProperty("Recommends") public List<Integer> recommends;
    @JsonProperty("RecommendDefault") public Integer recommendDefault;
}
