package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class V1FeishuChannelConfig {
    @JsonProperty("AppID") public String appId;
    @JsonProperty("AppSecret") public String appSecret;
    @JsonProperty("EncryptKey") public String encryptKey;
    @JsonProperty("VerificationToken") public String verificationToken;
    @JsonProperty("Domain") public String domain;
    @JsonProperty("ConnectionMode") public String connectionMode;
    @JsonProperty("WebhookPath") public String webhookPath;
    @JsonProperty("RequireMention") public Boolean requireMention;
    @JsonProperty("RenderMode") public String renderMode;
    @JsonProperty("Streaming") public Boolean streaming;
    @JsonProperty("ReactionLevel") public String reactionLevel;
    @JsonProperty("TextChunkLimit") public Integer textChunkLimit;
    @JsonProperty("MediaMaxMB") public Integer mediaMaxMb;
    @JsonProperty("BlockReply") public Boolean blockReply;

    @Override
    public String toString() {
        return "V1FeishuChannelConfig{" +
                "appId='" + appId + '\'' +
                ", appSecret='***'" +
                ", encryptKey='***'" +
                ", verificationToken='***'" +
                ", domain='" + domain + '\'' +
                ", connectionMode='" + connectionMode + '\'' +
                ", webhookPath='" + webhookPath + '\'' +
                ", requireMention=" + requireMention +
                ", renderMode='" + renderMode + '\'' +
                ", streaming=" + streaming +
                ", reactionLevel='" + reactionLevel + '\'' +
                ", textChunkLimit=" + textChunkLimit +
                ", mediaMaxMb=" + mediaMaxMb +
                ", blockReply=" + blockReply +
                '}';
    }
}
