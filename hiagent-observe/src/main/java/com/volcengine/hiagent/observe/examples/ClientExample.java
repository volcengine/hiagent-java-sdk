package com.volcengine.hiagent.observe.examples;

import com.volcengine.hiagent.observe.Client;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;

public class ClientExample {
  public static void main(String[] args) {
    String traceEndpoint = System.getenv("HIAGENT_TRACE_ENDPOINT");
    String topEndpoint = System.getenv("HIAGENT_TOP_ENDPOINT");
    String ak = System.getenv("VOLC_ACCESSKEY");
    String sk = System.getenv("VOLC_SECRETKEY");
    String workspaceId = System.getenv("WORKSPACE_ID");
    String appId = System.getenv("CUSTOM_APP_ID");

    Client client = new Client(traceEndpoint, topEndpoint, ak, sk, workspaceId, appId);

    try {
      client.init();
      System.out.println("OpenTelemetry initialized successfully");

      Tracer tracer = client.getTracer("example-service", "1.0.0");

      Span span = tracer.spanBuilder("example-operation")
          .setAttribute("operation.type", "test")
          .setAttribute("user.id", "test-user")
          .startSpan();

      try {
        Thread.sleep(100);
        span.addEvent("Processing started");

        Thread.sleep(200);
        span.addEvent("Processing completed");
      } catch (InterruptedException e) {
        span.recordException(e);
        Thread.currentThread().interrupt();
      } finally {
        span.end();
      }

      Thread.sleep(1000);
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    } finally {
      client.shutdown();
    }
  }
}
