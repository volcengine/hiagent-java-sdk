## 背景

### UP 服务端如何验签

UP 不比对客户端传来的签名串，而是用**自己收到的请求**重算一遍，再与 `Authorization` 里的签名比较：

```text
Method        请求方法
Path          收到的 URL.Path（已解码）
Query         收到的全部 query（含 Action/Version），解码后按参数名排序，
              名和值只保留 A-Z a-z 0-9 - _ . ~，其余按 UTF-8 字节写成大写 %XX，空格为 %20
Headers       按 Authorization 里 SignedHeaders 的顺序取 "name:去首尾空白的值"
SignedHeaders 原样
BodyHash      X-Content-Sha256 头；没有这个头时服务端读完整个请求体自己算
```

签名串为 `HMAC-SHA256\n<X-Date>\n<yyyyMMdd>/<region>/<service>/request\n<hex(SHA256(canonical))>`，密钥由 SK 依次对日期、region、service、`request` 做 HMAC-SHA256 派生。服务端按 Credential 里的 service 原样派生，直连 UP 时 service 取什么值都不影响结果。

### core 的 `buildRequest` 为什么签不对

`volcengine-java-sdk-core` 0.2.30（2.0.x 同样复现）的 `ApiClient.buildRequest` 是为"参数在 JSON 请求体、query 只有 Action/Version"的接口设计的，与 UP 文件接口"请求体是文件、参数在 query"的形态不符：

| 环节 | core 的行为 | 后果 |
| --- | --- | --- |
| query | 拼 URL 用完整参数，签名前 `queryParams.clear()` 只留 Action/Version | `Id`、`Expire`、`path`、`sha256` 未签 |
| 请求体哈希 | 对 `buffer.readUtf8()` 的结果算 SHA256 | 二进制文件哈希被改写 |
| path | canonical URI 固定 `/` | endpoint 带路径时失配 |
| 非 JSON body | `byte[].toString()` 作为空名参数拼进 URL | URL 多出 `=[B@…` |
| 算法 | 只有 HMAC-SHA256 | 国密环境失配（本次不处理） |

任何一处单独都会导致验签失败。

## 方案

### 原则：签名只对最终要发出的 `Request` 做

这次缺陷的本质是"签名材料"和"发出的请求"是两份东西。`UpSigner` 只提供一个入口：

```java
Request sign(Request request, Instant now)
```

- path 取 `request.httpUrl()` 解码后的路径。
- query 取 `request.httpUrl().encodedQuery()`，解码后按服务端规则重新编码、排序。
- payload hash 取请求自带的 `X-Content-Sha256` 头；没有这个头直接拒绝签名，避免任何"另算一份哈希"的路径。
- 只添加 `X-Date`、`Authorization`，使用 STS 凭证时添加 `X-Security-Token`（不参与签名，与 core 行为一致）。

签名器拿不到请求以外的输入，所以"签的和发的不一致"在结构上不会出现。

### `UpClient` 的请求构造

五个方法共用一个私有的 `execute`：

```text
URL      scheme://<endpoint 去掉末尾 />/?Action=<A>&Version=2023-08-01&<参数...>
         参数名和值按服务端规则编码后写入 URL（addEncodedQueryParameter），
         URL 上的字节与 canonical query 使用同一种编码
Headers  Content-Type、X-Content-Sha256、X-Trace-Product-Code（已配置时）
         JSON 请求另带 Accept: application/json（与旧版发出的一致）
         以及 core ApiClient 的默认 Header（User-Agent 等，沿用 processHeaderParams）
Body     uploadRaw：RequestBody.create(MediaType, File)，OkHttp 按文件流式写出
         其余：Gson 序列化后的 UTF-8 字节，Content-Type 为 application/json; charset=utf-8，
         与 core 现在发出的一致
签名     UpSigner.sign(request, Instant.now())
响应     沿用 core 的 handleResponse；出错时与 core 的 execute 一样，把响应体里的
         ResponseMetadata 挂到 ApiException 上；网络异常保留 cause
         download 成功时响应体是文件，不走 handleResponse；失败时 UP 仍返回 JSON 错误
         信封，同样把状态码、响应体和 ResponseMetadata 挂到 ApiException 上，
         错误信息仍为 "Download failed with status: N"
```

scheme、endpoint、region、`OkHttpClient`（超时、代理）全部取自调用方传入的 `com.volcengine.ApiClient`，不新增配置项。凭证与 core 签名器的取法一致：先用静态 `Credentials`，没有时用 `CredentialProvider`。

AK 或 STS token 里如果有 OkHttp 不允许出现在 Header 中的字符（`<= 0x1f` 或 `>= 0x7f`，包括 `é` 这类非 ASCII 字符），OkHttp 设置 Header 时抛出的异常会把整个值写进信息里。`UpSigner` 在设置 `Authorization` 和 `X-Security-Token` 时捕获这个异常，换成不含凭证值的信息，也不把原异常作为 cause。SDK 不另写一套字符规则，拒绝条件始终与 OkHttp 一致。token 首尾的空白会被去掉（例如从文件读入时带的换行）。

### 请求体哈希

