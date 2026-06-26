package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1ChatFile {
    @JsonProperty("Name") public String name;
    @JsonProperty("ContentType") public String contentType;
    @JsonProperty("URL") public String url;
    @JsonProperty("BlobID") public String blobId;
}
