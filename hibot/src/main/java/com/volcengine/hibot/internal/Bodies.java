package com.volcengine.hibot.internal;

import java.util.LinkedHashMap;
import java.util.Map;

/** Small helpers for building TOP request bodies. */
public final class Bodies {
    private Bodies() {}

    public static Map<String, Object> map() {
        return new LinkedHashMap<>();
    }

    public static void putIfNotEmpty(Map<String, Object> m, String key, String value) {
        if (value != null && !value.isEmpty()) {
            m.put(key, value);
        }
    }

    public static void putIfNotNull(Map<String, Object> m, String key, Object value) {
        if (value != null) {
            m.put(key, value);
        }
    }
}
