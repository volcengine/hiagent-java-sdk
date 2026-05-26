package com.volcengine.hibot.v1.types;

import java.util.List;
import java.util.Map;

public final class V1MCPTestConnectionParams {
    public String workspaceId;
    public String transport;
    public String endpoint;
    public Map<String, String> headers;
    public Map<String, String> env;
    public String command;
    public List<String> args;
    public String authType;
    public V1CredentialRefParams credential;
    public Long timeout;
}
