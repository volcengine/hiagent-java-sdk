package com.volcengine.hibot.v1.types;

public final class V1SessionChatEvent {
    public String type;
    public String requestId;
    public V1SessionTextDelta delta = new V1SessionTextDelta();
    public V1SessionChatError error = new V1SessionChatError();
    public V1Message message;
    /** Raw SSE data payload. */
    public String rawData;
}
