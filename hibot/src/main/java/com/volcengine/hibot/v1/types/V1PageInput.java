package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Pagination input. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1PageInput {
    @JsonProperty("PageNum") public Integer pageNum;
    @JsonProperty("PageSize") public Integer pageSize;

    public V1PageInput() {}

    public V1PageInput(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
