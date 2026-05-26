package com.volcengine.hibot.v1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volcengine.hibot.ApiException;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.internal.Bodies;
import com.volcengine.hibot.internal.RequestExecutor;
import com.volcengine.hibot.internal.ResponseDecoder;
import com.volcengine.hibot.internal.SseDecoder;
import com.volcengine.hibot.internal.Versions;
import com.volcengine.hibot.v1.types.V1Message;
import com.volcengine.hibot.v1.types.V1MessageGetParams;
import com.volcengine.hibot.v1.types.V1MessageInjectParams;
import com.volcengine.hibot.v1.types.V1MessageList;
import com.volcengine.hibot.v1.types.V1MessageListParams;
import com.volcengine.hibot.v1.types.V1Session;
import com.volcengine.hibot.v1.types.V1SessionArchiveParams;
import com.volcengine.hibot.v1.types.V1SessionChatError;
import com.volcengine.hibot.v1.types.V1SessionChatEvent;
import com.volcengine.hibot.v1.types.V1SessionChatParams;
import com.volcengine.hibot.v1.types.V1SessionDeleteParams;
import com.volcengine.hibot.v1.types.V1SessionGetByKeyParams;
import com.volcengine.hibot.v1.types.V1SessionGetParams;
import com.volcengine.hibot.v1.types.V1SessionList;
import com.volcengine.hibot.v1.types.V1SessionListParams;
import com.volcengine.hibot.v1.types.V1SessionNewParams;
import com.volcengine.hibot.v1.types.V1SessionTextDelta;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Mirrors go/hibot/v1/sessions.go and stream.go. */
public final class SessionsService {
    private final RequestExecutor requester;
    private final HibotConfig config;
    private final ConcurrentHashMap<String, String> sessionAgents = new ConcurrentHashMap<>();
    private static final ObjectMapper MAPPER = ResponseDecoder.mapper();

    public SessionsService(RequestExecutor requester, HibotConfig config) {
        this.requester = requester;
        this.config = config;
    }

