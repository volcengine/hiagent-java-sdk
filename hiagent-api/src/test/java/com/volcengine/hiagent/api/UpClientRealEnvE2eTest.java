package com.volcengine.hiagent.api;

import com.volcengine.hiagent.api.model.DeleteRequest;
import com.volcengine.hiagent.api.model.DownloadKeyRequest;
import com.volcengine.hiagent.api.model.DownloadKeyResponse;
import com.volcengine.hiagent.api.model.DownloadRequest;
import com.volcengine.hiagent.api.model.LongLiveRequest;
import com.volcengine.hiagent.api.model.LongLiveResponse;
import com.volcengine.hiagent.api.model.UploadRawRequest;
import com.volcengine.hiagent.api.model.UploadRawResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Runs the whole UpClient flow against a real UP with anonymous access turned
 * off, so every call must pass UP's signature check. Skipped unless
 * {@code HIAGENT_UP_E2E_ENDPOINT} is set; also needs {@code HIAGENT_UP_E2E_AK}
 * and {@code HIAGENT_UP_E2E_SK}. {@code HIAGENT_UP_E2E_REGION} (default
 * {@code cn-north-1}) and {@code HIAGENT_PRODUCT_CODE} are optional.
 * Credentials are never printed.
 */
@EnabledIfEnvironmentVariable(named = "HIAGENT_UP_E2E_ENDPOINT", matches = ".+")
class UpClientRealEnvE2eTest {
    private static final int FILE_BYTES = 1024 * 1024;

    @TempDir
    Path tmp;

    @Test
    void uploadLongLiveDownloadKeyDownloadDelete() throws Exception {
        UpClient client = new UpClient(new ApiClient(
                requiredEnv("HIAGENT_UP_E2E_ENDPOINT"),
                requiredEnv("HIAGENT_UP_E2E_AK"),
                requiredEnv("HIAGENT_UP_E2E_SK"),
                envOrDefault("HIAGENT_UP_E2E_REGION", "cn-north-1"),
                System.getenv("HIAGENT_PRODUCT_CODE")));
        byte[] content = new byte[FILE_BYTES];
        new Random().nextBytes(content);
        content[0] = (byte) 0xff; // never valid UTF-8
        Path file = Files.write(tmp.resolve("e2e.bin"), content);
        String id = UUID.randomUUID().toString().replace("-", "");

        UploadRawRequest upload = new UploadRawRequest();
        upload.setId(id);
        upload.setExpire("1h");
        upload.setContentType("application/octet-stream");
        UploadRawResponse uploaded = client.uploadRaw(upload, file.toFile());
        assertEquals(UpSigner.sha256Hex(content), uploaded.getSha256());
        assertNotNull(uploaded.getPath());
        try {
            LongLiveRequest longLive = new LongLiveRequest();
            longLive.setPath(uploaded.getPath());
            longLive.setId(id);
            LongLiveResponse longLived = client.longLive(longLive);
            assertNotNull(longLived.getPath());

            DownloadKeyRequest downloadKey = new DownloadKeyRequest();
            downloadKey.setPath(longLived.getPath());
            DownloadKeyResponse key = client.downloadKey(downloadKey);
            assertNotNull(key.getKey());

            // UP checks the Key against the file named by Path, so both are required.
            Path saved = tmp.resolve("downloaded/e2e.bin");
            DownloadRequest download = new DownloadRequest();
            download.setKey(key.getKey());
            download.setPath(longLived.getPath());
            download.setSaveTo(saved.toString());
            client.download(download);
            assertArrayEquals(content, Files.readAllBytes(saved));
        } finally {
            // Runs even when a step above fails, so the test leaves nothing on the server.
            DeleteRequest delete = new DeleteRequest();
            delete.setId(id);
            delete.setSha256(uploaded.getSha256());
            client.delete(delete);
        }
    }

    private static String requiredEnv(String name) {
        String value = System.getenv(name);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException(name + " must be set");
        }
        return value.trim();
    }

    private static String envOrDefault(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.trim().isEmpty() ? fallback : value.trim();
    }
}
