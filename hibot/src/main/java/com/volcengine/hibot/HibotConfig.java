package com.volcengine.hibot;

import java.net.http.HttpClient;
import java.time.Duration;

/** Hibot SDK client configuration. */
public final class HibotConfig {
    private final String endpoint;
    private final String accessKey;
    private final String secretKey;
    private final String workspaceId;
    private final String region;
    private final HttpClient httpClient;
    private final String serverService;
    private final String gatewayService;
    private final String modelService;
    private final String upService;

    private HibotConfig(Builder b) {
        this.endpoint = trim(b.endpoint);
        this.accessKey = trim(b.accessKey);
        this.secretKey = trim(b.secretKey);
        this.workspaceId = trim(b.workspaceId);
        this.region = orDefault(trim(b.region), "cn-north-1");
        this.serverService = orDefault(trim(b.serverService), "hibot-server");
        this.gatewayService = orDefault(trim(b.gatewayService), "hibot-gateway");
        this.modelService = orDefault(trim(b.modelService), "aigw");
        this.upService = orDefault(trim(b.upService), "up");
        this.httpClient = b.httpClient != null
                ? b.httpClient
                : HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(30)).build();

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
    public HttpClient httpClient() { return httpClient; }
    public String serverService() { return serverService; }
    public String gatewayService() { return gatewayService; }
    public String modelService() { return modelService; }
    public String upService() { return upService; }

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
        private HttpClient httpClient;
        private String serverService;
        private String gatewayService;
        private String modelService;
        private String upService;

        public Builder endpoint(String v) { this.endpoint = v; return this; }
        public Builder accessKey(String v) { this.accessKey = v; return this; }
        public Builder secretKey(String v) { this.secretKey = v; return this; }
        public Builder workspaceId(String v) { this.workspaceId = v; return this; }
        public Builder region(String v) { this.region = v; return this; }
        public Builder httpClient(HttpClient v) { this.httpClient = v; return this; }
        public Builder serverService(String v) { this.serverService = v; return this; }
        public Builder gatewayService(String v) { this.gatewayService = v; return this; }
        public Builder modelService(String v) { this.modelService = v; return this; }
        public Builder upService(String v) { this.upService = v; return this; }

        public HibotConfig build() { return new HibotConfig(this); }
    }
}
