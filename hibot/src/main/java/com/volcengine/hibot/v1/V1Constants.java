package com.volcengine.hibot.v1;

/** Constants mirroring go/hibot/v1/types.go. */
public final class V1Constants {
    private V1Constants() {}

    public static final String V1_MANAGED_AGENT_MODEL_DOUBAO_SEED_PRO = "doubao-seed-2.0-pro-260215";
    public static final String V1_RESOURCE_TYPE_DOCUMENT_COLLECTION = "document_collection";
    public static final String V1_MCP_TRANSPORT_STREAMABLE_HTTP = "streamable_http";

    public static final String V1_MANAGED_AGENT_SKILL_TOOL_PARAMS_TYPE_SKILL = "skill";
    public static final String V1_MANAGED_AGENT_MCP_TOOL_PARAMS_TYPE_MCP = "mcp";

    /** SSE event names; aligned with hibot-gateway (internal/components/gateway/ssehub.go). */
    public static final String V1_SESSION_CHAT_EVENT_DELTA = "delta";
    public static final String V1_SESSION_CHAT_EVENT_COMPLETED = "completed";
    public static final String V1_SESSION_CHAT_EVENT_FAILED = "failed";
    public static final String V1_SESSION_CHAT_EVENT_RUN_CANCELLING = "run_cancelling";
    public static final String V1_SESSION_CHAT_EVENT_RUN_CANCELLED = "run_cancelled";
    public static final String V1_SESSION_CHAT_EVENT_APPROVAL_REQUEST = "approval_request";
    public static final String V1_SESSION_CHAT_EVENT_APPROVAL_RESPONDED = "approval_responded";
    public static final String V1_SESSION_CHAT_EVENT_TOOL_START = "tool_start";
    public static final String V1_SESSION_CHAT_EVENT_TOOL_COMPLETE = "tool_complete";
}
