package com.volcengine.hibot.v1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.internal.Bodies;
import com.volcengine.hibot.internal.RequestExecutor;
import com.volcengine.hibot.internal.Versions;
import com.volcengine.hibot.v1.types.V1MCP;
import com.volcengine.hibot.v1.types.V1MCPDeleteParams;
import com.volcengine.hibot.v1.types.V1MCPGetParams;
import com.volcengine.hibot.v1.types.V1MCPListParams;
import com.volcengine.hibot.v1.types.V1MCPNewParams;
import com.volcengine.hibot.v1.types.V1MCPResolveParams;
import com.volcengine.hibot.v1.types.V1MCPTestConnectionParams;
import com.volcengine.hibot.v1.types.V1MCPTestConnectionResult;
import com.volcengine.hibot.v1.types.V1MCPUpdateParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Mirrors go/hibot/v1/mcps.go. */
public final class McpsService {
    private final RequestExecutor requester;
    private final HibotConfig config;

    public McpsService(RequestExecutor requester, HibotConfig config) {
        this.requester = requester;
        this.config = config;
    }

    public V1MCP create(V1MCPNewParams params) {
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        Bodies.putIfNotEmpty(body, "Name", params.name);
        Bodies.putIfNotEmpty(body, "Description", params.description);
        Bodies.putIfNotEmpty(body, "Transport", params.transport);
        // SDK exposes "endpoint", server-side field is "URL".
        Bodies.putIfNotEmpty(body, "URL", params.endpoint);
        if (params.headers != null) body.put("Headers", params.headers);
        if (params.env != null) body.put("Env", params.env);
        Bodies.putIfNotEmpty(body, "Command", params.command);
        if (params.args != null) body.put("Args", params.args);
        Bodies.putIfNotEmpty(body, "AuthType", params.authType);
        if (params.credential != null) body.put("Credential", params.credential);
        if (params.toolAllowlist != null) body.put("ToolAllowlist", params.toolAllowlist);
        if (params.toolDenylist != null) body.put("ToolDenylist", params.toolDenylist);
        Bodies.putIfNotEmpty(body, "ToolPrefix", params.toolPrefix);
        if (params.timeout != null) body.put("Timeout", params.timeout);
        Bodies.putIfNotEmpty(body, "Source", params.source);
        IdResult r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "CreateMCP", body),
                new TypeReference<IdResult>() {});
        if (r == null || isEmpty(r.id)) {
            throw new IllegalStateException("hibot: create mcp response missing ID");
        }
        V1MCP out = new V1MCP();
        out.id = r.id;
        out.name = params.name;
        out.transport = params.transport;
        out.endpoint = params.endpoint;
        return out;
    }

    public List<V1MCP> list(V1MCPListParams params) {
        Map<String, Object> body = Bodies.map();
        if (params != null) {
            Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
            Bodies.putIfNotEmpty(body, "Keyword", params.keyword);
            Bodies.putIfNotEmpty(body, "Status", params.status);
            Bodies.putIfNotEmpty(body, "Source", params.source);
            if (params.page != null) body.put("Page", params.page);
        }
        Items r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListMCPs", body),
                new TypeReference<Items>() {});
        if (r == null || r.items == null) return new ArrayList<>();
        return r.items;
    }

    public V1MCP get(V1MCPGetParams params) {
        if (params == null || isEmpty(params.id)) {
            throw new IllegalArgumentException("hibot: mcp id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("ID", params.id);
        V1MCP r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetMCP", body),
                new TypeReference<V1MCP>() {});
        if (r == null || isEmpty(r.id)) {
            throw new IllegalStateException("hibot: get mcp response missing ID");
        }
        return r;
    }

    public void update(V1MCPUpdateParams params) {
        if (params == null || isEmpty(params.id)) {
            throw new IllegalArgumentException("hibot: mcp id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("ID", params.id);
        if (params.name != null) body.put("Name", params.name);
        if (params.description != null) body.put("Description", params.description);
        if (params.transport != null) body.put("Transport", params.transport);
        // Endpoint is mapped to URL for the server.
        if (params.endpoint != null) body.put("URL", params.endpoint);
        if (params.headers != null) body.put("Headers", params.headers);
        if (params.env != null) body.put("Env", params.env);
        if (params.command != null) body.put("Command", params.command);
        if (params.args != null) body.put("Args", params.args);
        if (params.authType != null) body.put("AuthType", params.authType);
        if (params.toolAllowlist != null) body.put("ToolAllowlist", params.toolAllowlist);
        if (params.toolDenylist != null) body.put("ToolDenylist", params.toolDenylist);
        if (params.toolPrefix != null) body.put("ToolPrefix", params.toolPrefix);
        if (params.timeout != null) body.put("Timeout", params.timeout);
        if (params.status != null) body.put("Status", params.status);
        if (params.source != null) body.put("Source", params.source);
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "UpdateMCP", body),
                null);
    }

    public void delete(V1MCPDeleteParams params) {
        if (params == null || isEmpty(params.id)) {
            throw new IllegalArgumentException("hibot: mcp id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("ID", params.id);
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "DeleteMCP", body),
                null);
    }

    public V1MCPTestConnectionResult testConnection(V1MCPTestConnectionParams params) {
        Map<String, Object> body = Bodies.map();
        if (params != null) {
            Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
            Bodies.putIfNotEmpty(body, "Transport", params.transport);
            Bodies.putIfNotEmpty(body, "URL", params.endpoint);
            if (params.headers != null) body.put("Headers", params.headers);
            if (params.env != null) body.put("Env", params.env);
            Bodies.putIfNotEmpty(body, "Command", params.command);
            if (params.args != null) body.put("Args", params.args);
            Bodies.putIfNotEmpty(body, "AuthType", params.authType);
            if (params.credential != null) body.put("Credential", params.credential);
            if (params.timeout != null) body.put("Timeout", params.timeout);
        }
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER,
                        "TestMCPConnection", body),
                new TypeReference<V1MCPTestConnectionResult>() {});
    }

    public V1MCP resolve(V1MCPResolveParams params) {
        if (params == null) {
            throw new IllegalArgumentException("hibot: mcp id or name is required");
        }
        if (!isEmpty(params.id)) {
            V1MCP m = new V1MCP();
            m.id = params.id;
            m.name = params.name;
            return m;
        }
        if (isEmpty(params.name)) {
            throw new IllegalArgumentException("hibot: mcp id or name is required");
        }
        V1MCPListParams lp = new V1MCPListParams();
        lp.workspaceId = params.workspaceId;
        lp.keyword = params.name;
        List<V1MCP> items = list(lp);
        for (V1MCP item : items) {
            if (params.name.equals(item.name) && !isEmpty(item.id)) {
                return item;
            }
        }
        throw new IllegalStateException("hibot: mcp \"" + params.name + "\" not found");
    }

    private static boolean isEmpty(String s) { return s == null || s.isEmpty(); }

    private static final class IdResult {
        @com.fasterxml.jackson.annotation.JsonProperty("ID") public String id;
    }
    private static final class Items {
        @com.fasterxml.jackson.annotation.JsonProperty("Items") public List<V1MCP> items;
    }
}
