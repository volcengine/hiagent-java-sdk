## 为什么

当前 Java SDK 的多个客户端请求层没有统一暴露 `X-Trace-Product-Code`，调用方无法稳定指定 IAM、Observe 和下游服务使用的产品范围。服务端已按产品隔离权限、Workspace、资源和 Trace 数据，现在需要在不破坏现有构造函数的前提下补齐客户端级配置。

## 变更内容

- 在 Java SDK 各客户端配置层增加可选 `productCode`。
- 在共享请求构造层注入 `X-Trace-Product-Code`。
- 保留显式请求 Header 的优先级和未配置时的兼容行为。
- 覆盖 `hiagent-api`、`hibot`、`hiagent-observe` 三类请求链路。
- 为签名请求、Observe OTLP 导出和产品编码覆盖行为增加测试。
- 不修改 `CreateApiTokenRequest` 的请求体结构。

## 功能 (Capabilities)

### 新增功能

- `java-sdk-product-code`: Java SDK Client 级产品编码配置与请求 Header 传播。

### 修改功能

无。

## 影响

- 影响 `hiagent-api`、`hibot`、`hiagent-observe` 的公共配置和 HTTP 请求构造。
- 可能调整 Maven 中 OkHttp/Kotlin 依赖的统一管理，但不改变对外 API 路径和签名协议。
- 需要新增本地 Mock 请求测试和配置校验测试。
