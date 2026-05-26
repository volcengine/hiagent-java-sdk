package com.volcengine.hibot.v1.types;

import java.util.List;
import java.util.Map;

public final class V1MCPUpdateParams {
    public String workspaceId;
    public String id;
    public String name;
    public String description;
    public String transport;
    public String endpoint;
    public Map<String, String> headers;
    public Map<String, String> env;
    public String command;
    public List<String> args;
    public String authType;
    public List<String> toolAllowlist;
    public List<String> toolDenylist;
    public String toolPrefix;
    public Long timeout;
    public String status;
    public String source;
}
