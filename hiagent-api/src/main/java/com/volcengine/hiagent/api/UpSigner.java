// Copyright (c) 2024 Bytedance Ltd. and/or its affiliates
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.volcengine.hiagent.api;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Signs UP requests the way UP verifies them: UP rebuilds the canonical request
 * from what it received, so this signer only ever reads the final
 * {@link Request} that is about to be sent and never takes signing material
 * from anywhere else.
 *
 * <p>The canonical request uses the decoded URL path, every query parameter
 * (decoded, sorted, re-encoded per RFC 3986 with {@code %20} for spaces), the
 * headers {@code x-content-sha256} and {@code x-date}, and the value of
 * {@code X-Content-Sha256} as the payload hash. {@code host} is deliberately not
 * signed, so a gateway that rewrites the Host header does not break
 * verification.
 */
final class UpSigner {
    static final String SERVICE = "up";
    static final String HEADER_DATE = "X-Date";
    static final String HEADER_CONTENT_SHA256 = "X-Content-Sha256";
    static final String HEADER_SECURITY_TOKEN = "X-Security-Token";
    static final String HEADER_AUTHORIZATION = "Authorization";

    private static final String ALGORITHM = "HMAC-SHA256";
    private static final String SIGNED_HEADERS = "x-content-sha256;x-date";
    private static final String HMAC = "HmacSHA256";
    private static final int HASH_BUFFER_BYTES = 64 * 1024;
    private static final int SHORT_DATE_LENGTH = 8;
    private static final DateTimeFormatter X_DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'").withZone(ZoneOffset.UTC);

    private final String accessKey;
    private final String secretKey;
    private final String sessionToken;
    private final String region;

    /**
     * Messages never contain the credential values: OkHttp would otherwise
     * reject a bad header with an exception that quotes the whole value.
     */
    UpSigner(String accessKey, String secretKey, String sessionToken, String region) {
        if (isBlank(accessKey) || isBlank(secretKey)) {
            throw new IllegalArgumentException("AccessKey and SecretKey must be set on the ApiClient credentials");
        }
        if (isBlank(region)) {
            throw new IllegalArgumentException("Region must be set on the ApiClient");
        }
        if (hasControlCharacter(accessKey)) {
            throw new IllegalArgumentException("AccessKey contains control characters");
        }
        String token = isBlank(sessionToken) ? null : sessionToken.trim();
        if (token != null && hasControlCharacter(token)) {
            throw new IllegalArgumentException("Session token contains control characters");
        }
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.sessionToken = token;
        this.region = region;
    }

    /**
     * Returns a copy of {@code request} carrying {@code X-Date},
     * {@code Authorization} and, for STS credentials, {@code X-Security-Token}.
     *
     * @throws IllegalArgumentException if the request has no {@code X-Content-Sha256} header
     */
    Request sign(Request request, Instant now) {
        String payloadHash = request.header(HEADER_CONTENT_SHA256);
        if (isBlank(payloadHash)) {
            throw new IllegalArgumentException(HEADER_CONTENT_SHA256 + " must be set before signing");
        }
        String xDate = X_DATE_FORMAT.format(now);
        String canonical = canonicalRequest(request.method(), request.httpUrl(), payloadHash, xDate);
        String shortDate = xDate.substring(0, SHORT_DATE_LENGTH);
        String signature = toHex(hmac(signingKey(shortDate), stringToSign(xDate, region, canonical)));
        String authorization = ALGORITHM + " Credential=" + accessKey + "/" + credentialScope(shortDate, region)
                + ", SignedHeaders=" + SIGNED_HEADERS + ", Signature=" + signature;

        Request.Builder signed = request.newBuilder()
                .header(HEADER_DATE, xDate)
                .header(HEADER_AUTHORIZATION, authorization);
        if (sessionToken != null) {
            signed.header(HEADER_SECURITY_TOKEN, sessionToken);
        }
        return signed.build();
    }

