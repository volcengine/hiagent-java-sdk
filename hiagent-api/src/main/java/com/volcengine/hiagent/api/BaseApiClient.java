// Copyright (c) 2024 Bytedance Ltd. and/or its affiliates
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.volcengine.hiagent.api;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * API请求的基类，提供通用的API调用方法
 * 使用OkHttp作为HTTP客户端，兼容Java 8
 */
public abstract class BaseApiClient {
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    private OkHttpClient httpClient;
    private String baseUrl;
    private String apiKey;
    private volatile String productCode;
    private static final String BASE_PATH = "/api/proxy/api/v1/";
    public static final String DATA_MARKER = "data:";
    private static final String END_MARKER = "[DONE]";
    private static final Gson GSON = new Gson();

    /**
     * 构造函数
     *
     * @param baseUrl API基础URL
     * @param apiKey  API密钥
     */
    public BaseApiClient(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        this.apiKey = apiKey;
    }

    /**
     * 构造函数，支持自定义OkHttpClient
     *
     * @param baseUrl    API基础URL
     * @param httpClient 自定义OkHttpClient实例
     */
    public BaseApiClient(String baseUrl, String apiKey, OkHttpClient httpClient) {
        this.baseUrl = baseUrl;
        this.httpClient = httpClient;
        this.apiKey = apiKey;
    }

    public BaseApiClient(String baseUrl, String apiKey, String productCode) {
        this(baseUrl, apiKey);
        this.productCode = ProductCode.normalize(productCode);
    }

    public BaseApiClient(String baseUrl, String apiKey, OkHttpClient httpClient, String productCode) {
        this(baseUrl, apiKey, httpClient);
        this.productCode = ProductCode.normalize(productCode);
    }

    public String getProductCode() { return productCode; }

    public void setProductCode(String productCode) {
        this.productCode = ProductCode.normalize(productCode);
    }

