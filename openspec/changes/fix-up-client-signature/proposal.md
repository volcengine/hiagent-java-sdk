## 为什么

`UpClient` 签名用的请求和实际发出的请求不是同一份。UP 服务端按自己收到的请求重算签名，所以只要 UP 关闭匿名访问（这是当前的安全默认配置），`uploadRaw`、`downloadKey`、`longLive` 必然验签失败；endpoint 带路径（如 `/up`）时，五个公开方法全部失败。现场唯一的绕过办法是打开全局匿名，这是安全上最差的结果。

根因在 `volcengine-java-sdk-core` 的通用 `ApiClient.buildRequest`，`UpClient` 直接调用了它：

1. 先用完整参数拼 URL，签名前又 `queryParams.clear()`，只把 `Action`、`Version` 加回去签。`uploadRaw` 的 `Id`/`Expire`、`downloadKey` 的 `path`/`sha256`、`longLive` 的 `Id` 都发出去了，但没进签名。
2. 签名用的请求体哈希是 `buffer.readUtf8()` 之后的字符串哈希。二进制文件里的非法 UTF-8 字节被替换，哈希与真实文件不一致。
3. canonical URI 固定为 `/`，endpoint 带路径时与实际路径不一致。

此外还有几处同一位置的缺陷：

- `uploadRaw`、`downloadKey`、`download`、`delete` 绕过了 `com.volcengine.hiagent.api.ApiClient.buildCall`，从不发送 `X-Trace-Product-Code`。
- `longLive` 硬编码了 `Id=test` query，服务端不读它，但它让签名失配。
- `uploadRaw` 先 `readAllBytes` 把整个文件读进内存；`download` 把整个响应读进内存，`saveTo` 只写文件名时还会因 `getParent()` 为 null 抛 NPE。
- 非 JSON 请求体时，core 把 `byte[].toString()` 当成空名参数拼进 URL（形如 `=[B@…`）。

## 变更内容

- `UpClient` 的五个公开方法不再经过 core 的 `buildRequest`，改为自行构造 OkHttp `Request`，由新增的包内类 `UpSigner` 对**最终要发出的 `Request`** 签名。
- 签名覆盖实际 path 与全部 query；SignedHeaders 固定为 `x-content-sha256;x-date`，不签 `host`；Credential scope 的 service 为 `up`。
- 每个请求都发送 `X-Content-Sha256`，签名里的 payload hash 取同一个值：调用方提供了 `Sha256` 就用调用方的值，否则 SDK 先流式扫描一遍文件计算。
- `uploadRaw` 流式发送文件；`download` 流式写入同目录临时文件，成功后原子替换目标文件。
- 删除 `longLive` 的 `Id=test`。
- 五个方法统一按 Client 配置注入 `X-Trace-Product-Code`。
- 根 `pom.xml` 的 `pluginManagement` 固定 `maven-surefire-plugin` 3.2.5，所有模块统一使用能识别 JUnit 5 的版本。
- 不修改 core，不修改任何公开方法签名。

## 非目标

- 国密（HMAC-SM3 签名、SM3 文件哈希）。UP 的签名算法与文件哈希算法是两个独立开关，SDK 无法探测，需要单独立项设计配置项与 SM3 实现。
- 中间代理改写路径（如 ingress `rewrite-target`）的部署形态。UP 按收到的路径验签，客户端无法签对。
- `InputStream` 上传重载。
- `UpClient` 以外的 TOP Client。

## 功能 (Capabilities)

### 新增功能

- `java-sdk-up-client`: `UpClient` 的请求签名、请求体哈希与文件流式传输。

### 修改功能

无。

## 影响

- 代码：`hiagent-api` 的 `UpClient`，新增包内类 `UpSigner`；新增测试与测试数据；根 `pom.xml`。
- 线上行为：
  - 发出的 URL 不再带空名参数 `=[B@…`，endpoint 末尾多余的 `/` 不再产生 `//` 路径；其余 URL 形状不变。
  - Credential scope 的 service 由 `app` 改为 `up`。直连 UP 时 service 不参与验签结果（服务端按调用方给的 service 派生密钥）；改为 `up` 是为了与 TOP 网关的 `up` 服务及 `hibot` 保持一致。
  - `longLive` 原先经过 core 的 `InterceptorContext` 自动重试，现在与其余四个方法一样不自动重试。core 的重试循环会用它自己的签名器重新签名，无法与 `UpSigner` 组合。
- 发布：从 `main` 发布 3.1.1。`hiagent-api` 自 1.0.0 以来公开 API 只有新增、依赖未变，未配置 productCode 时不发送该 Header，1.0.x 用户可直接升级。
