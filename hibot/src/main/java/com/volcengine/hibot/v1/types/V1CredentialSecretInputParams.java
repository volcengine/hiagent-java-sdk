package com.volcengine.hibot.v1.types;

public final class V1CredentialSecretInputParams {
    public String secretId;
    public String keyName;
    public String description;
    public String secretType;
    public String secretValue;

    public V1CredentialSecretInputParams() {}

    public V1CredentialSecretInputParams(String keyName, String secretValue) {
        this.keyName = keyName;
        this.secretValue = secretValue;
    }

    @Override
    public String toString() {
        return "V1CredentialSecretInputParams{" +
                "secretId='" + mask(secretId) + '\'' +
                ", keyName='" + mask(keyName) + '\'' +
                ", description='" + description + '\'' +
                ", secretType='" + secretType + '\'' +
                ", secretValue='***'" +
                '}';
    }

    private static String mask(String s) {
        if (s == null || s.length() <= 4) return "***";
        return s.substring(0, 2) + "***" + s.substring(s.length() - 2);
    }
}
