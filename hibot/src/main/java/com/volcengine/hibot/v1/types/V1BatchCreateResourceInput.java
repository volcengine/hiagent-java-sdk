package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1BatchCreateResourceInput {
    @JsonProperty("DirectoryID") public String directoryId;
    @JsonProperty("Name") public String name;
    @JsonProperty("BlobID") public String blobId;
}
