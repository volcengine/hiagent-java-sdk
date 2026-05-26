package com.volcengine.hibot.internal;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * VOLC Signature V4 (HMAC-SHA256) for TOP Open API.
 *
 * <p>Equivalent to volc-sdk-golang base.Credentials.Sign and follows the
 * specification documented at https://www.volcengine.com/docs/6438/69241.
 */
public final class Signer {
    public static final String ALGORITHM = "HMAC-SHA256";

    private static final DateTimeFormatter X_DATE = DateTimeFormatter
            .ofPattern("yyyyMMdd'T'HHmmss'Z'")
            .withZone(ZoneOffset.UTC);

    private final String accessKey;
    private final String secretKey;
    private final String region;
    private final String service;

    public Signer(String accessKey, String secretKey, String region, String service) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
        this.service = service;
    }

    /** Result of signing — contains the headers that must be set on the request. */
    public static final class Signed {
        public final Map<String, String> headers;
        public final String authorization;
        public final String xDate;
        public final String xContentSha256;

        Signed(Map<String, String> headers, String authorization, String xDate, String xContentSha256) {
            this.headers = headers;
            this.authorization = authorization;
            this.xDate = xDate;
            this.xContentSha256 = xContentSha256;
        }
    }

    /**
     * Sign an HTTP request.
     *
     * @param method  HTTP method (GET/POST/...)
     * @param uri     target URI (host + path + ?query)
     * @param headers user-supplied request headers; the host/x-date/x-content-sha256/content-type
     *                headers will be added automatically when missing
     * @param body    request body bytes (may be empty/null)
     */
    public Signed sign(String method, URI uri, Map<String, String> headers, byte[] body, Instant now) {
        if (body == null) {
            body = new byte[0];
        }
        if (now == null) {
            now = Instant.now();
        }
        String xDate = X_DATE.format(now);
        String shortDate = xDate.substring(0, 8);
        String contentSha256 = sha256Hex(body);

        Map<String, String> reqHeaders = new HashMap<>();
        if (headers != null) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                reqHeaders.put(e.getKey().toLowerCase(Locale.ROOT), e.getValue());
            }
        }
        reqHeaders.put("host", uri.getHost() + (uri.getPort() > 0 ? ":" + uri.getPort() : ""));
        reqHeaders.put("x-date", xDate);
        reqHeaders.put("x-content-sha256", contentSha256);
        reqHeaders.putIfAbsent("content-type", "application/json");

        // CanonicalQueryString
        String canonicalQuery = canonicalQueryString(uri.getRawQuery());

        // Signed headers list (sorted, lowercase)
        List<String> signedHeaderNames = new ArrayList<>();
        // Per spec, host & x-date must be present. We always include those four.
        signedHeaderNames.add("content-type");
        signedHeaderNames.add("host");
        signedHeaderNames.add("x-content-sha256");
        signedHeaderNames.add("x-date");
        Collections.sort(signedHeaderNames);
        StringBuilder canonicalHeaders = new StringBuilder();
        for (String name : signedHeaderNames) {
            String value = reqHeaders.get(name);
            if (value == null) {
                value = "";
            }
            canonicalHeaders.append(name).append(':').append(value.trim()).append('\n');
        }
        String signedHeaders = String.join(";", signedHeaderNames);

        String canonicalUri = uri.getRawPath();
        if (canonicalUri == null || canonicalUri.isEmpty()) {
            canonicalUri = "/";
        }

        String canonicalRequest =
                method.toUpperCase(Locale.ROOT) + '\n'
                + canonicalUri + '\n'
                + canonicalQuery + '\n'
                + canonicalHeaders + '\n'
                + signedHeaders + '\n'
                + contentSha256;

        String credentialScope = shortDate + '/' + region + '/' + service + '/' + "request";
        String stringToSign =
                ALGORITHM + '\n'
                + xDate + '\n'
                + credentialScope + '\n'
                + sha256Hex(canonicalRequest.getBytes(StandardCharsets.UTF_8));

        byte[] kDate = hmacSha256(secretKey.getBytes(StandardCharsets.UTF_8), shortDate);
        byte[] kRegion = hmacSha256(kDate, region);
        byte[] kService = hmacSha256(kRegion, service);
        byte[] kSigning = hmacSha256(kService, "request");
        String signature = toHex(hmacSha256(kSigning, stringToSign));

        String authorization = ALGORITHM
                + " Credential=" + accessKey + '/' + credentialScope
                + ", SignedHeaders=" + signedHeaders
                + ", Signature=" + signature;

        Map<String, String> outHeaders = new HashMap<>();
        outHeaders.put("Host", reqHeaders.get("host"));
        outHeaders.put("X-Date", xDate);
        outHeaders.put("X-Content-Sha256", contentSha256);
        outHeaders.put("Content-Type", reqHeaders.get("content-type"));
        outHeaders.put("Authorization", authorization);
        return new Signed(outHeaders, authorization, xDate, contentSha256);
    }

    /** Canonicalise an already URL-encoded query string from URI.getRawQuery(). */
    static String canonicalQueryString(String rawQuery) {
        if (rawQuery == null || rawQuery.isEmpty()) {
            return "";
        }
        // Parse into (key, value) pairs preserving raw-encoded forms; then re-encode using our scheme.
        TreeMap<String, List<String>> sorted = new TreeMap<>();
        for (String pair : rawQuery.split("&")) {
            if (pair.isEmpty()) {
                continue;
            }
            int eq = pair.indexOf('=');
            String k;
            String v;
            if (eq < 0) {
                k = decode(pair);
                v = "";
            } else {
                k = decode(pair.substring(0, eq));
                v = decode(pair.substring(eq + 1));
            }
            sorted.computeIfAbsent(k, x -> new ArrayList<>()).add(v);
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, List<String>> e : sorted.entrySet()) {
            String k = signEncode(e.getKey());
            for (String v : e.getValue()) {
                if (!first) sb.append('&');
                first = false;
                sb.append(k).append('=').append(signEncode(v));
            }
        }
        return sb.toString();
    }

    private static String decode(String s) {
        return java.net.URLDecoder.decode(s, StandardCharsets.UTF_8);
    }

    /** RFC3986-compliant encode used in canonical query string (space → %20). */
    static String signEncode(String s) {
        if (s == null || s.isEmpty()) return "";
        // URLEncoder encodes spaces as '+'; convert back to %20 and unencode "~".
        String encoded = URLEncoder.encode(s, StandardCharsets.UTF_8);
        return encoded.replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
    }

    static String sha256Hex(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return toHex(md.digest(bytes));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static byte[] hmacSha256(byte[] key, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, "HmacSHA256"));
            return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