- `uploadRaw`：调用方设置了 `Sha256` 则去掉首尾空白后原样使用（服务端也按这个值校验文件）；未设置则先流式读一遍文件计算 SHA256，再流式发送。IO 两遍，内存 O(1)。不能"边读边算"：哈希要写进请求头并参与签名，二者都先于请求体发出。
- JSON 请求：对序列化后的字节计算。
- 每个请求都发送 `X-Content-Sha256`。不发时服务端会在鉴权中间件里读完整个请求体来算哈希，大文件会占满服务端内存。

### 不签 `host`

经域名或七层网关访问时，网关可能改写 Host（例如去掉非默认端口），签了 `host` 就会失配。UP 不要求签 `host`，SignedHeaders 固定为 `x-content-sha256;x-date`。

### `download` 落盘

保持原先的做法：先用 `bytes()` 读完整个响应，再 `Files.write` 到 `saveTo`。整个响应读完之前不碰目标文件，所以传输失败或中途截断时不留下文件，已有文件保持原样；`saveTo` 是符号链接时，`Files.write` 写入它指向的文件。本次只改两处：

- `saveTo` 没有父目录（只有文件名）时不再创建父目录，按当前目录写入，不再 NPE。
- 非 2xx 时读取 UP 返回的 JSON 错误信封，见上文"响应"。

流式写文件不在本次范围，原因见 proposal 的非目标。

### 与 `hibot` 签名器的关系

仓库里会有两份签名代码：`hibot/internal/Signer` 与 `hiagent-api` 的 `UpSigner`。`hibot` 是独立模块，不依赖 `hiagent-api`，这与 `ProductCodeSupport` 的处理方式一致。`UpSigner` 只从 `hibot` 移植了 query 规范化与编码两个函数（`canonicalQueryString`、`signEncode`），没有原样移植整个类，原因：

- `hibot` 的 `Signer` 固定签 `host` 和 `content-type`，经网关改写 Host 时会失配。
- 它要求传入完整请求体字节数组，与流式上传冲突。
- 它的入参是 `(method, URI, headers, body)`，仍然是"另传一份签名材料"的形状。

两份代码中容易出错的 query 编码，由下文的服务端样例固定。

### 被否决的方案

- **修改 core 或向 core 打补丁**：core 是外部依赖，对其他 TOP Client 行为影响面大。
- **只改 `uploadRaw` 和 `downloadKey`**：`download`、`delete` 在 endpoint 带路径时同样失败，productCode 在四个方法上都缺失，将来支持国密也需要只改一处。

## 兼容性

- 公开类、方法签名、请求/响应模型均不变。
- 未配置 productCode 时不发送 `X-Trace-Product-Code`。
- Credential scope 的 service 由 `app` 改为 `up`，见 proposal。
- `longLive` 不再自动重试，见 proposal。它原先支持的 `ResponseMetadata`、异常 cause 和 `CredentialProvider` 都保留，并且五个方法现在一致支持。
- 依赖不变，仍为 Java 8 + core 0.2.30 自带的 OkHttp 2.7.5。

## 测试方案

三层验证，前两层离线运行：

1. **服务端样例（主验收）**：用 UP 服务端验签所用的签名库，按服务端从请求中取材的方式（解析 query、取 `URL.Path`、读 `X-Content-Sha256`）离线生成固定样例，提交为 `hiagent-api/src/test/resources/up-signature-vectors.json`，内含 canonical request、待签串和签名。测试运行时不依赖 Go。生成程序依赖服务端的非公开模块，不随 SDK 提交。样例覆盖：文本与含非法 UTF-8 的二进制请求体、根路径与 `/up/` 路径、含 `/`、空格、`+`、`=`、`&`、`!'()*`、中文的 query 值、大小写混合的参数名排序、https 域名。
2. **签的就是发的**：本地 `HttpServer` 接收 `UpClient` 五个方法实际发出的请求，用已被样例验证过的 `UpSigner` 按**收到的** path、query、Header 重算签名，必须等于收到的 `Authorization`；同时校验收到的 `X-Content-Sha256` 等于收到的请求体的真实 SHA256。另外覆盖 productCode 注入与缺省、STS token 与 `CredentialProvider`、`longLive` 不带 `Id=test`、URL 无空名参数、endpoint 末尾有无 `/`、错误响应带 `ResponseMetadata`、网络异常保留 cause。`download` 覆盖：内容正确、自动创建目录、错误信封、响应中途截断时旧文件不变、只有文件名。截断用例已做变异验证：把实现改成边读响应边写目标文件，用例会失败。
3. **真实环境**：在一个关闭匿名访问的 UP 上跑一次上传、长期化、获取下载 Key、下载、删除，作为补充。已在 UP v3.2.1（`enableIsAnonymous: false`）上通过。`Download` 除 `Key` 外还需要 `Path` 或 `Sha256`，UP 用它们校验 Key 与文件是否对应。

## 风险与边界

- 只在直连 UP 或中间代理不改写路径时成立；改写路径的代理需要客户端签它看不到的路径，不支持。
- 服务端会把 `application/x-www-form-urlencoded` 请求体当作表单参数并入 query，`uploadRaw` 不支持以该类型上传。
- 国密环境不支持，见非目标。
