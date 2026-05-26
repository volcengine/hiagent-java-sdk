package com.volcengine.hibot.internal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volcengine.hibot.ApiException;

/** Decodes the TOP envelope into the wrapped Result and surfaces errors as ApiException. */
public final class ResponseDecoder {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private ResponseDecoder() {}

    public static ObjectMapper mapper() {
        return MAPPER;
    }

    public static <T> T decode(int statusCode, byte[] body, TypeReference<T> typeRef) {
        JsonNode root;
        try {
            root = MAPPER.readTree(body);
        } catch (Exception e) {
            if (statusCode >= 400) {
                throw new ApiException(statusCode, "", "", new String(body));
            }
            throw new RuntimeException("hibot: decode response: " + e.getMessage(), e);
        }
        JsonNode meta = root.path("ResponseMetadata");
        String requestId = meta.path("RequestId").asText("");
        JsonNode errNode = meta.path("Error");
        if (statusCode >= 400 || (errNode != null && !errNode.isMissingNode() && !errNode.isNull())) {
            String code = "";
            String message = "";
            if (errNode != null && !errNode.isMissingNode() && !errNode.isNull()) {
                code = errNode.path("Code").asText("");
                message = errNode.path("Message").asText("");
            }
            throw new ApiException(statusCode, requestId, code, message);
        }
        if (typeRef == null) {
            return null;
        }
        JsonNode result = root.path("Result");
        if (result == null || result.isMissingNode() || result.isNull()) {
            return null;
        }
        try {
            return MAPPER.convertValue(result, typeRef);
        } catch (Exception e) {
            throw new RuntimeException("hibot: decode result: " + e.getMessage(), e);
        }
    }
}
