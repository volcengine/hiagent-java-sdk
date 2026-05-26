package com.volcengine.hibot.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/** Decodes Server-Sent Events frames. Mirrors go/hibot/internal/stream/sse.go. */
public final class SseDecoder implements AutoCloseable {
    private final BufferedReader reader;

    public SseDecoder(InputStream input) {
        this.reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
    }

    public static final class Frame {
        public final String event;
        public final String data;

        public Frame(String event, String data) {
            this.event = event;
            this.data = data;
        }
    }

    /** Reads the next frame. Returns null on EOF. */
    public Frame next() throws IOException {
        String event = "";
        List<String> data = new ArrayList<>();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                if (!event.isEmpty() || !data.isEmpty()) {
                    return new Frame(event, String.join("\n", data));
                }
                return null;
            }
            // BufferedReader strips \r\n / \n already, but strip trailing \r just in case.
            if (!line.isEmpty() && line.charAt(line.length() - 1) == '\r') {
                line = line.substring(0, line.length() - 1);
            }
            if (line.isEmpty()) {
                if (event.isEmpty() && data.isEmpty()) {
                    continue;
                }
                return new Frame(event, String.join("\n", data));
            }
            if (line.startsWith(":")) {
                continue;
            }
            if (line.startsWith("event:")) {
                event = line.substring("event:".length()).trim();
                continue;
            }
            if (line.startsWith("data:")) {
                data.add(line.substring("data:".length()).trim());
            }
        }
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
