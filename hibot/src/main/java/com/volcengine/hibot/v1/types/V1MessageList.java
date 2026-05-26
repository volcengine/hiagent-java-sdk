package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1MessageList {
    @JsonProperty("Items") public List<V1Message> items;
    @JsonProperty("Page") public V1Page page;
}
