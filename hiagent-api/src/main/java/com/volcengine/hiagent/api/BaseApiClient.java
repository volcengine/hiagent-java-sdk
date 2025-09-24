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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * API请求的基类，提供通用的API调用方法
 * 使用Java标准库中的HttpClient作为HTTP客户端
 */
public abstract class BaseApiClient {
    private HttpClient httpClient;
    private String baseUrl;
    private String apiKey;
    private static final String BASE_PATH = "/api/proxy/api/v1/";
    private static final  String END_MARKER = "[done]";
    private static final Gson GSON = new Gson();

    /**
     * 构造函数
     * @param baseUrl API基础URL
     * @param apiKey API密钥
     */
    public BaseApiClient(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(30))
                .build();
        this.apiKey = apiKey;
    }

    /**
     * 构造函数，支持自定义HttpClient
     * @param baseUrl API基础URL
     * @param httpClient 自定义HttpClient实例
     */
    public BaseApiClient(String baseUrl, String apiKey, HttpClient httpClient) {
        this.baseUrl = baseUrl;
        this.httpClient = httpClient;
        this.apiKey = apiKey;
    }

    /**
     * 获取HttpClient实例
     * @return HttpClient实例
     */
    public HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * 设置HttpClient实例
     * @param httpClient HttpClient实例
     */
    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 获取基础URL
     * @return 基础URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * 设置基础URL
     * @param baseUrl 基础URL
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 执行POST请求
     * @param endpoint 接口名
     * @param requestBody 请求体
     * @param responseClass 响应类型
     * @param <T> 响应类型泛型
     * @return 响应对象
     * @throws IOException IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException API调用异常
     */
    protected <T> T post(String endpoint, Object requestBody, Class<T> responseClass)
            throws IOException, InterruptedException, ApiException {
        // 修复：直接调用带自定义请求头的版本，传入null作为自定义请求头
        return post(endpoint, requestBody, responseClass, null);
    }

    /**
     * 执行带自定义请求头的POST请求
     * @param endpoint 接口名
     * @param requestBody 请求体
     * @param responseClass 响应类型
     * @param customHeaders 自定义请求头
     * @param <T> 响应类型泛型
     * @return 响应对象
     * @throws IOException IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException API调用异常
     */
    protected <T> T post(String endpoint, Object requestBody, Class<T> responseClass, 
                         Map<String, String> customHeaders) 
            throws IOException, InterruptedException, ApiException {
        // 构建完整URL
        String url = buildUrl(endpoint);
        
        // 构建请求体JSON
        String requestBodyJson = GSON.toJson(requestBody);
        
        // 构建请求
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Apikey", apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson));
        
        // 添加自定义请求头
        addCustomHeaders(requestBuilder, customHeaders);
        
        HttpRequest request = requestBuilder.build();
        
        // 发送请求并获取响应
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        // 处理响应
        return handleResponse(response, responseClass);
    }

    /**
     * 执行流式POST请求
     *
     * @param endpoint         接口名
     * @param requestBody      请求体
     * @param dataProcessor    数据处理器，用于处理流式响应
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
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Apikey", apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "text/event-stream")
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson));

        HttpRequest request = requestBuilder.build();

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        // 异步发送请求并处理响应
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                .thenAccept(response -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (!line.isBlank() && line.startsWith("data:")) {
                                // 提取 SSE 数据并放入队列
                                queue.put(line.substring(5).trim());
                            }
                        }
                        // SSE 流结束，放入结束标志
                        queue.put(END_MARKER);
                    } catch (Exception e) {
                        try {
                            queue.put(END_MARKER); // 异常时也结束
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        throw new RuntimeException(e);
                    }
                });

        // 返回一个 Iterable，支持 for 循环消费
        return () -> new SSEIterator<>(queue, END_MARKER, dataProcessor);
    }

    /**
     * 执行GET请求
     * @param endpoint 接口名
     * @param queryParams 查询参数
     * @param responseClass 响应类型
     * @param <T> 响应类型泛型
     * @return 响应对象
     * @throws IOException IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException API调用异常
     */
    protected <T> T get(String endpoint, Map<String, String> queryParams, Class<T> responseClass) 
            throws IOException, InterruptedException, ApiException {
        // 修复：直接调用带自定义请求头的版本，传入null作为自定义请求头
        return get(endpoint, queryParams, responseClass, null);
    }

    /**
     * 执行带自定义请求头的GET请求
     * @param endpoint 接口名
     * @param queryParams 查询参数
     * @param responseClass 响应类型
     * @param customHeaders 自定义请求头
     * @param <T> 响应类型泛型
     * @return 响应对象
     * @throws IOException IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException API调用异常
     */
    protected <T> T get(String endpoint, Map<String, String> queryParams, Class<T> responseClass, 
                       Map<String, String> customHeaders) 
            throws IOException, InterruptedException, ApiException {
        // 构建完整URL，包含查询参数
        String url = buildUrlWithQueryParams(endpoint, queryParams);
        
        // 构建请求
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Apikey", apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .GET();
        
        // 添加自定义请求头
        addCustomHeaders(requestBuilder, customHeaders);
        
        HttpRequest request = requestBuilder.build();
        
        // 发送请求并获取响应
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        // 处理响应
        return handleResponse(response, responseClass);
    }

    /**
     * 执行PUT请求
     * @param endpoint 接口名
     * @param requestBody 请求体
     * @param responseClass 响应类型
     * @param <T> 响应类型泛型
     * @return 响应对象
     * @throws IOException IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException API调用异常
     */
    protected <T> T put(String endpoint, Object requestBody, Class<T> responseClass) 
            throws IOException, InterruptedException, ApiException {
        // 修复：直接调用带自定义请求头的版本，传入null作为自定义请求头
        return put(endpoint, requestBody, responseClass, null);
    }

    /**
     * 执行带自定义请求头的PUT请求
     * @param endpoint 接口名
     * @param requestBody 请求体
     * @param responseClass 响应类型
     * @param customHeaders 自定义请求头
     * @param <T> 响应类型泛型
     * @return 响应对象
     * @throws IOException IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException API调用异常
     */
    protected <T> T put(String endpoint, Object requestBody, Class<T> responseClass, 
                       Map<String, String> customHeaders) 
            throws IOException, InterruptedException, ApiException {
        // 构建完整URL
        String url = buildUrl(endpoint);
        
        // 构建请求体JSON
        String requestBodyJson = GSON.toJson(requestBody);
        
        // 构建请求
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Apikey", apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBodyJson));
        
        // 添加自定义请求头
        addCustomHeaders(requestBuilder, customHeaders);
        
        HttpRequest request = requestBuilder.build();
        
        // 发送请求并获取响应
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        // 处理响应
        return handleResponse(response, responseClass);
    }

    /**
     * 执行DELETE请求
     * @param endpoint 接口名
     * @param responseClass 响应类型
     * @param <T> 响应类型泛型
     * @return 响应对象
     * @throws IOException IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException API调用异常
     */
    protected <T> T delete(String endpoint, Class<T> responseClass) 
            throws IOException, InterruptedException, ApiException {
        return delete(endpoint, null, responseClass);
    }

    /**
     * 执行带查询参数的DELETE请求
     * @param endpoint 接口名
     * @param queryParams 查询参数
     * @param responseClass 响应类型
     * @param <T> 响应类型泛型
     * @return 响应对象
     * @throws IOException IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException API调用异常
     */
    protected <T> T delete(String endpoint, Map<String, String> queryParams, Class<T> responseClass) 
            throws IOException, InterruptedException, ApiException {
        return delete(endpoint, queryParams, responseClass, null);
    }

    /**
     * 执行带查询参数和自定义请求头的DELETE请求
     * @param endpoint 接口名
     * @param queryParams 查询参数
     * @param responseClass 响应类型
     * @param customHeaders 自定义请求头
     * @param <T> 响应类型泛型
     * @return 响应对象
     * @throws IOException IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException API调用异常
     */
    protected <T> T delete(String endpoint, Map<String, String> queryParams, Class<T> responseClass, 
                          Map<String, String> customHeaders) 
            throws IOException, InterruptedException, ApiException {
        // 构建完整URL，包含查询参数
        String url = buildUrlWithQueryParams(endpoint, queryParams);
        
        // 构建请求
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Apikey", apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .DELETE();
        
        // 添加自定义请求头
        addCustomHeaders(requestBuilder, customHeaders);
        
        HttpRequest request = requestBuilder.build();
        
        // 发送请求并获取响应
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        // 处理响应
        return handleResponse(response, responseClass);
    }

    /**
     * 构建完整URL
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
     * @param endpoint 接口名
     * @param queryParams 查询参数
     * @return 带查询参数的完整URL
     */
    private String buildUrlWithQueryParams(String endpoint, Map<String, String> queryParams) {
        String url = buildUrl(endpoint);
        
        if (queryParams != null && !queryParams.isEmpty()) {
            StringJoiner joiner = new StringJoiner("&", "?", "");
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                joiner.add(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" + 
                           URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            }
            url += joiner.toString();
        }
        
        return url;
    }

    /**
     * 处理HTTP响应
     * @param response HTTP响应
     * @param responseClass 响应类型
     * @param <T> 响应类型泛型
     * @return 响应对象
     * @throws ApiException API调用异常
     */
    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws ApiException {
        int statusCode = response.statusCode();
        String responseBody = response.body();
        
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
     * @param requestBuilder 请求构建器
     * @param customHeaders 自定义请求头
     */
    private void addCustomHeaders(HttpRequest.Builder requestBuilder, Map<String, String> customHeaders) {
        if (customHeaders != null && !customHeaders.isEmpty()) {
            for (Map.Entry<String, String> entry : customHeaders.entrySet()) {
                requestBuilder.header(entry.getKey(), entry.getValue());
            }
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

    private static class SSEIterator<R> implements java.util.Iterator<R> {
        private final BlockingQueue<String> queue;
        private final String END_MARKER;
        private final Function<String, R> dataProcessor;
        private String nextItem;

        public SSEIterator(BlockingQueue<String> queue, String endMarker, Function<String, R> dataProcessor) {
            this.queue = queue;
            this.END_MARKER = endMarker;
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
            return !END_MARKER.equals(nextItem); // 判断是否结束
        }

        @Override
        public R next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            String rawData = nextItem;
            nextItem = null; // 清空当前项，准备读取下一项
            return dataProcessor.apply(rawData); // 使用处理函数转换数据
        }
    }
}