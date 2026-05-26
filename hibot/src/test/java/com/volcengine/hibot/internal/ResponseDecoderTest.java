package com.volcengine.hibot.internal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.hibot.ApiException;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResponseDecoderTest {
    @Test
    void decodesResultEnvelope() {
        String body = "{\"ResponseMetadata\":{\"RequestId\":\"r1\"},\"Result\":{\"ID\":\"abc\",\"Name\":\"foo\"}}";
        Map<String, Object> got = ResponseDecoder.decode(200, body.getBytes(),
                new TypeReference<Map<String, Object>>() {});
        assertEquals("abc", got.get("ID"));
        assertEquals("foo", got.get("Name"));
    }

    @Test
    void emptyResultReturnsNull() {
        String body = "{\"ResponseMetadata\":{\"RequestId\":\"r1\"}}";
        Map<String, Object> got = ResponseDecoder.decode(200, body.getBytes(),
                new TypeReference<Map<String, Object>>() {});
        assertNull(got);
    }

    @Test
    void surfacesApiErrorOnNon2xx() {
        String body = "{\"ResponseMetadata\":{\"RequestId\":\"r-err\",\"Error\":{\"Code\":\"InvalidArg\",\"Message\":\"bad\"}}}";
        ApiException ex = assertThrows(ApiException.class, () ->
                ResponseDecoder.decode(400, body.getBytes(), new TypeReference<LinkedHashMap<String, Object>>() {}));
        assertEquals(400, ex.statusCode());
        assertEquals("r-err", ex.requestId());
        assertEquals("InvalidArg", ex.code());
        assertTrue(ex.getMessage().contains("bad"));
    }

    @Test
    void surfacesEmbeddedErrorOn200() {
        String body = "{\"ResponseMetadata\":{\"RequestId\":\"r-2\",\"Error\":{\"Code\":\"AccessDenied\",\"Message\":\"nope\"}}}";
        ApiException ex = assertThrows(ApiException.class, () ->
                ResponseDecoder.decode(200, body.getBytes(), new TypeReference<Map<String, Object>>() {}));
        assertEquals("AccessDenied", ex.code());
    }

    @Test
    void unparseableNon2xxStillThrowsApiException() {
        ApiException ex = assertThrows(ApiException.class, () ->
                ResponseDecoder.decode(503, "not json".getBytes(), new TypeReference<Map<String, Object>>() {}));
        assertEquals(503, ex.statusCode());
    }
}