    /**
     * 获取OkHttpClient实例
     *
     * @return OkHttpClient实例
     */
    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * 设置OkHttpClient实例
     *
     * @param httpClient OkHttpClient实例
     */
    public void setHttpClient(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 获取基础URL
     *
     * @return 基础URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * 设置基础URL
     *
     * @param baseUrl 基础URL
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 执行POST请求
     *
     * @param endpoint      接口名
     * @param requestBody   请求体
     * @param responseClass 响应类型
     * @param <T>           响应类型泛型
     * @return 响应对象
     * @throws IOException          IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException         API调用异常
     */
    protected <T> T post(String endpoint, Object requestBody, Class<T> responseClass)
            throws IOException, InterruptedException, ApiException {
        // 修复：直接调用带自定义请求头的版本，传入null作为自定义请求头
        return post(endpoint, requestBody, responseClass, null);
    }

    /**
     * 执行带自定义请求头的POST请求
     *
     * @param endpoint      接口名
     * @param requestBody   请求体
     * @param responseClass 响应类型
     * @param customHeaders 自定义请求头
     * @param <T>           响应类型泛型
     * @return 响应对象
     * @throws IOException          IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException         API调用异常
     */
    protected <T> T post(String endpoint, Object requestBody, Class<T> responseClass,
            Map<String, String> customHeaders)
            throws IOException, InterruptedException, ApiException {
        // 构建完整URL
        String url = buildUrl(endpoint);

        // 构建请求体JSON
        String requestBodyJson = GSON.toJson(requestBody);

        // 构建请求
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .header("Apikey", apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .post(RequestBody.create(requestBodyJson, JSON_MEDIA_TYPE));
        addProductCodeHeader(requestBuilder);

        // 添加自定义请求头
        addCustomHeaders(requestBuilder, customHeaders);

        Request request = requestBuilder.build();

        // 发送请求并获取响应
        try (Response response = httpClient.newCall(request).execute()) {
            ResponseBody body = response.body();
            return handleResponse(response.code(), body == null ? "" : body.string(), responseClass);
        }
    }

    /**
     * 执行流式POST请求
     *
     * @param endpoint      接口名
     * @param requestBody   请求体
     * @param dataProcessor 数据处理器，用于处理流式响应
     * @return
     * @throws IOException          IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException         API调用异常
     */
    protected <R> Iterable<R> postStream(String endpoint, Object requestBody,
            Function<String, R> dataProcessor)
            throws IOException, InterruptedException, ApiException {
        // 构建完整URL
        String url = buildUrl(endpoint);

        // 构建请求体JSON
        String requestBodyJson = GSON.toJson(requestBody);

        // 构建请求
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .header("Apikey", apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "text/event-stream")
                .post(RequestBody.create(requestBodyJson, JSON_MEDIA_TYPE));
        addProductCodeHeader(requestBuilder);

        Request request = requestBuilder.build();

        BlockingQueue<SSEMessage> queue = new LinkedBlockingQueue<>();
        // 异步发送请求并处理响应
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                putSSEMessage(queue, SSEMessage.error(e));
            }

            @Override
            public void onResponse(Call call, Response response) {
                try (Response resp = response;
                        ResponseBody responseBody = resp.body()) {
                    if (resp.code() < 200 || resp.code() >= 300) {
                        String errorBody = responseBody == null ? "" : responseBody.string();
                        putSSEMessage(queue, SSEMessage.error(
                                new ApiException("API调用失败: " + resp.code() + " - " + errorBody)));
                        return;
                    }

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(responseBody == null
                                    ? new java.io.ByteArrayInputStream(new byte[0])
                                    : responseBody.byteStream(), StandardCharsets.UTF_8));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!line.trim().isEmpty() && line.startsWith(DATA_MARKER)) {
                            line = line.substring(DATA_MARKER.length());
                            if (!line.isEmpty() && line.charAt(0) == ' ') {
                                line = line.substring(1);
                            }
                            // 兼容v1版本api，格式：data:data: {}
                            if (line.startsWith(DATA_MARKER)) {
                                line = line.substring(DATA_MARKER.length());
                                if (!line.isEmpty() && line.charAt(0) == ' ') {
                                    line = line.substring(1);
                                }
                            }
                            if (END_MARKER.equals(line.trim())) {
                                putSSEMessage(queue, SSEMessage.end());
                                return;
                            }
                            // 提取 SSE 数据并放入队列
                            putSSEMessage(queue, SSEMessage.data(line));
                        }
                    }
                    // SSE 流结束，放入结束标志
                    putSSEMessage(queue, SSEMessage.end());
                } catch (Exception e) {
                    putSSEMessage(queue, SSEMessage.error(e));
                }
            }
        });

        // 返回一个 Iterable，支持 for 循环消费
        return () -> new SSEIterator<>(queue, dataProcessor);
    }

    /**
     * 执行GET请求
     *
     * @param endpoint      接口名
     * @param queryParams   查询参数
     * @param responseClass 响应类型
     * @param <T>           响应类型泛型
     * @return 响应对象
     * @throws IOException          IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException         API调用异常
     */
    protected <T> T get(String endpoint, Map<String, String> queryParams, Class<T> responseClass)
            throws IOException, InterruptedException, ApiException {
        // 修复：直接调用带自定义请求头的版本，传入null作为自定义请求头
        return get(endpoint, queryParams, responseClass, null);
    }

    /**
     * 执行带自定义请求头的GET请求
     *
     * @param endpoint      接口名
     * @param queryParams   查询参数
     * @param responseClass 响应类型
     * @param customHeaders 自定义请求头
     * @param <T>           响应类型泛型
     * @return 响应对象
     * @throws IOException          IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException         API调用异常
     */
    protected <T> T get(String endpoint, Map<String, String> queryParams, Class<T> responseClass,
            Map<String, String> customHeaders)
            throws IOException, InterruptedException, ApiException {
        // 构建完整URL，包含查询参数
        String url = buildUrlWithQueryParams(endpoint, queryParams);

        // 构建请求
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .header("Apikey", apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .get();
        addProductCodeHeader(requestBuilder);

        // 添加自定义请求头
        addCustomHeaders(requestBuilder, customHeaders);

        Request request = requestBuilder.build();

        // 发送请求并获取响应
        try (Response response = httpClient.newCall(request).execute()) {
            ResponseBody body = response.body();
            return handleResponse(response.code(), body == null ? "" : body.string(), responseClass);
        }
    }

    /**
     * 执行PUT请求
     *
     * @param endpoint      接口名
     * @param requestBody   请求体
     * @param responseClass 响应类型
     * @param <T>           响应类型泛型
     * @return 响应对象
     * @throws IOException          IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException         API调用异常
     */
    protected <T> T put(String endpoint, Object requestBody, Class<T> responseClass)
            throws IOException, InterruptedException, ApiException {
        // 修复：直接调用带自定义请求头的版本，传入null作为自定义请求头
        return put(endpoint, requestBody, responseClass, null);
    }

    /**
     * 执行带自定义请求头的PUT请求
     *
     * @param endpoint      接口名
     * @param requestBody   请求体
     * @param responseClass 响应类型
     * @param customHeaders 自定义请求头
     * @param <T>           响应类型泛型
     * @return 响应对象
     * @throws IOException          IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException         API调用异常
     */
    protected <T> T put(String endpoint, Object requestBody, Class<T> responseClass,
            Map<String, String> customHeaders)
            throws IOException, InterruptedException, ApiException {
        // 构建完整URL
        String url = buildUrl(endpoint);

        // 构建请求体JSON
        String requestBodyJson = GSON.toJson(requestBody);

        // 构建请求
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .header("Apikey", apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .put(RequestBody.create(requestBodyJson, JSON_MEDIA_TYPE));
        addProductCodeHeader(requestBuilder);

        // 添加自定义请求头
        addCustomHeaders(requestBuilder, customHeaders);

        Request request = requestBuilder.build();

        // 发送请求并获取响应
        try (Response response = httpClient.newCall(request).execute()) {
            ResponseBody body = response.body();
            return handleResponse(response.code(), body == null ? "" : body.string(), responseClass);
        }
    }

    /**
     * 执行DELETE请求
     *
     * @param endpoint      接口名
     * @param responseClass 响应类型
     * @param <T>           响应类型泛型
     * @return 响应对象
     * @throws IOException          IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException         API调用异常
     */
    protected <T> T delete(String endpoint, Class<T> responseClass)
            throws IOException, InterruptedException, ApiException {
        return delete(endpoint, null, responseClass);
    }

    /**
     * 执行带查询参数的DELETE请求
     *
     * @param endpoint      接口名
     * @param queryParams   查询参数
     * @param responseClass 响应类型
     * @param <T>           响应类型泛型
     * @return 响应对象
     * @throws IOException          IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException         API调用异常
     */
    protected <T> T delete(String endpoint, Map<String, String> queryParams, Class<T> responseClass)
            throws IOException, InterruptedException, ApiException {
        return delete(endpoint, queryParams, responseClass, null);
    }

    /**
     * 执行带查询参数和自定义请求头的DELETE请求
     *
     * @param endpoint      接口名
     * @param queryParams   查询参数
     * @param responseClass 响应类型
     * @param customHeaders 自定义请求头
     * @param <T>           响应类型泛型
     * @return 响应对象
     * @throws IOException          IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException         API调用异常
     */
    protected <T> T delete(String endpoint, Map<String, String> queryParams, Class<T> responseClass,
            Map<String, String> customHeaders)
            throws IOException, InterruptedException, ApiException {
        // 构建完整URL，包含查询参数
        String url = buildUrlWithQueryParams(endpoint, queryParams);

        // 构建请求
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .header("Apikey", apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .delete();
        addProductCodeHeader(requestBuilder);

        // 添加自定义请求头
        addCustomHeaders(requestBuilder, customHeaders);

        Request request = requestBuilder.build();

        // 发送请求并获取响应
        try (Response response = httpClient.newCall(request).execute()) {
            ResponseBody body = response.body();
            return handleResponse(response.code(), body == null ? "" : body.string(), responseClass);
        }
    }

    /**
     * 构建完整URL
     *
     * @param endpoint 接口名
     * @return 完整URL
     */
    private String buildUrl(String endpoint) {
        // 确保baseUrl末尾没有斜杠，endpoint开头没有斜杠
        String normalizedBaseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        String normalizedEndpoint = endpoint.startsWith("/") ? endpoint.substring(1) : endpoint;

        return normalizedBaseUrl + BASE_PATH + normalizedEndpoint;
    }

    /**
     * 构建带查询参数的完整URL
     *
     * @param endpoint    接口名
     * @param queryParams 查询参数
     * @return 带查询参数的完整URL
     */
    private String buildUrlWithQueryParams(String endpoint, Map<String, String> queryParams) {
        String url = buildUrl(endpoint);

        if (queryParams != null && !queryParams.isEmpty()) {
            StringJoiner joiner = new StringJoiner("&", "?", "");
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                joiner.add(encodeParam(entry.getKey()) + "=" + encodeParam(entry.getValue()));
            }
            url += joiner.toString();
        }

        return url;
    }

    /**
     * 处理HTTP响应
     *
     * @param response      HTTP响应
     * @param responseClass 响应类型
     * @param <T>           响应类型泛型
     * @return 响应对象
     * @throws ApiException API调用异常
     */
    private <T> T handleResponse(int statusCode, String responseBody, Class<T> responseClass) throws ApiException {
        // 检查响应状态码
        if (statusCode < 200 || statusCode >= 300) {
            throw new ApiException("API调用失败: " + statusCode + " - " + responseBody);
        }

        try {
            // 解析JSON响应
            return GSON.fromJson(responseBody, responseClass);
        } catch (JsonSyntaxException e) {
            throw new ApiException("响应JSON解析失败: " + e.getMessage(), e);
        }
    }

    /**
     * 添加自定义请求头
     *
     * @param requestBuilder 请求构建器
     * @param customHeaders  自定义请求头
     */
    private void addCustomHeaders(Request.Builder requestBuilder, Map<String, String> customHeaders) {
        if (customHeaders != null && !customHeaders.isEmpty()) {
            for (Map.Entry<String, String> entry : customHeaders.entrySet()) {
                requestBuilder.header(entry.getKey(), entry.getValue());
            }
        }
    }

    private void addProductCodeHeader(Request.Builder requestBuilder) {
        if (productCode != null) requestBuilder.header(ProductCode.HEADER_NAME, productCode);
    }

    private String encodeParam(String value) {
        try {
            return URLEncoder.encode(value, UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("UTF-8 encoding is not supported", e);
        }
    }

    /**
     * 自定义API异常类
     */
    public static class ApiException extends Exception {
        public ApiException(String message) {
            super(message);
        }

        public ApiException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * 自定义SSE迭代器类
     */

    private static void putSSEMessage(BlockingQueue<SSEMessage> queue, SSEMessage message) {
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static class SSEMessage {
        private final String data;
        private final Throwable error;
        private final boolean end;

        private SSEMessage(String data, Throwable error, boolean end) {
            this.data = data;
            this.error = error;
            this.end = end;
        }

        private static SSEMessage data(String data) {
            return new SSEMessage(data, null, false);
        }

        private static SSEMessage error(Throwable error) {
            return new SSEMessage(null, error, false);
        }

        private static SSEMessage end() {
            return new SSEMessage(null, null, true);
        }
    }

    private static class SSEIterator<R> implements java.util.Iterator<R> {
        private final BlockingQueue<SSEMessage> queue;
        private final Function<String, R> dataProcessor;
        private SSEMessage nextItem;

        public SSEIterator(BlockingQueue<SSEMessage> queue, Function<String, R> dataProcessor) {
            this.queue = queue;
            this.dataProcessor = dataProcessor;
        }

        @Override
        public boolean hasNext() {
            if (nextItem == null) {
                try {
                    nextItem = queue.take(); // 阻塞等待下一个事件
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
            if (nextItem.error != null) {
                throw toRuntimeException(nextItem.error);
            }
            return !nextItem.end; // 判断是否结束
        }

        @Override
        public R next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            String rawData = nextItem.data;
            nextItem = null; // 清空当前项，准备读取下一项
            return dataProcessor.apply(rawData); // 使用处理函数转换数据
        }

        private RuntimeException toRuntimeException(Throwable error) {
            if (error instanceof RuntimeException) {
                return (RuntimeException) error;
            }
            return new RuntimeException(error);
        }
    }
}