    static String canonicalRequest(String method, HttpUrl url, String payloadHash, String xDate) {
        String path = url.uri().getPath();
        if (path == null || path.isEmpty()) {
            path = "/";
        }
        // The server's HTTP parser strips surrounding whitespace from header values.
        String hash = payloadHash.trim();
        return method.toUpperCase(Locale.ROOT) + '\n'
                + path + '\n'
                + canonicalQueryString(url.encodedQuery()) + '\n'
                + "x-content-sha256:" + hash + '\n'
                + "x-date:" + xDate + '\n'
                + '\n'
                + SIGNED_HEADERS + '\n'
                + hash;
    }

    static String stringToSign(String xDate, String region, String canonicalRequest) {
        return ALGORITHM + '\n'
                + xDate + '\n'
                + credentialScope(xDate.substring(0, SHORT_DATE_LENGTH), region) + '\n'
                + sha256Hex(canonicalRequest.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Canonicalises an already URL-encoded query string: decode every pair,
     * sort by name (values keep their order), re-encode with {@link #signEncode}.
     * Ported from hibot's {@code Signer#canonicalQueryString}.
     */
    static String canonicalQueryString(String rawQuery) {
        if (rawQuery == null || rawQuery.isEmpty()) {
            return "";
        }
        TreeMap<String, List<String>> sorted = new TreeMap<>();
        for (String pair : rawQuery.split("&")) {
            if (pair.isEmpty()) {
                continue;
            }
            int eq = pair.indexOf('=');
            String name = decode(eq < 0 ? pair : pair.substring(0, eq));
            String value = eq < 0 ? "" : decode(pair.substring(eq + 1));
            sorted.computeIfAbsent(name, k -> new ArrayList<>()).add(value);
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : sorted.entrySet()) {
            String name = signEncode(entry.getKey());
            for (String value : entry.getValue()) {
                if (sb.length() > 0) {
                    sb.append('&');
                }
                sb.append(name).append('=').append(signEncode(value));
            }
        }
        return sb.toString();
    }

    /**
     * RFC 3986 percent-encoding: only {@code A-Z a-z 0-9 - _ . ~} stay literal,
     * spaces become {@code %20}. Ported from hibot's {@code Signer#signEncode}.
     */
    static String signEncode(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        try {
            return URLEncoder.encode(s, StandardCharsets.UTF_8.name())
                    .replace("+", "%20")
                    .replace("*", "%2A")
                    .replace("%7E", "~");
        } catch (IOException e) {
            throw new IllegalStateException("UTF-8 encoding is not supported", e);
        }
    }

    static String sha256Hex(byte[] bytes) {
        return toHex(newSha256().digest(bytes));
    }

    /** Hashes a file in fixed-size chunks so large uploads are never held in memory. */
    static String sha256Hex(File file) throws IOException {
        MessageDigest digest = newSha256();
        byte[] buffer = new byte[HASH_BUFFER_BYTES];
        try (InputStream in = new FileInputStream(file)) {
            int read;
            while ((read = in.read(buffer)) != -1) {
                digest.update(buffer, 0, read);
            }
        }
        return toHex(digest.digest());
    }

    private byte[] signingKey(String shortDate) {
        byte[] kDate = hmac(secretKey.getBytes(StandardCharsets.UTF_8), shortDate);
        byte[] kRegion = hmac(kDate, region);
        byte[] kService = hmac(kRegion, SERVICE);
        return hmac(kService, "request");
    }

    private static String credentialScope(String shortDate, String region) {
        return shortDate + '/' + region + '/' + SERVICE + "/request";
    }

    private static String decode(String s) {
        try {
            return URLDecoder.decode(s, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            throw new IllegalStateException("UTF-8 encoding is not supported", e);
        }
    }

    private static byte[] hmac(byte[] key, String data) {
        try {
            Mac mac = Mac.getInstance(HMAC);
            mac.init(new SecretKeySpec(key, HMAC));
            return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IllegalStateException(HMAC + " is not available", e);
        }
    }

    private static MessageDigest newSha256() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(Character.forDigit((b >> 4) & 0xf, 16)).append(Character.forDigit(b & 0xf, 16));
        }
        return sb.toString();
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static boolean hasControlCharacter(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isISOControl(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
