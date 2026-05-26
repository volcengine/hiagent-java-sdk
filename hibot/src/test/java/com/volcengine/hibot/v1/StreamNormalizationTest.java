package com.volcengine.hibot.v1;

import com.volcengine.hibot.v1.types.V1SessionChatEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StreamNormalizationTest {
    @Test
    void normalizeChatEventName_aliases() {
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_DELTA,
                SessionsService.normalizeChatEventName("message.chunk"));
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_DELTA,
                SessionsService.normalizeChatEventName("message_delta"));
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_DELTA,
                SessionsService.normalizeChatEventName("message_chunk"));
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_COMPLETED,
                SessionsService.normalizeChatEventName("message.completed"));
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_COMPLETED,
                SessionsService.normalizeChatEventName("message_completed"));
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_COMPLETED,
                SessionsService.normalizeChatEventName("run_completed"));
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_FAILED,
                SessionsService.normalizeChatEventName("message.failed"));
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_FAILED,
                SessionsService.normalizeChatEventName("message_failed"));
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_FAILED,
                SessionsService.normalizeChatEventName("run_failed"));
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_TOOL_START,
                SessionsService.normalizeChatEventName("tool_started"));
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_TOOL_COMPLETE,
                SessionsService.normalizeChatEventName("tool_completed"));
        assertEquals("custom_event",
                SessionsService.normalizeChatEventName("custom_event"));
    }

    @Test
    void decodeChatEvent_pullsTextDelta() {
        V1SessionChatEvent e = SessionsService.decodeChatEvent("message_delta",
                "{\"delta\":{\"text\":\"hi\"},\"request_id\":\"r-1\"}");
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_DELTA, e.type);
        assertEquals("r-1", e.requestId);
        assertEquals("hi", e.delta.text);
    }

    @Test
    void decodeChatEvent_fallbackToTextField() {
        V1SessionChatEvent e = SessionsService.decodeChatEvent("message.chunk",
                "{\"text\":\"world\",\"RequestID\":\"r-2\"}");
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_DELTA, e.type);
        assertEquals("r-2", e.requestId);
        assertEquals("world", e.delta.text);
    }

    @Test
    void decodeChatEvent_completedSynthesizesMessage() {
        V1SessionChatEvent e = SessionsService.decodeChatEvent("run_completed",
                "{\"message_id\":\"m-9\",\"content\":\"final\"}");
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_COMPLETED, e.type);
        assertNotNull(e.message);
        assertEquals("m-9", e.message.id);
        assertEquals("final", e.message.content);
    }

    @Test
    void decodeChatEvent_failedExtractsErrorString() {
        V1SessionChatEvent e = SessionsService.decodeChatEvent("message.failed",
                "{\"error\":\"boom\",\"code\":\"E001\"}");
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_FAILED, e.type);
        assertEquals("boom", e.error.message);
        assertEquals("E001", e.error.code);
    }

    @Test
    void decodeChatEvent_failedExtractsErrorObject() {
        V1SessionChatEvent e = SessionsService.decodeChatEvent("run_failed",
                "{\"error\":{\"Code\":\"E\",\"Message\":\"m\"}}");
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_FAILED, e.type);
        assertEquals("E", e.error.code);
        assertEquals("m", e.error.message);
    }

    @Test
    void decodeChatEvent_typeFromPayload() {
        V1SessionChatEvent e = SessionsService.decodeChatEvent("",
                "{\"type\":\"message.chunk\",\"delta\":{\"text\":\"x\"}}");
        assertEquals(V1Constants.V1_SESSION_CHAT_EVENT_DELTA, e.type);
        assertEquals("x", e.delta.text);
    }
}
