package com.volcengine.hibot.v1.types;

import com.fasterxml.jackson.databind.JsonNode;

public final class V1EnvironmentNewParams {
    public String workspaceId;
    public String name;
    public String description;
    public String imageType;
    public JsonNode envVars;
    public String cpuLimit;
    public String memoryLimit;
    public String pvcSize;
    public String dataPath;
}
