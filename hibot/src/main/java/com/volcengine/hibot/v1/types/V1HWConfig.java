package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1HWConfig {
    @JsonProperty("Width") public V1IntRange width;
    @JsonProperty("Height") public V1IntRange height;
    @JsonProperty("Ratio") public V1DoubleRange ratio;
    @JsonProperty("Pairs") public List<List<Short>> pairs;
    @JsonProperty("Total") public V1IntRange total;
}
