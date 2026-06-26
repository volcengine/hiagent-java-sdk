package com.volcengine.hibot.v1.types;

import java.util.List;

/**
 * 对应服务端 CredentialConfig 入参，用于在创建 / 更新 / 测试连接 MCP
 * 时同时声明所需凭证；服务端会据此创建或绑定 credential provider。
 */
public final class V1MCPCredentialInputParams {
    public String name;
    public String description;
    public String source;
    public String providerType;
    /** Free-form provider config; Jackson serializes into JSON. */
    public Object config;
    public List<V1CredentialSecretInputParams> secrets;

    @Override
    public String toString() {
        return "V1MCPCredentialInputParams{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", source='" + source + '\'' +
                ", providerType='" + providerType + '\'' +
                ", config=" + config +
                ", secrets=" + secrets +
                '}';
    }
}
