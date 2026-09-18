package com.volcengine.hibot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductCodeConfigTest {
    @Test
    void productCodeIsTrimmedAndOptional() {
        HibotConfig config = HibotConfig.builder().endpoint("https://example.com")
                .accessKey("ak").secretKey("sk").workspaceId("ws")
                .productCode("  product-a  ").build();
        assertEquals("product-a", config.productCode());
        assertNull(HibotConfig.builder().endpoint("https://example.com")
                .accessKey("ak").secretKey("sk").workspaceId("ws").productCode(" ").build().productCode());
    }

    @Test
    void controlCharacterIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> HibotConfig.builder()
                .endpoint("https://example.com").accessKey("ak").secretKey("sk").workspaceId("ws")
                .productCode("product\r\nX").build());
    }
}
