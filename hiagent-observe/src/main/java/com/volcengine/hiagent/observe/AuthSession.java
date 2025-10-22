package com.volcengine.hiagent.observe;

import com.volcengine.ApiException;
import com.volcengine.hiagent.api.ApiClient;
import com.volcengine.hiagent.api.ObserveClient;
import com.volcengine.hiagent.api.model.CreateApiTokenRequest;
import com.volcengine.hiagent.api.model.CreateApiTokenResponse;
import okhttp3.*;

import java.io.IOException;
import java.util.logging.Logger;

public class AuthSession {
    private static final Logger logger = Logger.getLogger(AuthSession.class.getName());

    private final String workspaceId;
    private final String appId;
    private final ObserveClient observeClient;
    private final OkHttpClient httpClient;

    private String token;
    private long expiresAt = 0;

    public AuthSession(String endpoint, String ak, String sk, String workspaceId, String appId) {
        this.workspaceId = workspaceId;
        this.appId = appId;

        ApiClient apiClient = new ApiClient(endpoint, ak, sk, "cn-north-1");

        this.observeClient = new ObserveClient(apiClient);

        this.httpClient = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor())
                .build();
    }

    public String getToken() throws ApiException {
        CreateApiTokenRequest request = new CreateApiTokenRequest();
        request.setWorkspaceID(workspaceId);
        request.setCustomAppID(appId);

        CreateApiTokenResponse response = observeClient.createApiToken(request);
        this.token = response.getToken();

        this.expiresAt = System.currentTimeMillis() / 1000 + response.getExpiresIn() - 100;

        return this.token;
    }

    public boolean isTokenExpired() {
        return System.currentTimeMillis() / 1000 >= expiresAt;
    }

    public void refreshTokenIfNeeded() throws ApiException {
        if (token == null || isTokenExpired()) {
            logger.fine("token expired or not set, refreshing...");
            getToken();
        }
    }

    public Response request(String method, String url, Headers headers, RequestBody body)
            throws IOException, ApiException {
        refreshTokenIfNeeded();

        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .method(method, body);

        Headers.Builder headersBuilder = headers != null ? headers.newBuilder() : new Headers.Builder();
        headersBuilder.add("Authorization", "Bearer " + token);
        requestBuilder.headers(headersBuilder.build());

        logger.fine("requesting " + url + " to export trace data");

        Response response = httpClient.newCall(requestBuilder.build()).execute();

        if (response.code() == 401) {
            response.close(); // 关闭原响应
            getToken();
            headersBuilder.set("Authorization", "Bearer " + token);
            requestBuilder.headers(headersBuilder.build());
            response = httpClient.newCall(requestBuilder.build()).execute();
        }

        return response;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    // 认证拦截器
    private class AuthInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            try {
                refreshTokenIfNeeded();
            } catch (ApiException e) {
                throw new IOException("Failed to refresh token", e);
            }

            Request originalRequest = chain.request();
            Request authenticatedRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + token)
                    .build();

            Response response = chain.proceed(authenticatedRequest);

            // 如果 token 失效，尝试重新获取并重试一次
            if (response.code() == 401) {
                response.close();
                try {
                    getToken();
                    Request retryRequest = originalRequest.newBuilder()
                            .header("Authorization", "Bearer " + token)
                            .build();
                    response = chain.proceed(retryRequest);
                } catch (ApiException e) {
                    throw new IOException("Failed to refresh token on retry", e);
                }
            }

            return response;
        }
    }
}
