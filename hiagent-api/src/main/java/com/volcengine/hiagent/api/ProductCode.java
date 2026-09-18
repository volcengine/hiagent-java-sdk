package com.volcengine.hiagent.api;

import java.util.Map;

/** Shared validation and header handling for the optional product code. */
public final class ProductCode {
    public static final String HEADER_NAME = "X-Trace-Product-Code";

    private ProductCode() {}

    public static String normalize(String value) {
        if (value == null) return null;
        String normalized = value.trim();
        if (normalized.isEmpty()) return null;
        for (int i = 0; i < normalized.length(); i++) {
            if (Character.isISOControl(normalized.charAt(i))) {
                throw new IllegalArgumentException("productCode must not contain HTTP control characters");
            }
        }
        return normalized;
    }

    public static boolean containsIgnoreCase(Map<String, ?> headers, String name) {
        if (headers == null) return false;
        for (String key : headers.keySet()) {
            if (name.equalsIgnoreCase(key)) return true;
        }
        return false;
    }
}
