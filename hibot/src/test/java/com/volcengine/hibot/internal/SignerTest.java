package com.volcengine.hibot.internal;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SignerTest {
    @Test
    void signedHeadersIncludeRequiredFields() {
        Signer signer = new Signer("AKID", "SECRET", "cn-north-1", "hibot-server");
        URI uri = URI.create("https://example.com/?Action=Foo&Version=2026-04-23");
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("content-type", "application/json");
        Signer.Signed signed = signer.sign("POST", uri, headers, "hello".getBytes(), Instant.parse("2026-05-24T10:00:00Z"));

        assertNotNull(signed.authorization);
        assertTrue(signed.authorization.startsWith("HMAC-SHA256 Credential=AKID/20260524/cn-north-1/hibot-server/request"));
        assertTrue(signed.authorization.contains("SignedHeaders=content-type;host;x-content-sha256;x-date"));
        assertTrue(signed.authorization.contains("Signature="));
        assertEquals("20260524T100000Z", signed.xDate);
        // sha256("hello")
        assertEquals("2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824", signed.xContentSha256);
    }

    @Test
    void signatureDeterministicForSameInput() {
        Signer signer = new Signer("AK", "SK", "cn-north-1", "hibot-server");
        URI uri = URI.create("https://example.com/?Action=Foo&Version=v1");
        Instant now = Instant.parse("2026-01-01T00:00:00Z");
        Signer.Signed a = signer.sign("POST", uri, new LinkedHashMap<>(), "{}".getBytes(), now);
        Signer.Signed b = signer.sign("POST", uri, new LinkedHashMap<>(), "{}".getBytes(), now);
        assertEquals(a.authorization, b.authorization);
    }

    @Test
    void canonicalQueryStringSortsKeys() {
        String s = Signer.canonicalQueryString("Version=v1&Action=Foo&Bar=1");
        assertEquals("Action=Foo&Bar=1&Version=v1", s);
    }

    @Test
    void signEncodeUsesPercent20() {
        assertEquals("hello%20world", Signer.signEncode("hello world"));
        assertEquals("a~b", Signer.signEncode("a~b"));
        assertEquals("%2A", Signer.signEncode("*"));
    }
}
