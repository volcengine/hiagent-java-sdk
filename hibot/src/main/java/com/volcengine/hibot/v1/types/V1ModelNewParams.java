package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public final class V1ModelNewParams {
    public String workspaceId;
    public String id;
    public String name;
    public String description;
    public String type;
    public String provider;
    public String spec;
    public String modelName;
    public JsonNode featuresConfig;
    public JsonNode property;
    public JsonNode credentialSchema;
    public Map<String, String> credential;
}
