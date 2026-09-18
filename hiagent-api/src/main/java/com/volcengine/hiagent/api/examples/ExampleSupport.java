package com.volcengine.hiagent.api.examples;

import com.volcengine.hiagent.api.ApiClient;

/** Shared environment-based setup for runnable examples. */
final class ExampleSupport {
    private ExampleSupport() {
    }

    static ApiClient topApiClient() {
        return topApiClient(
                System.getenv("HIAGENT_TOP_ENDPOINT"),
                System.getenv("VOLC_ACCESSKEY"),
                System.getenv("VOLC_SECRETKEY"),
                System.getenv("HIAGENT_TOP_REGION"),
                System.getenv("HIAGENT_PRODUCT_CODE"));
    }

    static ApiClient topApiClient(String endpoint, String accessKey, String secretKey,
            String region, String productCode) {
        return new ApiClient(endpoint, accessKey, secretKey, region, productCode);
    }
}
