package com.volcengine.hibot.v1.types;

import java.util.List;

/**
 * 对应服务端 SkillCredentialInput；用于在创建 / 更新 Skill
 * 时一并提交原始凭证（凭证值字段名为 SecretValue）。
 */
public final class V1SkillCredentialInputParams {
    public String name;
    public String description;
    public String source;
    public String providerType;
    /** Free-form provider config; Jackson serializes into JSON. */
    public Object config;
    public List<V1CredentialSecretInputParams> secrets;

    @Override
    public String toString() {
        return "V1SkillCredentialInputParams{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", source='" + source + '\'' +
                ", providerType='" + providerType + '\'' +
                ", config=" + config +
                ", secrets=" + secrets +
                '}';
    }
}
