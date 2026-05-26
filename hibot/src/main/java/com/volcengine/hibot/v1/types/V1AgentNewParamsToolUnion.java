package com.volcengine.hibot.v1.types;

public final class V1AgentNewParamsToolUnion {
    public V1ManagedAgentSkillToolParams ofSkill;
    public V1ManagedAgentMCPToolParams ofMcp;

    public static V1AgentNewParamsToolUnion ofSkill(V1ManagedAgentSkillToolParams p) {
        V1AgentNewParamsToolUnion u = new V1AgentNewParamsToolUnion();
        u.ofSkill = p;
        return u;
    }

    public static V1AgentNewParamsToolUnion ofMcp(V1ManagedAgentMCPToolParams p) {
        V1AgentNewParamsToolUnion u = new V1AgentNewParamsToolUnion();
        u.ofMcp = p;
        return u;
    }
}
