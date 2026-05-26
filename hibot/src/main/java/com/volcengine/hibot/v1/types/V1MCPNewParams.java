package com.volcengine.hibot.v1.types;

import java.util.List;
import java.util.Map;

public final class V1MCPNewParams {
    public String workspaceId;
    public String name;
    public String description;
    public String transport;
    /** Server-side field URL, exposed as endpoint in the SDK. */
    public String endpoint;
    public Map<String, String> headers;
    public Map<String, String> env;
    public String command;
    public List<String> args;
    public String authType;
    public V1CredentialRefParams credential;
    public List<String> toolAllowlist;
    public List<String> toolDenylist;
    public String toolPrefix;
    public Long timeout;
    public String source;
}
