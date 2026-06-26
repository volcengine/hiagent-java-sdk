package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1SessionAuthContext {
    @JsonProperty("IdentityID") public String identityId;
    @JsonProperty("AccessLevel") public String accessLevel;
}
