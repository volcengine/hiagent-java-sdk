package com.volcengine.hibot.internal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.hibot.HibotConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductCodeRequestTest {
    @Test
    void configProductCodeReachesSignedHttpRequest() throws Exception {
        HibotConfig config = HibotConfig.builder().endpoint("https://example.com")
                .accessKey("ak").secretKey("sk").workspaceId("ws")
                .productCode("product-a").build();
        RequestExecutor executor = new RequestExecutor(config);
        Method method = RequestExecutor.class.getDeclaredMethod("buildHttpRequest", RequestExecutor.Action.class,
                byte[].class, String.class, java.util.Map.class);
        method.setAccessible(true);
        Request request = (Request) method.invoke(executor,
                new RequestExecutor.Action("observe", "2025-05-01", "Test", Collections.emptyMap()),
                new byte[0], "application/json", null);

        assertEquals("product-a", request.header("X-Trace-Product-Code"));
        assertEquals("POST", request.method());
    }
}
