package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1AgentSecurityConfig {
    @JsonProperty("Enabled") public Boolean enabled;
    @JsonProperty("CloudAccessKey") public String cloudAccessKey;
    @JsonProperty("CloudSecretKey") public String cloudSecretKey;
    @JsonProperty("CloudAccountID") public String cloudAccountId;
    @JsonProperty("CloudRegion") public String cloudRegion;
    @JsonProperty("CloudSessionToken") public String cloudSessionToken;

    @Override
    public String toString() {
        return "V1AgentSecurityConfig{" +
                "enabled=" + enabled +
                ", cloudAccessKey='" + mask(cloudAccessKey) + '\'' +
                ", cloudSecretKey='***'" +
                ", cloudAccountId='" + cloudAccountId + '\'' +
                ", cloudRegion='" + cloudRegion + '\'' +
                ", cloudSessionToken='***'" +
                '}';
    }

    private static String mask(String s) {
        if (s == null || s.length() <= 4) return "***";
        return s.substring(0, 2) + "***" + s.substring(s.length() - 2);
    }
}
