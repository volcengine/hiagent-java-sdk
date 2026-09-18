package com.volcengine.hiagent.api;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.MediaType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ProductCodeTest {
    @Test
    void configuredProductCodeIsAddedToLegacyRequest() throws Exception {
        final String[] value = new String[1];
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor((Interceptor.Chain chain) -> {
            value[0] = chain.request().header(ProductCode.HEADER_NAME);
            return new Response.Builder().request(chain.request()).protocol(okhttp3.Protocol.HTTP_1_1)
                    .code(200).message("OK").body(ResponseBody.create("\"ok\"", MediaType.parse("application/json"))).build();
        }).build();
        ExposedClient api = new ExposedClient("http://localhost", "key", client, " demo ");

        assertEquals("ok", api.call(Collections.emptyMap()));
        assertEquals("demo", value[0]);
    }

    @Test
    void explicitRequestHeaderOverridesConfiguredValue() throws Exception {
        final String[] value = new String[1];
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor((Interceptor.Chain chain) -> {
            value[0] = chain.request().header(ProductCode.HEADER_NAME);
            return new Response.Builder().request(chain.request()).protocol(okhttp3.Protocol.HTTP_1_1)
                    .code(200).message("OK").body(ResponseBody.create("\"ok\"", MediaType.parse("application/json"))).build();
        }).build();
        ExposedClient api = new ExposedClient("http://localhost", "key", client, "configured");

        assertEquals("ok", api.call(Collections.singletonMap(ProductCode.HEADER_NAME, "explicit")));
        assertEquals("explicit", value[0]);
    }

    @Test
    void blankIsAbsentAndControlCharacterIsRejected() {
        assertNull(ProductCode.normalize("  "));
        assertThrows(IllegalArgumentException.class, () -> ProductCode.normalize("bad\nvalue"));
    }

    private static final class ExposedClient extends BaseApiClient {
        ExposedClient(String baseUrl, String apiKey, OkHttpClient httpClient, String productCode) {
            super(baseUrl, apiKey, httpClient, productCode);
        }

        String call(java.util.Map<String, String> headers) throws Exception {
            return post("test", Collections.emptyMap(), String.class, headers);
        }
    }
}
