## 背景

服务端使用 `X-Trace-Product-Code` 作为跨服务产品选择契约。`llmops-observe` 通过公共 Echo middleware 将该 Header 写入 Context，再由下游 Header helper 转发。`CreateApiTokenRequest` 当前只包含 `WorkspaceID` 和 `CustomAppID`，所以 Observe 的 productCode 应用于 OTLP Trace 导出请求。

Java SDK 有三条不同请求链路：

```text
hiagent-api      BaseApiClient / volcengine ApiClient
hibot            RequestExecutor
hiagent-observe  ObserveClient + AuthenticatedOtlpSpanExporter
```

不能用一个 HTTP 实现强行覆盖三者，但可以统一配置语义和 Header 优先级。

## 方案

每个客户端保留原有构造函数，并增加带 `productCode` 的配置入口。值在构造时完成 trim：null、空串和纯空白视为未配置；包含 HTTP 控制字符时立即拒绝。

共享请求层按以下规则注入：

```text
configuredProductCode 非空
且请求没有 X-Trace-Product-Code
  => 添加 X-Trace-Product-Code
否则
  => 保留请求现有值
```

### hiagent-api

`BaseApiClient` 的 OkHttp 请求通过统一 Header hook 注入。生成式 TOP Client 使用 `com.volcengine.ApiClient`，Header 必须在 canonical request 和签名生成前加入；如果底层 SDK 不提供公共 hook，应增加受控扩展点，避免修改每个生成方法。

`hiagent-api` 下的可运行 examples 统一使用 SDK 自己的 `com.volcengine.hiagent.api.ApiClient`，通过 `HIAGENT_PRODUCT_CODE` 注入可选配置。Chat examples 通过 `ChatClient` 的兼容构造函数传递同一个环境变量。CreateApiToken 等请求仍不把 productCode 写入请求体。

### hibot

`HibotConfig` 保存可选 productCode，`RequestExecutor` 在创建 TOP 请求并计算签名之前注入 Header。所有 Agent、Session、Skill、MCP、Resource 等服务请求共用该逻辑。

### hiagent-observe

`Client` 将 productCode 传给 `AuthenticatedOtlpSpanExporter`。Token bootstrap 保持现有 JSON 请求体，不增加字段；OTLP exporter 创建和 Token 刷新时都重新设置 `X-Trace-Product-Code`。

## 兼容性

- 原有构造函数继续可用，默认不发送产品 Header。
- SDK 不硬编码 `hiagent`，避免多产品场景下静默路由错误。
- 显式请求 Header 优先于 Client 配置。
- 不改变 API 路径、TOP Action/Version 或流式响应协议。

## 测试方案

- 使用 MockWebServer/Interceptor 检查 Header 是否发送、是否覆盖和是否缺省。
- 检查 `hiagent-api` examples 的 TOP 客户端初始化不会退回到原始 `com.volcengine.ApiClient`，Chat examples 能传递缺省或显式 productCode。
- 对 TOP 请求验证 Header 在签名前进入 canonical request。
- 对 Observe 验证 Token 请求体没有 ProductCode，OTLP 导出请求包含 Header，刷新 Token 后仍保留 Header。
- 验证非法 Header 字符在发起网络请求前失败。
- 最后执行全仓 Maven 构建，并进行一次带明确产品编码的真实环境 smoke test。

## 风险

- `okhttp` 与 `okhttp-jvm` 是不同 Maven artifact，不能只靠一个变量替换版本；需要保持实际 artifact 与调用方兼容。
- Header 如果注入晚于签名会导致 TOP 鉴权失败。
- 服务端能推导默认产品时，缺少 Header 可能暂时不报错，测试必须覆盖显式多产品请求。
