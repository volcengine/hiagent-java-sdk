package com.volcengine.hiagent.observe.examples;

import java.util.UUID;
import java.time.Instant;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;

import com.volcengine.hiagent.observe.Client;
import com.volcengine.hiagent.observe.model.SemanticConvention;
import com.volcengine.hiagent.observe.model.SpanType;

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

      String conversationId = UUID.randomUUID().toString();
      String messageId = UUID.randomUUID().toString();
      String modelId = UUID.randomUUID().toString();
      String modelName = "gemma3:1b";
      String userInput = "What is the history of artificial intelligence?";
      String modelOutput = "Artificial intelligence (AI) has a rich history dating back to ancient civilizations, but modern AI began in the 1950s...";

      // 1. 创建主要的工作流span（根span）
      Instant baseTime = Instant.now();
      Span workflowSpan = tracer.spanBuilder("POST /v1/chat/completions")
          .setStartTimestamp(baseTime)
          .setAttribute(SemanticConvention.CONVERSATION_ID.getValue(), conversationId)
          .setAttribute(SemanticConvention.MESSAGE_ID.getValue(), messageId)
          .setAttribute(SemanticConvention.INPUT.getValue(), userInput)
          .setAttribute(SemanticConvention.INPUT_RAW.getValue(), userInput)
          .setAttribute(SemanticConvention.MODEL_ID.getValue(), modelId)
          .setAttribute(SemanticConvention.MODEL_NAME.getValue(), modelName)
          .startSpan();

      try (Scope workflowScope = workflowSpan.makeCurrent()) {
        Thread.sleep(10); // 等待一小段时间

        // 2. 创建LLM开始span（workflowSpan的子span）
        Instant llmStartTime = Instant.now();
        Span llmStartSpan = tracer.spanBuilder("START_LLM")
            .setParent(Context.current().with(workflowSpan))
            .setStartTimestamp(llmStartTime)
            .setAttribute(SemanticConvention.SPAN_TYPE.getValue(), SpanType.START.getValue())
            .setAttribute(SemanticConvention.MESSAGE_ID.getValue(), messageId)
            .startSpan();

        try (Scope llmStartScope = llmStartSpan.makeCurrent()) {
          Thread.sleep(50); // 模拟处理时间

          // 3. 创建首token延迟span（llmStartSpan的子span）
          Instant firstTokenTime = Instant.now();
          Span firstTokenSpan = tracer.spanBuilder("START_LLM_TOKEN")
              .setParent(Context.current().with(llmStartSpan))
              .setStartTimestamp(firstTokenTime)
              .setAttribute(SemanticConvention.LATENCY_FIRST_RESP.getValue(), "150")
              .setAttribute(SemanticConvention.INPUT.getValue(), "{\"input\":\"" + userInput + "\"}")
              .setAttribute(SemanticConvention.INPUT_RAW.getValue(), userInput)
              .setAttribute(SemanticConvention.MESSAGE_ID.getValue(), messageId)
              .startSpan();

          try (Scope firstTokenScope = firstTokenSpan.makeCurrent()) {
            Thread.sleep(100); // 模拟首token延迟
          } finally {
            firstTokenSpan.setStatus(StatusCode.OK, "");
            firstTokenSpan.end();
          }

          Thread.sleep(30); // 等待一段时间后创建下一个span

          // 4. 创建LLM结束span（llmStartSpan的子span）- 包含完整属性
          Instant llmEndTime = Instant.now();
          Span llmEndSpan = tracer.spanBuilder("LLM_RESPONSE")
              .setParent(Context.current().with(llmStartSpan))
              .setStartTimestamp(llmEndTime)
              .setAttribute(SemanticConvention.MESSAGE_ID.getValue(), messageId)
              .setAttribute(SemanticConvention.LATENCY.getValue(), "2500")
              .setAttribute(SemanticConvention.LATENCY_FIRST_RESP.getValue(), "150")
              .setAttribute(SemanticConvention.MODEL_ID.getValue(), modelId)
              .setAttribute(SemanticConvention.MODEL_NAME.getValue(), modelName)
              .setAttribute(SemanticConvention.SPAN_TYPE.getValue(), SpanType.CORE_LLM.getValue())
              .setAttribute(SemanticConvention.INPUT.getValue(), "{\"input\":\"" + userInput + "\"}")
              .setAttribute(SemanticConvention.INPUT_RAW.getValue(), userInput)
              .setAttribute(SemanticConvention.INPUT_TOKENS.getValue(), "45")
              .setAttribute(SemanticConvention.OUTPUT.getValue(), "{\"output\":\"" + modelOutput + "\"}")
              .setAttribute(SemanticConvention.OUTPUT_RAW.getValue(), modelOutput)
              .setAttribute(SemanticConvention.OUTPUT_TOKENS.getValue(), 128)
              .setAttribute(SemanticConvention.OUTPUT_PRICE.getValue(), 2.3)
              .setAttribute(SemanticConvention.PRICE_UNIT.getValue(), 0.1)
              .setAttribute(SemanticConvention.INPUT_PRICE.getValue(), 34.5)
              .setAttribute(SemanticConvention.CURRENCY.getValue(), "USD")
              .setAttribute("gen_ai.metrics.latency.first_token", 150.0)
              .setAttribute("gen_ai.metrics.latency.model_total", 2500.0)
              .setAttribute("gen_ai.metrics.latency.span", 2500.0)
              .setAttribute("gen_ai.metrics.request.model_request_count", 1)
              .setAttribute("gen_ai.metrics.usage.currency", "USD")
              .setAttribute("gen_ai.metrics.usage.input_cost", 6.576)
              .setAttribute("gen_ai.metrics.usage.input_tokens", 822)
              .setAttribute("gen_ai.metrics.usage.output_cost", 2.48)
              .setAttribute("gen_ai.metrics.usage.output_token_tps", 27.80269058295964)
              .setAttribute("gen_ai.metrics.latency.agent_total", 2500.0)
              .setAttribute("gen_ai.metrics.usage.output_tokens", 124)
              .setAttribute("gen_ai.metrics.usage.total_cost", 6.2)
              .setAttribute("gen_ai.metrics.usage.total_tokens", 946)
              .setAttribute("gen_ai.span_type", "core_llm")
              .setAttribute("gen_ai.metrics.request.agent_request_count", 1)
              .startSpan();

          try (Scope llmEndScope = llmEndSpan.makeCurrent()) {
            Thread.sleep(200); // 模拟LLM处理时间
            llmEndSpan.setStatus(StatusCode.OK, "");
          } finally {
            llmEndSpan.end();
          }

        } finally {
          llmStartSpan.setStatus(StatusCode.OK, "");
          llmStartSpan.end();
        }

      } finally {
        workflowSpan.end();
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
