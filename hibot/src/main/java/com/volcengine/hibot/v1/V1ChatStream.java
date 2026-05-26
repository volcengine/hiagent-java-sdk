package com.volcengine.hibot.v1;

import com.volcengine.hibot.internal.SseDecoder;
import com.volcengine.hibot.v1.types.V1Message;
import com.volcengine.hibot.v1.types.V1SessionChatEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Streaming chat result. Mirrors go/hibot/v1/stream.go (V1SessionChatStream).
 *
 * <p>Use either {@link #next()} / {@link #current()} in a while loop or iterate via for-each.
 * Always close — backed by an SSE response.
 */
public final class V1ChatStream implements AutoCloseable, Iterable<V1SessionChatEvent> {
    HttpResponse<InputStream> response;
    SseDecoder decoder;
    V1SessionChatEvent current = new V1SessionChatEvent();
    V1Message finalMsg;
    Throwable err;
    boolean closed;

    V1ChatStream() {}

    /** Reads the next event. Returns false on EOF or error. */
    public boolean next() {
        if (err != null || decoder == null) {
            return false;
        }
        SseDecoder.Frame frame;
        try {
            frame = decoder.next();
        } catch (IOException e) {
            err = e;
            return false;
        }
        if (frame == null) {
            return false;
        }
        try {
            V1SessionChatEvent event = SessionsService.decodeChatEvent(frame.event, frame.data);
            current = event;
            if (event.message != null) {
                finalMsg = event.message;
            }
            return true;
        } catch (RuntimeException e) {
            err = e;
            return false;
        }
    }

    public V1SessionChatEvent current() {
        return current;
    }

    public Throwable err() {
        return err;
    }

    @Override
    public void close() {
        if (closed) return;
        closed = true;
        if (decoder != null) {
            try {
                decoder.close();
            } catch (IOException ignore) {
                // best-effort
            }
        }
    }

    /** Returns the final V1Message (after stream completion). */
    public V1Message finalMessage() {
        if (err != null) {
            if (err instanceof RuntimeException) throw (RuntimeException) err;
            throw new RuntimeException(err);
        }
        if (finalMsg != null) return finalMsg;
        if (current != null && current.message != null) return current.message;
        throw new IllegalStateException("hibot: final message is not available");
    }

    /**
     * Consume the entire stream, accumulating delta text. Returns a V1Message whose Content is
     * either the server-supplied final content or the concatenated delta text. Mirrors
     * go/hibot/v1/stream.go (Accumulate).
     */
    public V1Message accumulate() {
        StringBuilder buf = new StringBuilder();
        V1Message finalEv = null;
        while (next()) {
            V1SessionChatEvent event = current;
            String type = event.type;
            if (V1Constants.V1_SESSION_CHAT_EVENT_FAILED.equals(type)) {
                String msg = event.error == null ? "" : event.error.message;
                if (msg == null || msg.isEmpty()) {
                    msg = event.error == null ? null : event.error.code;
                }
                if (msg == null || msg.isEmpty()) {
                    msg = "unknown error";
                }
                throw new IllegalStateException("hibot: chat failed: " + msg);
            } else if (V1Constants.V1_SESSION_CHAT_EVENT_DELTA.equals(type)) {
                if (event.delta != null && event.delta.text != null && !event.delta.text.isEmpty()) {
                    buf.append(event.delta.text);
                }
            } else if (V1Constants.V1_SESSION_CHAT_EVENT_COMPLETED.equals(type)) {
                if (event.message != null) {
                    finalEv = event.message;
                }
            }
        }
        if (err != null) {
            if (err instanceof RuntimeException) throw (RuntimeException) err;
            throw new RuntimeException(err);
        }
        if (finalEv == null) finalEv = finalMsg;
        if (finalEv == null) {
            if (buf.length() == 0) {
                throw new IllegalStateException("hibot: final message is not available");
            }
            V1Message m = new V1Message();
            m.role = "assistant";
            m.content = buf.toString();
            return m;
        }
        V1Message out = new V1Message();
        out.id = finalEv.id;
        out.sessionId = finalEv.sessionId;
        out.runId = finalEv.runId;
        out.role = finalEv.role;
        out.content = finalEv.content;
        out.visibility = finalEv.visibility;
        out.createdAt = finalEv.createdAt;
        out.files = finalEv.files;
        if ((out.content == null || out.content.isEmpty()) && buf.length() > 0) {
            out.content = buf.toString();
        }
        return out;
    }

    @Override
    public Iterator<V1SessionChatEvent> iterator() {
        return new Iterator<V1SessionChatEvent>() {
            private boolean fetched = false;
            private boolean has = false;

            @Override
            public boolean hasNext() {
                if (!fetched) {
                    has = V1ChatStream.this.next();
                    fetched = true;
                }
                return has;
            }

            @Override
            public V1SessionChatEvent next() {
                if (!hasNext()) throw new NoSuchElementException();
                fetched = false;
                return current;
            }
        };
    }
}
