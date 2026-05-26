package com.volcengine.hibot;

import com.fasterxml.jackson.databind.JsonNode;
import com.volcengine.hibot.testutil.MockHibotServer;
import com.volcengine.hibot.v1.BaseModels;
import com.volcengine.hibot.v1.types.V1UploadBlob;
import com.volcengine.hibot.v1.types.V1UploadBlobParams;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Smoke E2E covering the wiring (signing, /up sub-path, base models). */
class BasicE2eOfflineTest {
    private MockHibotServer server;
    private Hibot client;

    @BeforeEach
    void setUp() throws Exception {
        server = new MockHibotServer();
        client = server.newClient();
    }

    @AfterEach
    void tearDown() throws Exception {
        client.close();
        server.close();
    }

    @Test
    void uploadBlob_postsToUpSubpath_withFilenameQuery() throws IOException {
        server.onRequest((rec, ex) -> {
            try {
                Map<String, Object> result = new LinkedHashMap<>();
                result.put("BlobID", "blob-123");
                MockHibotServer.writeOk(ex, result);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        V1UploadBlobParams p = new V1UploadBlobParams("doc.txt", "text/plain");
        V1UploadBlob got = client.v1.uploads.uploadBlob(p, "hello".getBytes());
        assertEquals("blob-123", got.blobId);

        MockHibotServer.RecordedRequest rec = server.last();
        assertEquals("/up", rec.path);
        assertTrue(rec.query.contains("Action=UploadBlob"));
        assertTrue(rec.query.contains("Filename=doc.txt"));
        assertEquals("up", rec.topService);
        assertEquals("text/plain", rec.contentType);
        assertNotNull(rec.authorization);
        assertTrue(rec.authorization.contains("Credential=AK/"));
    }

    @Test
    void baseModels_has100EntriesAndConstantsAlign() {
        assertEquals(100, BaseModels.ALL.size());
        assertEquals("text-generation", BaseModels.BASE_MODEL_TYPE_TEXT_GENERATION);
        assertEquals("volcengine", BaseModels.BASE_MODEL_PROVIDER_VOLCENGINE);
        // Sanity check a few well-known entries.
        boolean hasDoubaoSeedPro = BaseModels.ALL.stream().anyMatch(b ->
                "volcengine".equals(b.provider) && "doubao-seed-2-0-pro-260215".equals(b.modelName));
        assertTrue(hasDoubaoSeedPro);
    }

    @Test
    void requestInjectsConfiguredWorkspaceId() throws Exception {
        server.onRequest((rec, ex) -> {
            try {
                MockHibotServer.writeOk(ex, new LinkedHashMap<>());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        com.volcengine.hibot.v1.types.V1AgentListParams p = new com.volcengine.hibot.v1.types.V1AgentListParams();
        client.v1.agents.list(p);
        JsonNode body = server.last().bodyJson();
        // Workspace injected by RequestExecutor.
        assertEquals("ws-test", body.get("WorkspaceID").asText());
    }

    @Test
    void config_validatesRequiredFields() {
        assertThrowsIllegal(() -> HibotConfig.builder().build());
        assertThrowsIllegal(() -> HibotConfig.builder().endpoint("http://x").build());
        assertThrowsIllegal(() -> HibotConfig.builder().endpoint("http://x").accessKey("a").build());
        assertThrowsIllegal(() -> HibotConfig.builder().endpoint("http://x").accessKey("a").secretKey("s").build());
        // OK
        HibotConfig cfg = HibotConfig.builder().endpoint("http://x").accessKey("a").secretKey("s").workspaceId("w").build();
        assertEquals("cn-north-1", cfg.region());
        assertEquals("hibot-server", cfg.serverService());
    }

    private static void assertThrowsIllegal(Runnable r) {
        try {
            r.run();
        } catch (IllegalArgumentException e) {
            return;
        }
        org.junit.jupiter.api.Assertions.fail("expected IllegalArgumentException");
    }
}
