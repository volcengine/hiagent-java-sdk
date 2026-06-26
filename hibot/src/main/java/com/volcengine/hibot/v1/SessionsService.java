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
import com.volcengine.hibot.v1.types.V1BatchGetSessionsRequest;
import com.volcengine.hibot.v1.types.V1BatchGetSessionsResponse;
import com.volcengine.hibot.v1.types.V1Message;
import com.volcengine.hibot.v1.types.V1MessageGetParams;
import com.volcengine.hibot.v1.types.V1MessageInjectParams;
import com.volcengine.hibot.v1.types.V1MessageList;
import com.volcengine.hibot.v1.types.V1MessageListParams;
import com.volcengine.hibot.v1.types.V1Session;
import com.volcengine.hibot.v1.types.V1SessionArchiveParams;
import com.volcengine.hibot.v1.types.V1SessionChatError;
import com.volcengine.hibot.v1.types.V1SessionChatEvent;
import com.volcengine.hibot.v1.types.V1ChatSyncResponse;
import com.volcengine.hibot.v1.types.V1SessionChatParams;
import com.volcengine.hibot.v1.types.V1SessionDeleteParams;
import com.volcengine.hibot.v1.types.V1SessionGetByKeyParams;
import com.volcengine.hibot.v1.types.V1SessionGetParams;
import com.volcengine.hibot.v1.types.V1SessionList;
import com.volcengine.hibot.v1.types.V1SessionListParams;
import com.volcengine.hibot.v1.types.V1SessionNewParams;
import com.volcengine.hibot.v1.types.V1SessionTextDelta;

import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/** Mirrors go/hibot/v1/sessions.go and stream.go. */
public final class SessionsService {
    private final RequestExecutor requester;
    private final HibotConfig config;
    private final ConcurrentHashMap<String, String> sessionAgents = new ConcurrentHashMap<>();
    private static final ObjectMapper MAPPER = ResponseDecoder.mapper();
    private static final SecureRandom RNG = new SecureRandom();
    private static final char[] HEX = "0123456789abcdef".toCharArray();

