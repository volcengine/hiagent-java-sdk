package com.volcengine.hibot;

import com.volcengine.hibot.internal.Versions;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/** Hibot SDK client configuration. */
public final class HibotConfig {
    private final String endpoint;
    private final String accessKey;
    private final String secretKey;
    private final String workspaceId;
    private final String region;
    private final OkHttpClient httpClient;
    private final String serverService;
    private final String modelService;
    private final String upService;
    private final int maxRetries;
    private final long retryBaseDelayMs;
    private final long streamReadTimeoutSeconds;

    private HibotConfig(Builder b) {
        this.endpoint = trim(b.endpoint);
        this.accessKey = trim(b.accessKey);
        this.secretKey = trim(b.secretKey);
        this.workspaceId = trim(b.workspaceId);
        this.region = orDefault(trim(b.region), Versions.DEFAULT_REGION);
        this.serverService = orDefault(trim(b.serverService), Versions.SERVER_SERVICE);
        this.modelService = orDefault(trim(b.modelService), Versions.AIGW_SERVICE);
        this.upService = orDefault(trim(b.upService), Versions.UP_SERVICE);
        this.maxRetries = b.maxRetries >= 0 ? b.maxRetries : 3;
        this.retryBaseDelayMs = b.retryBaseDelayMs > 0 ? b.retryBaseDelayMs : 500;
        this.streamReadTimeoutSeconds = b.streamReadTimeoutSeconds > 0
                ? b.streamReadTimeoutSeconds : 3600;
        this.httpClient = b.httpClient != null
                ? b.httpClient
                : new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).build();

        if (this.endpoint.isEmpty()) {
            throw new IllegalArgumentException("hibot: endpoint is required");
        }
        if (this.accessKey.isEmpty()) {
            throw new IllegalArgumentException("hibot: access key is required");
        }
        if (this.secretKey.isEmpty()) {
            throw new IllegalArgumentException("hibot: secret key is required");
        }
        if (this.workspaceId.isEmpty()) {
            throw new IllegalArgumentException("hibot: workspace id is required");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public String endpoint() { return endpoint; }
    public String accessKey() { return accessKey; }
    public String secretKey() { return secretKey; }
    public String workspaceId() { return workspaceId; }
    public String region() { return region; }
    public OkHttpClient httpClient() { return httpClient; }
    public String serverService() { return serverService; }
    public String modelService() { return modelService; }
    public String upService() { return upService; }
    public int maxRetries() { return maxRetries; }
    public long retryBaseDelayMs() { return retryBaseDelayMs; }
    public long streamReadTimeoutSeconds() { return streamReadTimeoutSeconds; }

    @Override
    public String toString() {
        return "HibotConfig{" +
                "endpoint='" + endpoint + '\'' +
                ", accessKey='" + mask(accessKey) + '\'' +
                ", secretKey='***'" +
                ", workspaceId='" + workspaceId + '\'' +
                ", region='" + region + '\'' +
                ", serverService='" + serverService + '\'' +
                ", modelService='" + modelService + '\'' +
                ", upService='" + upService + '\'' +
                ", maxRetries=" + maxRetries +
                ", retryBaseDelayMs=" + retryBaseDelayMs +
                ", streamReadTimeoutSeconds=" + streamReadTimeoutSeconds +
                '}';
    }

    private static String mask(String s) {
        if (s == null || s.length() <= 4) return "***";
        return s.substring(0, 2) + "***" + s.substring(s.length() - 2);
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }

    private static String orDefault(String s, String def) {
        return s == null || s.isEmpty() ? def : s;
    }

    public static final class Builder {
        private String endpoint;
        private String accessKey;
        private String secretKey;
        private String workspaceId;
        private String region;
        private OkHttpClient httpClient;
        private String serverService;
        private String modelService;
        private String upService;
        private int maxRetries = -1;
        private long retryBaseDelayMs = -1;
        private long streamReadTimeoutSeconds = -1;

        public Builder endpoint(String v) { this.endpoint = v; return this; }
        public Builder accessKey(String v) { this.accessKey = v; return this; }
        public Builder secretKey(String v) { this.secretKey = v; return this; }
        public Builder workspaceId(String v) { this.workspaceId = v; return this; }
        public Builder region(String v) { this.region = v; return this; }
        public Builder httpClient(OkHttpClient v) { this.httpClient = v; return this; }
        public Builder serverService(String v) { this.serverService = v; return this; }
        public Builder modelService(String v) { this.modelService = v; return this; }
        public Builder upService(String v) { this.upService = v; return this; }
        public Builder maxRetries(int v) { this.maxRetries = v; return this; }
        public Builder retryBaseDelayMs(long v) { this.retryBaseDelayMs = v; return this; }
        public Builder streamReadTimeoutSeconds(long v) { this.streamReadTimeoutSeconds = v; return this; }

        public HibotConfig build() { return new HibotConfig(this); }
    }
}
