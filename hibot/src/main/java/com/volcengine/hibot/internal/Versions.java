package com.volcengine.hibot.internal;

/** TOP service / version constants. Mirrors go/hibot/internal/version. */
public final class Versions {
    private Versions() {}

    public static final String DEFAULT_REGION = "cn-north-1";

    public static final String SERVER_SERVICE = "hibot-server";
    public static final String GATEWAY_SERVICE = "hibot-gateway";
    public static final String AIGW_SERVICE = "aigw";
    public static final String UP_SERVICE = "up";

    /** API Versions registered on TOP. */
    public static final String SERVER = "2026-04-23";
    public static final String CHAT = "2026-05-11";
    public static final String MODEL = "2023-08-01";
    public static final String UP = "2022-01-01";
}
