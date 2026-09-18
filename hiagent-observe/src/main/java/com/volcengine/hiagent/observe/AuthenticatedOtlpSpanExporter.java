package com.volcengine.hiagent.observe;

import com.volcengine.ApiException;
import com.volcengine.hiagent.api.ProductCode;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporterBuilder;
import io.opentelemetry.sdk.common.CompletableResultCode;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.opentelemetry.sdk.trace.export.SpanExporter;

import java.util.Collection;
import java.util.logging.Logger;

/**
 * 带认证的 OTLP Span Exporter
 * 自动处理 token 刷新和重试
 */
public class AuthenticatedOtlpSpanExporter implements SpanExporter {
  private static final Logger logger = Logger.getLogger(AuthenticatedOtlpSpanExporter.class.getName());

  private final AuthSession authSession;
  private final String endpoint;
  private final String productCode;
  private volatile OtlpHttpSpanExporter currentExporter;

  public AuthenticatedOtlpSpanExporter(String endpoint, AuthSession authSession) {
    this(endpoint, authSession, null);
  }

  public AuthenticatedOtlpSpanExporter(String endpoint, AuthSession authSession, String productCode) {
    this.endpoint = endpoint;
    this.authSession = authSession;
    this.productCode = ProductCode.normalize(productCode);
    this.currentExporter = createExporter();
  }

  private OtlpHttpSpanExporter createExporter() {
    try {
      authSession.refreshTokenIfNeeded();
      String token = authSession.getToken();
      OtlpHttpSpanExporterBuilder builder = OtlpHttpSpanExporter.builder()
          .setEndpoint(endpoint)
          .addHeader("Authorization", "Bearer " + token);
      if (productCode != null) {
        builder.addHeader(ProductCode.HEADER_NAME, productCode);
      }
      return builder.build();
    } catch (ApiException e) {
      logger.severe("Failed to create exporter with token: " + e.getMessage());
      throw new RuntimeException("Failed to create authenticated exporter", e);
    }
  }

  @Override
  public CompletableResultCode export(Collection<SpanData> spans) {
    try {
      CompletableResultCode result = currentExporter.export(spans);

      // 如果导出失败，可能是 token 过期，尝试重新创建 exporter
      if (!result.isSuccess()) {
        logger.fine("Export failed, trying to refresh token and retry");
        try {
          authSession.getToken(); // 强制刷新 token
          currentExporter = createExporter();
          result = currentExporter.export(spans);
        } catch (ApiException e) {
          logger.warning("Failed to refresh token for retry: " + e.getMessage());
        }
      }

      return result;
    } catch (Exception e) {
      logger.severe("Unexpected error during export: " + e.getMessage());
      return CompletableResultCode.ofFailure();
    }
  }

  @Override
  public CompletableResultCode flush() {
    return currentExporter.flush();
  }

  @Override
  public CompletableResultCode shutdown() {
    return currentExporter.shutdown();
  }
}
