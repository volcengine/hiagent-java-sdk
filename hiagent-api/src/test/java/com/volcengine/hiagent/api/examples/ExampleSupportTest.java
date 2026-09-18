package com.volcengine.hiagent.api.examples;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExampleSupportTest {
    @Test
    void topExampleClientUsesConfiguredProductCode() {
        com.volcengine.hiagent.api.ApiClient client = ExampleSupport.topApiClient(
                "https://example.com", "ak", "sk", "cn-north-1", " example-product ");

        assertEquals("example-product", client.getProductCode());
    }
}
