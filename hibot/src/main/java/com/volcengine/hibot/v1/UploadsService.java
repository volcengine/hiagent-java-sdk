package com.volcengine.hibot.v1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.internal.RequestExecutor;
import com.volcengine.hibot.internal.Versions;
import com.volcengine.hibot.v1.types.V1UploadBlob;
import com.volcengine.hibot.v1.types.V1UploadBlobParams;

import java.util.LinkedHashMap;
import java.util.Map;

/** Mirrors go/hibot/v1/uploads.go. */
public final class UploadsService {
    private final RequestExecutor requester;
    private final HibotConfig config;

    public UploadsService(RequestExecutor requester, HibotConfig config) {
        this.requester = requester;
        this.config = config;
    }

    public V1UploadBlob uploadBlob(V1UploadBlobParams params, byte[] body) {
        if (params == null || params.filename == null || params.filename.isEmpty()) {
            throw new IllegalArgumentException("hibot: upload filename is required");
        }
        Map<String, String> query = new LinkedHashMap<>();
        query.put("Filename", params.filename);
        V1UploadBlob result = requester.doRawAction(
                new RequestExecutor.Action(config.upService(), Versions.UP, "UploadBlob", null),
                body == null ? new byte[0] : body,
                params.contentType,
                query,
                new TypeReference<V1UploadBlob>() {});
        if (result == null || result.blobId == null || result.blobId.isEmpty()) {
            throw new IllegalStateException("hibot: upload blob response missing BlobID");
        }
        return result;
    }
}
