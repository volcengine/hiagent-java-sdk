package com.volcengine.hibot.internal;

/** Validation shared by Hibot configuration and request signing. */
public final class ProductCodeSupport {
    private ProductCodeSupport() {}

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
}
