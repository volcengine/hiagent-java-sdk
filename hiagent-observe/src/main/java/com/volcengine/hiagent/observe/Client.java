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
package com.volcengine.hiagent.observe;

import com.volcengine.ApiException;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.api.common.AttributeKey;

import java.util.logging.Logger;

public class Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());
    private static final String VERSION = "1.0.0-SNAPSHOT";

    private final String traceEndpoint;
    private final String topEndpoint;
    private final String ak;
    private final String sk;
    private final String workspaceId;
    private final String appId;

    private AuthSession authSession;
    private OpenTelemetrySdk openTelemetry;
    private SdkTracerProvider tracerProvider;
    private volatile boolean shutdown = false;

    public Client(String traceEndpoint, String topEndpoint, String ak, String sk, String workspaceId, String appId) {
        this.traceEndpoint = traceEndpoint;
        this.topEndpoint = topEndpoint;
        this.ak = ak;
        this.sk = sk;
        this.workspaceId = workspaceId;
        this.appId = appId;
    }

    public SdkTracerProvider init() throws RuntimeException {
        try {
            // 创建认证会话
            authSession = new AuthSession(topEndpoint, ak, sk, workspaceId, appId);

            // 获取初始 token
            String token = authSession.getToken();
            logger.fine("got initial token: " + token);

            // 创建带认证的 OTLP Span Exporter
            AuthenticatedOtlpSpanExporter exporter = new AuthenticatedOtlpSpanExporter(
                    traceEndpoint + "/v1/traces",
                    authSession);

            // 创建资源
            Resource resource = Resource.getDefault()
                    .merge(Resource.builder()
                            .put(AttributeKey.stringKey("service.name"), "HIAGENT_OBSERVE_SDK")
                            .put(AttributeKey.stringKey("service.version"), VERSION)
                            .build());

            // 创建 TracerProvider
            tracerProvider = SdkTracerProvider.builder()
                    .setResource(resource)
                    .addSpanProcessor(BatchSpanProcessor.builder(exporter).build())
                    .build();

            // 创建 OpenTelemetry SDK
            openTelemetry = OpenTelemetrySdk.builder()
                    .setTracerProvider(tracerProvider)
                    .setPropagators(ContextPropagators.create(
                            W3CTraceContextPropagator.getInstance()))
                    .build();

            // 设置全局 OpenTelemetry 实例
            GlobalOpenTelemetry.set(openTelemetry);

            return tracerProvider;

        } catch (ApiException e) {
            throw new RuntimeException("failed to get initial token: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("failed to initialize OpenTelemetry: " + e.getMessage(), e);
        }
    }

    public Tracer getTracer(String instrumentationName) {
        if (openTelemetry == null) {
            throw new IllegalStateException("Client not initialized. Call init() first.");
        }
        return openTelemetry.getTracer(instrumentationName);
    }

    public Tracer getTracer(String instrumentationName, String instrumentationVersion) {
        if (openTelemetry == null) {
            throw new IllegalStateException("Client not initialized. Call init() first.");
        }
        return openTelemetry.getTracer(instrumentationName, instrumentationVersion);
    }

    public void shutdown() {
        if (shutdown) {
            logger.warning("Calling shutdown() multiple times.");
            return;
        }

        shutdown = true;
        logger.info("Shutting down OpenTelemetry client...");

        try {
            if (tracerProvider != null) {
                tracerProvider.close();
            }
            if (openTelemetry != null) {
                openTelemetry.close();
            }

            // 重置全局 OpenTelemetry 实例以避免重复关闭
            GlobalOpenTelemetry.resetForTest();

        } catch (Exception e) {
            logger.warning("Error during shutdown: " + e.getMessage());
        }

        logger.info("OpenTelemetry client shutdown completed.");
    }

    public AuthSession getAuthSession() {
        return authSession;
    }

    public OpenTelemetrySdk getOpenTelemetry() {
        return openTelemetry;
    }
}
