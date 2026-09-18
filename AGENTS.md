# HiAgent Java SDK 开发约束

## 范围

本文件约束 `hiagent-java-sdk` 及其模块：`hiagent-api`、`hiagent-observe`、`hibot`、`hiagent-chat`、`hiagent-components` 和 `hiagent-tool`。

## productCode 契约

- 产品编码 Header 固定为 `X-Trace-Product-Code`。
- `productCode` 应配置在 Client/Config 层，由共享请求层注入，不能给每个 API 方法重复增加入参。
- 显式请求 Header 优先于 Client 默认值；SDK 不得覆盖调用方明确传入的产品编码。
- 未配置时保持兼容，不主动写入 Header，也不在 SDK 内硬编码默认产品。
- 不得把 `productCode` 加入 `CreateApiTokenRequest`，除非服务端 IDL 先增加该字段。

## 模块边界

- `hiagent-api` 的 legacy ChatClient、生成式 TOP Client 使用不同 HTTP 栈，必须分别验证。
- `hibot` 的 Header 必须在签名生成前注入。
- `hiagent-observe` 的 Token 创建请求和 OTLP Trace 导出请求分开处理：产品编码属于 Trace 导出 Header，不属于 Token 请求体。
- TOP 网关路径、observe 直连路径和 `/api/proxy/api/v1` App API 路径不可混用；修改前必须从源码确认最终 URL。

## 验证要求

- Java 修改至少执行 `mvn clean test` 或受影响模块的窄范围 Maven 测试。
- Header、签名和响应协议优先使用本地 Mock/Interceptor 验证；真实环境 smoke test 只能作为补充。
- 日志和测试不得输出 AK、SK、API Key、Bearer Token 或完整敏感响应。
- 所有行为变更必须先更新 `openspec/changes/` 下的 proposal、design、spec 和 tasks，并通过 `openspec-cn validate`。
