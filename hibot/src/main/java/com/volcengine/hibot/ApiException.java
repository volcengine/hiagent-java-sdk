package com.volcengine.hibot;

/** Hibot SDK API exception. */
public class ApiException extends RuntimeException {
    private final int statusCode;
    private final String requestId;
    private final String code;

    public ApiException(int statusCode, String requestId, String code, String message) {
        super(buildMessage(statusCode, requestId, code, message));
        this.statusCode = statusCode;
        this.requestId = requestId == null ? "" : requestId;
        this.code = code == null ? "" : code;
    }

    public int statusCode() {
        return statusCode;
    }

    public String requestId() {
        return requestId;
    }

    public String code() {
        return code;
    }

    private static String buildMessage(int statusCode, String requestId, String code, String message) {
        if ((code == null || code.isEmpty()) && (message == null || message.isEmpty())) {
            return String.format("hibot: api error status=%d request_id=%s",
                    statusCode, requestId == null ? "" : requestId);
        }
        return String.format("hibot: api error status=%d request_id=%s code=%s message=%s",
                statusCode,
                requestId == null ? "" : requestId,
                code == null ? "" : code,
                message == null ? "" : message);
    }
}