    /**
     * 生成 ConversationID：
     * {@code ^[A-Za-z0-9_-]{1,64}$}。当前实现为 16 字节随机数转 32 位 hex，
     */
    Supplier<String> conversationIdGenerator = () -> {
        byte[] buf = new byte[16];
        RNG.nextBytes(buf);
        char[] out = new char[buf.length * 2];
        for (int i = 0; i < buf.length; i++) {
            int b = buf[i] & 0xff;
            out[i * 2] = HEX[b >>> 4];
            out[i * 2 + 1] = HEX[b & 0x0f];
        }
        return new String(out);
    };

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
            if (!Bodies.isEmpty(params.peer.channel)) {
                payload.put("Channel", params.peer.channel);
            }
            if (!Bodies.isEmpty(params.peer.peerKind)) {
                payload.put("PeerKind", params.peer.peerKind);
            }
            if (!Bodies.isEmpty(params.peer.peerId)) {
                payload.put("PeerID", params.peer.peerId);
            }
        }
        // ConversationID 仅在 webchat（SupportsMultiSession=true）渠道下由SDK 自动生成并透传
        if ("webchat".equals(payload.get("Channel"))) {
            String cid = conversationIdGenerator.get();
            if (cid != null && !cid.isEmpty()) {
                payload.put("ConversationID", cid);
            }
        }
        body.put("Payload", payload);

        V1Session result = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "CreateSession", body),
                new TypeReference<V1Session>() {});
        if (result == null || Bodies.isEmpty(result.id)) {
            throw new IllegalStateException("hibot: create session response missing ID");
        }
        result.agentId = params.agentId;
        if (!Bodies.isEmpty(params.agentId)) {
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
        if (params == null || Bodies.isEmpty(params.sessionId)) {
            throw new IllegalArgumentException("hibot: session id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("SessionID", params.sessionId);
        V1Session r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetSession", body),
                new TypeReference<V1Session>() {});
        if (r == null || Bodies.isEmpty(r.id)) {
            throw new IllegalStateException("hibot: get session response missing ID");
        }
        return r;
    }

    public V1Session getByKey(V1SessionGetByKeyParams params) {
        if (params == null || Bodies.isEmpty(params.sessionKey)) {
            throw new IllegalArgumentException("hibot: session key is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        Bodies.putIfNotEmpty(body, "AgentID", params.agentId);
        body.put("SessionKey", params.sessionKey);
        V1Session r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetSessionByKey", body),
                new TypeReference<V1Session>() {});
        if (r == null || Bodies.isEmpty(r.id)) {
            throw new IllegalStateException("hibot: get session by key response missing ID");
        }
        return r;
    }

    public V1BatchGetSessionsResponse batchGet(V1BatchGetSessionsRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "BatchGetSessions", params),
                new TypeReference<V1BatchGetSessionsResponse>() {});
    }

    public void archive(V1SessionArchiveParams params) {
        if (params == null || Bodies.isEmpty(params.sessionId)) {
            throw new IllegalArgumentException("hibot: session id is required");
        }
        Map<String, Object> payload = new LinkedHashMap<>();
        if (!Bodies.isEmpty(params.summary)) payload.put("Summary", params.summary);
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
        if (params == null || Bodies.isEmpty(params.sessionId)) {
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
        if (params == null || Bodies.isEmpty(params.sessionId)) {
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
        if (params == null || Bodies.isEmpty(params.sessionId) || Bodies.isEmpty(params.messageId)) {
            throw new IllegalArgumentException("hibot: session id and message id are required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("SessionID", params.sessionId);
        body.put("MessageID", params.messageId);
        V1Message r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetMessage", body),
                new TypeReference<V1Message>() {});
        if (r == null || Bodies.isEmpty(r.id)) {
            throw new IllegalStateException("hibot: get message response missing ID");
        }
        return r;
    }

    public V1Message injectMessage(V1MessageInjectParams params) {
        if (params == null || Bodies.isEmpty(params.sessionId)) {
            throw new IllegalArgumentException("hibot: session id is required");
        }
        Map<String, Object> payload = new LinkedHashMap<>();
        if (!Bodies.isEmpty(params.role)) payload.put("Role", params.role);
        if (!Bodies.isEmpty(params.content)) payload.put("Content", params.content);
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("SessionID", params.sessionId);
        body.put("Payload", payload);
        V1Message r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "InjectMessage", body),
                new TypeReference<V1Message>() {});
        if (r == null || Bodies.isEmpty(r.id)) {
            throw new IllegalStateException("hibot: inject message response missing ID");
        }
        return r;
    }

    /**
     * Send a chat message and block until final response.
     *
     * <p>Corresponds to server-side {@code Stream=false} sync branch: hibot-server aggregates all
     * {@code message_completed.content} internally, and returns a single HTTP JSON
     * {@code ChatSyncResponse{Message, TokenCount, Files}} after {@code run_completed}.
     * The SDK wraps it as V1Message and returns; if callers need message id/runId/files etc.,
     * they can call {@link #listMessages} / {@link #getMessage} for secondary fetch.
     *
     * <p>On non-streaming path, if approval is requested, hibot-server auto-approves only when
     * {@code Approve=="all"}. Therefore, when callers do not explicitly set
     * {@link V1SessionChatParams#approve}, SDK defaults to {@code "all"} to ensure batch reply works.
     */
    public V1Message chat(String sessionId, V1SessionChatParams params) {
        if (params == null) params = new V1SessionChatParams();
        Map<String, Object> body = buildChatBody(sessionId, params);
        body.put("Stream", Boolean.FALSE);
        if (Bodies.isEmpty((String) body.get("Approve"))) {
            body.put("Approve", "all");
        }
        RequestExecutor.Action action =
                new RequestExecutor.Action(config.serverService(), Versions.CHAT, "Chat", body);
        action.retryable = false;
        action.readTimeoutSeconds = config.streamReadTimeoutSeconds();
        V1ChatSyncResponse resp = requester.doAction(action, new TypeReference<V1ChatSyncResponse>() {});
        V1Message m = new V1Message();
        m.role = "assistant";
        m.content = resp == null || resp.message == null ? "" : resp.message;
        m.tokenCount = resp == null ? null : resp.tokenCount;
        m.files = resp == null ? null : resp.files;
        return m;
    }

    /** Send a chat message in streaming mode. */
    public V1ChatStream chatStreaming(String sessionId, V1SessionChatParams params) {
        if (params == null) params = new V1SessionChatParams();
        Map<String, Object> body = buildChatBody(sessionId, params);

        V1ChatStream stream = new V1ChatStream();
        try {
            Response resp = requester.doStream(
                    new RequestExecutor.Action(config.serverService(), Versions.CHAT, "Chat", body));
            if (resp.code() >= 400) {
                byte[] payload;
                try (ResponseBody responseBody = resp.body();
                     InputStream is = responseBody == null ? null : responseBody.byteStream()) {
                    payload = is == null ? new byte[0] : readAllBytes(is);
                }
                stream.err = new ApiException(resp.code(), "", "",
                        new String(payload, StandardCharsets.UTF_8));
                return stream;
            }
            stream.response = resp;
            ResponseBody responseBody = resp.body();
            if (responseBody == null) {
                stream.err = new IOException("hibot: stream response body is empty");
                resp.close();
                return stream;
            }
            stream.decoder = new SseDecoder(responseBody.byteStream());
        } catch (Exception e) {
            stream.err = e;
        }
        return stream;
    }

    /** 构造 Chat / ChatStreaming 共用的请求体。 */
    private Map<String, Object> buildChatBody(String sessionId, V1SessionChatParams params) {
        String agentId = params.agentId;
        if (Bodies.isEmpty(agentId)) {
            agentId = sessionAgents.get(sessionId);
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("SessionID", sessionId);
        Bodies.putIfNotEmpty(body, "AgentID", agentId);
        // Content 允许为空：当 files 非空时仅传文件即可。
        if (params.input != null) {
            body.put("Content", params.input);
        }
        if (params.files != null && !params.files.isEmpty()) {
            body.put("Files", params.files);
        }
        Bodies.putIfNotEmpty(body, "ClientMessageID", params.clientMessageId);
        Bodies.putIfNotEmpty(body, "Approve", params.approve);
        return body;
    }

    String agentIdForSession(String sessionId) {
        return sessionAgents.get(sessionId);
    }

    private static byte[] readAllBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int n;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

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
        if (Bodies.isEmpty(ev.delta.text)) {
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
                if (Bodies.isEmpty(ev.error.code)) {
                    JsonNode c = firstNode(rawErr, "code", "Code");
                    if (c != null) ev.error.code = c.asText("");
                }
                if (Bodies.isEmpty(ev.error.message)) {
                    JsonNode m = firstNode(rawErr, "message", "Message");
                    if (m != null) ev.error.message = m.asText("");
                }
            } else if (rawErr.isTextual()) {
                ev.error.message = rawErr.asText("");
            }
        }
        if (Bodies.isEmpty(ev.error.code)) {
            JsonNode rawCode = firstNode(payload, "code", "Code");
            if (rawCode != null) ev.error.code = rawCode.asText("");
        }
        if (Bodies.isEmpty(ev.error.message)) {
            JsonNode rawMsg = firstNode(payload, "message", "Message");
            if (rawMsg != null && rawMsg.isTextual()) {
                ev.error.message = rawMsg.asText("");
            }
        }
        if (V1Constants.V1_SESSION_CHAT_EVENT_FAILED.equals(ev.type) && Bodies.isEmpty(ev.error.message)) {
            JsonNode rawContent = firstNode(payload, "content", "Content");
            if (rawContent != null && rawContent.isTextual()) {
                ev.error.message = rawContent.asText("");
            }
        }
        if (V1Constants.V1_SESSION_CHAT_EVENT_FAILED.equals(ev.type)) {
            JsonNode rawPayload = payload.get("payload");
            if (rawPayload != null && rawPayload.isTextual()) {
                try {
                    JsonNode inner = MAPPER.readTree(rawPayload.asText(""));
                    if (inner != null && inner.isObject()) {
                        JsonNode turn = inner.get("turn");
                        if (turn != null && turn.isObject()) {
                            JsonNode turnErr = turn.get("error");
                            if (turnErr != null && turnErr.isObject()) {
                                if (Bodies.isEmpty(ev.error.message)) {
                                    JsonNode m = turnErr.get("message");
                                    if (m != null && m.isTextual()) ev.error.message = m.asText("");
                                }
                                if (Bodies.isEmpty(ev.error.code)) {
                                    JsonNode c = turnErr.get("code");
                                    if (c != null && c.isTextual()) ev.error.code = c.asText("");
                                }
                            }
                            if (Bodies.isEmpty(ev.error.message)) {
                                JsonNode status = turn.get("status");
                                if (status != null && status.isTextual() && "failed".equals(status.asText(""))) {
                                    JsonNode rawContent = firstNode(payload, "content", "Content");
                                    if (rawContent != null && rawContent.isTextual() && Bodies.isEmpty(ev.error.message)) {
                                        ev.error.message = rawContent.asText("");
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception ignore) {
                    // payload may not be JSON; that's fine.
                }
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
            if (!Bodies.isEmpty(msg.id) || !Bodies.isEmpty(msg.content)) {
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
