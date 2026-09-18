package com.volcengine.hiagent.api;

import com.volcengine.interceptor.InterceptorContext;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TopProductCodeTest {
    @Test
    void productCodeIsAddedBeforeGeneratedTopBuildCall() throws Exception {
        CapturingApiClient client = new CapturingApiClient("example.com", "ak", "sk", "cn-north-1", "maas");
        client.buildCall("/test", "POST", new ArrayList<com.volcengine.Pair>(),
                new ArrayList<com.volcengine.Pair>(), null, new HashMap<String, String>(),
                new HashMap<String, Object>(), new String[0], null);
        assertEquals("maas", client.headers.get(ProductCode.HEADER_NAME));
    }

    @Test
    void explicitGeneratedHeaderWins() throws Exception {
        CapturingApiClient client = new CapturingApiClient("example.com", "ak", "sk", "cn-north-1", "configured");
        Map<String, String> headers = new HashMap<>();
        headers.put(ProductCode.HEADER_NAME, "explicit");
        client.buildCall("/test", "POST", new ArrayList<com.volcengine.Pair>(),
                new ArrayList<com.volcengine.Pair>(), null, headers,
                new HashMap<String, Object>(), new String[0], null);
        assertEquals("explicit", client.headers.get(ProductCode.HEADER_NAME));
    }

    private static final class CapturingApiClient extends ApiClient {
        private Map<String, String> headers;

        CapturingApiClient(String endpoint, String ak, String sk, String region, String productCode) {
            super(endpoint, ak, sk, region, productCode);
        }

        @Override
        public InterceptorContext buildCall(String path, String method, List<com.volcengine.Pair> queryParams,
                List<com.volcengine.Pair> collectionFormats, Object body, Map<String, String> headerParams,
                Map<String, Object> formParams, String[] authNames,
                com.volcengine.ProgressRequestBody.ProgressRequestListener listener, boolean... async)
                throws com.volcengine.ApiException {
            this.headers = headerParams;
            return super.buildCall(path, method, queryParams, collectionFormats, body, headerParams,
                    formParams, authNames, listener, async);
        }
    }
}
