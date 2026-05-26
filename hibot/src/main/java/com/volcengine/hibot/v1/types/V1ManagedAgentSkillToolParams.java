package com.volcengine.hibot.v1.types;

public final class V1ManagedAgentSkillToolParams {
    public String type;
    public String skillVersionId;

    public V1ManagedAgentSkillToolParams() {}

    public V1ManagedAgentSkillToolParams(String type, String skillVersionId) {
        this.type = type;
        this.skillVersionId = skillVersionId;
    }
}
