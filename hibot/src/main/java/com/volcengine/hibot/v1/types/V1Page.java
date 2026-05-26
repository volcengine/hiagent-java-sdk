package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/** V1 Page meta (server-returned). */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1Page {
    @JsonProperty("PageNum") public Integer pageNum;
    @JsonProperty("PageSize") public Integer pageSize;
    @JsonProperty("Total") public Long total;
}