    public V1Session create(V1SessionNewParams params) {
        if (params == null) params = new V1SessionNewParams();
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        Bodies.putIfNotEmpty(body, "AgentID", params.agentId);

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("Channel", "webchat");
        payload.put("PeerKind", "system");
        payload.put("PeerID", params.agentId);
        if (params.peer != null) {
            if (!isEmpty(params.peer.channel)) {
                payload.put("Channel", params.peer.channel);
            }
            if (!isEmpty(params.peer.peerKind)) {
                payload.put("PeerKind", params.peer.peerKind);
            }
            if (!isEmpty(params.peer.peerId)) {
                payload.put("PeerID", params.peer.peerId);
            }
        }
        body.put("Payload", payload);

        V1Session result = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "CreateSession", body),
                new TypeReference<V1Session>() {});
        if (result == null || isEmpty(result.id)) {
            throw new IllegalStateException("hibot: create session response missing ID");
        }
        result.agentId = params.agentId;
        if (!isEmpty(params.agentId)) {
            sessionAgents.put(result.id, params.agentId);
        }
        return result;
    }

    public V1SessionList list(V1SessionListParams params) {
        Map<String, Object> body = Bodies.map();
        if (params != null) {
            Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
            Bodies.putIfNotEmpty(body, "AgentID", params.agentId);
            Bodies.putIfNotEmpty(body, "Status", params.status);
            Bodies.putIfNotEmpty(body, "Channel", params.channel);
            if (params.page != null) body.put("Page", params.page);
        }
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListSessions", body),
                new TypeReference<V1SessionList>() {});
    }

    public V1Session get(V1SessionGetParams params) {
        if (params == null || isEmpty(params.sessionId)) {
            throw new IllegalArgumentException("hibot: session id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("SessionID", params.sessionId);
        V1Session r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetSession", body),
                new TypeReference<V1Session>() {});
        if (r == null || isEmpty(r.id)) {
            throw new IllegalStateException("hibot: get session response missing ID");
        }
        return r;
    }

    public V1Session getByKey(V1SessionGetByKeyParams params) {
        if (params == null || isEmpty(params.sessionKey)) {
            throw new IllegalArgumentException("hibot: session key is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        Bodies.putIfNotEmpty(body, "AgentID", params.agentId);
        body.put("SessionKey", params.sessionKey);
        V1Session r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetSessionByKey", body),
                new TypeReference<V1Session>() {});
        if (r == null || isEmpty(r.id)) {
            throw new IllegalStateException("hibot: get session by key response missing ID");
        }
        return r;
    }

    public void archive(V1SessionArchiveParams params) {
        if (params == null || isEmpty(params.sessionId)) {
            throw new IllegalArgumentException("hibot: session id is required");
        }
        Map<String, Object> payload = new LinkedHashMap<>();
        if (!isEmpty(params.summary)) payload.put("Summary", params.summary);
        if (params.consolidate != null) payload.put("Consolidate", params.consolidate);
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("SessionID", params.sessionId);
        if (!payload.isEmpty()) body.put("Payload", payload);
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ArchiveSession", body),
                null);
    }

    public void delete(V1SessionDeleteParams params) {
        if (params == null || isEmpty(params.sessionId)) {
            throw new IllegalArgumentException("hibot: session id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("SessionID", params.sessionId);
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "DeleteSession", body),
                null);
    }

    public V1MessageList listMessages(V1MessageListParams params) {
        if (params == null || isEmpty(params.sessionId)) {
            throw new IllegalArgumentException("hibot: session id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("SessionID", params.sessionId);
        Bodies.putIfNotEmpty(body, "Visibility", params.visibility);
        if (params.page != null) body.put("Page", params.page);
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListMessages", body),
                new TypeReference<V1MessageList>() {});
    }

    public V1Message getMessage(V1MessageGetParams params) {
        if (params == null || isEmpty(params.sessionId) || isEmpty(params.messageId)) {
            throw new IllegalArgumentException("hibot: session id and message id are required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("SessionID", params.sessionId);
        body.put("MessageID", params.messageId);
        V1Message r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetMessage", body),
                new TypeReference<V1Message>() {});
        if (r == null || isEmpty(r.id)) {
            throw new IllegalStateException("hibot: get message response missing ID");
        }
        return r;
    }

    public V1Message injectMessage(V1MessageInjectParams params) {
        if (params == null || isEmpty(params.sessionId)) {
            throw new IllegalArgumentException("hibot: session id is required");
        }
        Map<String, Object> payload = new LinkedHashMap<>();
        if (!isEmpty(params.role)) payload.put("Role", params.role);
        if (!isEmpty(params.content)) payload.put("Content", params.content);
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("SessionID", params.sessionId);
        body.put("Payload", payload);
        V1Message r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "InjectMessage", body),
                new TypeReference<V1Message>() {});
        if (r == null || isEmpty(r.id)) {
            throw new IllegalStateException("hibot: inject message response missing ID");
        }
        return r;
    }

    /** Send a chat message and block until the final response arrives. */
    public V1Message chat(String sessionId, V1SessionChatParams params) {
        try (V1ChatStream stream = chatStreaming(sessionId, params)) {
            while (stream.next()) {
                V1SessionChatEvent ev = stream.current();
                if (V1Constants.V1_SESSION_CHAT_EVENT_FAILED.equals(ev.type)) {
                    throw new IllegalStateException("hibot: chat failed: " + (ev.error == null ? "" : ev.error.message));
                }
            }
            Throwable err = stream.err();
            if (err != null) {
                if (err instanceof RuntimeException) throw (RuntimeException) err;
                throw new RuntimeException(err);
            }
            return stream.finalMessage();
        }
    }

    /** Send a chat message in streaming mode. */
    public V1ChatStream chatStreaming(String sessionId, V1SessionChatParams params) {
        if (params == null) params = new V1SessionChatParams();
        String agentId = params.agentId;
        if (isEmpty(agentId)) {
            agentId = sessionAgents.get(sessionId);
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("SessionID", sessionId);
        Bodies.putIfNotEmpty(body, "AgentID", agentId);
        Bodies.putIfNotEmpty(body, "Content", params.input);
        Bodies.putIfNotEmpty(body, "ClientMessageID", params.clientMessageId);

        V1ChatStream stream = new V1ChatStream();
        try {
            HttpResponse<InputStream> resp = requester.doStream(
                    new RequestExecutor.Action(config.gatewayService(), Versions.CHAT, "Chat", body));
            if (resp.statusCode() >= 400) {
                byte[] payload;
                try (InputStream is = resp.body()) {
                    payload = is == null ? new byte[0] : is.readAllBytes();
                }
                stream.err = new ApiException(resp.statusCode(), "", "",
                        new String(payload, StandardCharsets.UTF_8));
                return stream;
            }
            stream.response = resp;
            stream.decoder = new SseDecoder(resp.body());
        } catch (Exception e) {
            stream.err = e;
        }
        return stream;
    }

    String agentIdForSession(String sessionId) {
        return sessionAgents.get(sessionId);
    }

    private static boolean isEmpty(String s) { return s == null || s.isEmpty(); }

    static V1SessionChatEvent decodeChatEvent(String eventName, String data) {
        V1SessionChatEvent ev = new V1SessionChatEvent();
        ev.type = normalizeChatEventName(eventName);
        ev.rawData = data;
        if (data == null || data.isEmpty()) {
            return ev;
        }
        JsonNode payload;
        try {
            payload = MAPPER.readTree(data);
        } catch (Exception e) {
            throw new RuntimeException("hibot: decode sse data: " + e.getMessage(), e);
        }
        if (payload == null || !payload.isObject()) {
            return ev;
        }
        JsonNode rawType = payload.get("type");
        if (rawType != null && (ev.type == null || ev.type.isEmpty())) {
            ev.type = normalizeChatEventName(rawType.asText(""));
        }
        JsonNode rawRequestId = firstNode(payload, "request_id", "RequestID", "RequestId");
        if (rawRequestId != null) {
            ev.requestId = rawRequestId.asText("");
        }
        JsonNode rawDelta = payload.get("delta");
        if (rawDelta != null) {
            if (rawDelta.isObject()) {
                ev.delta = MAPPER.convertValue(rawDelta, V1SessionTextDelta.class);
                if (ev.delta == null) ev.delta = new V1SessionTextDelta();
            } else if (rawDelta.isTextual()) {
                ev.delta = new V1SessionTextDelta(rawDelta.asText(""));
            }
        }
        if (ev.delta == null) ev.delta = new V1SessionTextDelta();
        if (isEmpty(ev.delta.text)) {
            JsonNode rawText = firstNode(payload, "text", "Text", "content", "Content");
            if (rawText != null && rawText.isTextual()) {
                ev.delta.text = rawText.asText("");
            }
        }
        if (ev.error == null) ev.error = new V1SessionChatError();
        JsonNode rawErr = firstNode(payload, "error", "Error");
        if (rawErr != null) {
            if (rawErr.isObject()) {
                try {
                    V1SessionChatError parsed = MAPPER.convertValue(rawErr, V1SessionChatError.class);
                    if (parsed != null) ev.error = parsed;
                } catch (Exception ignore) {
                    // Tolerate unknown fields — fall back to manual extraction below.
                }
                if (isEmpty(ev.error.code)) {
                    JsonNode c = firstNode(rawErr, "code", "Code");
                    if (c != null) ev.error.code = c.asText("");
                }
                if (isEmpty(ev.error.message)) {
                    JsonNode m = firstNode(rawErr, "message", "Message");
                    if (m != null) ev.error.message = m.asText("");
                }
            } else if (rawErr.isTextual()) {
                ev.error.message = rawErr.asText("");
            }
        }
        if (isEmpty(ev.error.code)) {
            JsonNode rawCode = firstNode(payload, "code", "Code");
            if (rawCode != null) ev.error.code = rawCode.asText("");
        }
        if (isEmpty(ev.error.message)) {
            JsonNode rawMsg = firstNode(payload, "message", "Message");
            if (rawMsg != null && rawMsg.isTextual()) {
                ev.error.message = rawMsg.asText("");
            }
        }
        JsonNode rawMessage = firstNode(payload, "message", "Message");
        if (rawMessage != null && rawMessage.isObject()) {
            try {
                V1Message msg = MAPPER.convertValue(rawMessage, V1Message.class);
                if (msg != null) ev.message = msg;
            } catch (Exception ignore) {
                // Keep going.
            }
        }
        if (V1Constants.V1_SESSION_CHAT_EVENT_COMPLETED.equals(ev.type) && ev.message == null) {
            V1Message msg = new V1Message();
            JsonNode rawId = firstNode(payload, "message_id", "MessageID", "ID");
            if (rawId != null) msg.id = rawId.asText("");
            JsonNode rawContent = firstNode(payload, "content", "Content");
            if (rawContent != null && rawContent.isTextual()) msg.content = rawContent.asText("");
            if (!isEmpty(msg.id) || !isEmpty(msg.content)) {
                ev.message = msg;
            }
        }
        return ev;
    }

    static String normalizeChatEventName(String name) {
        if (name == null) return "";
        switch (name) {
            case "message.chunk":
            case "message_delta":
            case "message_chunk":
                return V1Constants.V1_SESSION_CHAT_EVENT_DELTA;
            case "message.completed":
            case "message_completed":
            case "run_completed":
                return V1Constants.V1_SESSION_CHAT_EVENT_COMPLETED;
            case "message.failed":
            case "message_failed":
            case "run_failed":
                return V1Constants.V1_SESSION_CHAT_EVENT_FAILED;
            case "tool_started":
                return V1Constants.V1_SESSION_CHAT_EVENT_TOOL_START;
            case "tool_completed":
                return V1Constants.V1_SESSION_CHAT_EVENT_TOOL_COMPLETE;
            default:
                return name;
        }
    }

    private static JsonNode firstNode(JsonNode payload, String... keys) {
        for (String k : keys) {
            JsonNode v = payload.get(k);
            if (v != null) return v;
        }
        return null;
    }
}
