package com.volcengine.hibot.internal;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SseDecoderTest {
    @Test
    void decodesMultipleFrames() throws Exception {
        String stream = "event: delta\n"
                + "data: {\"text\":\"hello\"}\n"
                + "\n"
                + "event: completed\n"
                + "data: {\"id\":\"m1\"}\n"
                + "\n";
        SseDecoder dec = new SseDecoder(new ByteArrayInputStream(stream.getBytes(StandardCharsets.UTF_8)));
        SseDecoder.Frame a = dec.next();
        assertEquals("delta", a.event);
        assertEquals("{\"text\":\"hello\"}", a.data);
        SseDecoder.Frame b = dec.next();
        assertEquals("completed", b.event);
        assertEquals("{\"id\":\"m1\"}", b.data);
        assertNull(dec.next());
    }

    @Test
    void supportsCrlfLineEndings() throws Exception {
        String stream = "event: foo\r\ndata: x\r\n\r\n";
        SseDecoder dec = new SseDecoder(new ByteArrayInputStream(stream.getBytes(StandardCharsets.UTF_8)));
        SseDecoder.Frame f = dec.next();
        assertEquals("foo", f.event);
        assertEquals("x", f.data);
    }

    @Test
    void multilineDataConcatenated() throws Exception {
        String stream = "event: delta\ndata: line1\ndata: line2\n\n";
        SseDecoder dec = new SseDecoder(new ByteArrayInputStream(stream.getBytes(StandardCharsets.UTF_8)));
        SseDecoder.Frame f = dec.next();
        assertEquals("line1\nline2", f.data);
    }

    @Test
    void ignoresCommentLines() throws Exception {
        String stream = ": ping\nevent: e\ndata: d\n\n";
        SseDecoder dec = new SseDecoder(new ByteArrayInputStream(stream.getBytes(StandardCharsets.UTF_8)));
        SseDecoder.Frame f = dec.next();
        assertEquals("e", f.event);
        assertEquals("d", f.data);
    }
}
