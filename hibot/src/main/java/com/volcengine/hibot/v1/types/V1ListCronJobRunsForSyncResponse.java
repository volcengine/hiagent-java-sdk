package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ListCronJobRunsForSyncResponse {
    @JsonProperty("Items") public List<V1CronJobRunSyncResponse> items;
    @JsonProperty("NextCursorUpdatedAt") public String nextCursorUpdatedAt;
    @JsonProperty("NextCursorID") public String nextCursorId;
}
