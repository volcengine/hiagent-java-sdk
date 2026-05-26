package com.volcengine.hibot.v1.types;

public final class V1ManagedAgentResourceRefParams {
    public String id;
    public String directoryId;

    public V1ManagedAgentResourceRefParams() {}

    public static V1ManagedAgentResourceRefParams ofResource(String id) {
        V1ManagedAgentResourceRefParams p = new V1ManagedAgentResourceRefParams();
        p.id = id;
        return p;
    }

    public static V1ManagedAgentResourceRefParams ofDirectory(String directoryId) {
        V1ManagedAgentResourceRefParams p = new V1ManagedAgentResourceRefParams();
        p.directoryId = directoryId;
        return p;
    }
}
