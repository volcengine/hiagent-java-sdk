## 背景

### UP 如何绑定这五个 Action 的参数

UP 不按路径路由，`?Action=<Name>` 反射到同名 handler（`pkg/server/middlewares/dispatcher/dispatcher.go:RefAction`）。绑定顺序固定：

1. 请求体：Action 不在 `skipParseBody` 中时，用 echo `DefaultBinder.BindBody` 按 JSON 解析。`UploadRaw`、`DownloadKey` 在 `skipParseBody` 中，请求体不参与绑定。
2. query：`BindQueryParams`，按结构体的 `query` tag 取值。echo 先按精确名找，找不到再不区分大小写找。
3. header：`BindHeaders`，按 `header` tag 取值；请求里没有该 header 时不覆盖前面绑定的值。
4. `c.Validate` 按 `validate` tag 校验，失败返回 400。

五个 Action 的契约（`idl/api-contract.golden`，UP `v3.1.0`、`release-v3.1.1`、`main` 一致）：

| Action | 字段（来源） |
| --- | --- |
| UploadRaw | `X-Content-Sha256`/`X-Up-Sha256`（header）、`Content-Type`（header）、`Expire`、`Id`（必填）、`Filename`、`ImageCompress`（query）；响应 `Path`、`Size int64`、`Sha256`、`ShortLink`、`PresignKey` |
| LongLive | `Path`、`ID`、`Filename`（JSON）；响应 `Path`、`Size int64`、`Sha256`、`ShortLink` |
| DownloadKey | `Path`、`Sha256`（query）；响应 `Key`、`Size int64`、`ContentType` |
| Download | `Path`、`Sha256`、`Key`、`IsAnonymous`、`ID`、`Range`、`ImageCompress`、`Filename`（JSON，`Range` 也可走 header）；响应是文件流 |
| Delete | `Sha256`（必填，64 位）、`Id`（必填，8 至 256 位）（JSON）；响应为空 |

`LongLive` 的 `ID` 在 SDK 里序列化为 `Id`，Go 的 `encoding/json` 按字段名不区分大小写匹配，能正确绑定，本次不改。

### 哈希比较

`UploadRaw` 用 `hex.EncodeToString` 计算文件哈希（小写），与请求哈希做字符串比较，不一致返回 `sha256 not match`。`DownloadKey`、`Download` 按哈希查库，库里存的也是小写。

## 决策

### `LongLiveResponse.size`：内部换成 `Long`，旧访问器保留

直接把 `getSize()` 的返回类型改成 `Long` 会破坏二进制兼容（方法描述符变化）和源码兼容（`int s = resp.getSize()` 无法编译），不适合补丁版本。

做法：

- 字段改为 `Long`，Gson 按字段解析，不再因超出 `int` 而失败。
- `Integer getSize()` 保留，标 `@Deprecated`，用 `Math.toIntExact` 转换：值在 `int` 范围内时行为不变，超出时抛 `ArithmeticException`。原先这种情况在解析阶段就失败，现在只有读旧访问器时才失败。
- `setSize(Integer)` 保留，标 `@Deprecated`。
- 新增 `Long getSizeAsLong()`、`setSizeAsLong(Long)`。不重载 `setSize(Long)`，否则 `setSize(null)` 会变成有歧义的调用，破坏源码兼容。
- 构造函数 `LongLiveResponse(String, Integer)` 保留。不新增 `(String, Long)` 重载，原因同上。

下一个次版本再把 `getSize()` 改为 `Long`、删除 `*AsLong`。

### `saveTo` 用 `transient`

`UpClient` 用 `ApiClient.getJSON()` 序列化请求，core 的 Gson 保持默认排除 `transient` 字段。改字段修饰符是最小改动，不需要在 `UpClient` 里复制请求对象。测试直接检查发出的请求体。

### 新字段放在哪里

与 UP 的绑定方式一致，不做额外映射：

- `UploadRaw` 的请求体是文件，新字段只能走 query：`Filename` 原样，`ImageCompress` 写成 `true`/`false`。都只在非 `null` 时发送。
- `LongLive`、`Download` 的新字段加在模型上，由 Gson 写进 JSON 请求体。序列化名与 UP 的 `json` tag 一致：`Filename`、`Sha256`、`ID`、`ImageCompress`。

### 不暴露 `Download.Range`

PR #41 曾加入 `Range`，合入后 review 指出它与 `download` 的落盘方式冲突，在 3.1.1 发版前移除：

- `download` 读完响应后用 `Files.write` 写 `saveTo`，会先清空已有文件。
- `Range` 的主要用途是 `bytes=N-` 续传。对同一个 `saveTo` 续传，结果只剩尾部，前 N 字节丢失，没有任何报错。
- 只在注释里提醒，等于留一个已知的坑；拒绝"`saveTo` 已存在时带 `Range`"又与 `download` 本来就覆盖已有文件的语义不一致。
- 正确的续传要按 `Content-Range` 的偏移写入、校验已有文件长度，与"失败不留文件、不破坏已有文件"所需的临时文件和原子替换是同一套机制，归入下载流式落盘的后续变更。

UP 读 JSON 里的 `Range` 这一点（dispatcher 先 `BindBody`，请求没有 `Range` 头时 `BindHeaders` 不覆盖，handler 把 `req.Range` 交给 `parseRange`）留给那个变更在真实环境验证。

### 哈希规范化覆盖 SDK 发出的所有 SHA-256

规则：去首尾空白，`toLowerCase(Locale.ROOT)`。

- `uploadRaw` 的 `X-Content-Sha256`、`downloadKey` 的 `Sha256` query 由 `UpClient` 自己写出，直接规范化。
- `download`、`delete` 的 `Sha256` 在 JSON 请求体里。`toJson` 先用 core 的 Gson 生成 JSON 树（`toJsonTree`），改写顶层的 `Sha256` 成员，再序列化树。调用方的请求对象不变，`X-Content-Sha256` 按最终字节计算。`toJsonTree` 与 `toJson(Object)` 使用同一套 Gson 配置（含省略 `null` 字段），输出形状不变。

只规范化上传会制造不一致：大写哈希的 `uploadRaw` 由失败变为成功，同一调用方随后用这个哈希 `delete`，UP 的 `DeleteFileAssociates` 在没有匹配行时也返回成功，在大小写敏感的库上就成了静默不删。所以要么全部规范化，要么都不做，这里选全部。

### `downloadKey` 的 query 参数名

改为契约中的 `Path`、`Sha256`。两个名字都签进 canonical query，签名不受影响；UP 走精确匹配，不再依赖 echo 的大小写兜底。

### `Id` 的本地检查

UP 对 `Id` 的校验是 `validate:"required"`，对字符串即"非空"。SDK 用同一条件（`null` 或 `isEmpty()`），不额外拒绝空白字符串，避免和服务端口径不一致。检查放在读文件、算哈希之前。

## 风险

- `downloadKey` 的 query 参数名变化：UP 历来按不区分大小写的兜底接受小写名，大写名是精确匹配，不存在只认小写的 UP 版本。
- 调用方若自己用 Gson 序列化或反序列化 `DownloadRequest` 并依赖 `SaveTo`，会看不到这个字段（`transient` 对两个方向都生效）。`saveTo` 只在本地有意义，影响可以接受。
- 签名用例用 `UpSigner` 对收到的请求重签再比对，证明的是"签的就是发的"；与 UP 验签器一致性由 `up-signature-vectors.json` 保证。新增 query 参数的字符类别（空格、`+`、`=`、`&`、中文）已在样例中覆盖。

## 验证

- `UpClientTest` 的本地 `HttpServer` 覆盖：新 query 和 JSON 字段确实发出且签名正确；`ImageCompress=false` 照发；`Size = 3000000000` 能解析；请求体不含 `SaveTo`；上传、`downloadKey`、`download`、`delete` 的大写哈希发出时为小写且请求对象不变；缺 `Id` 时在读文件前失败、空白 `Id` 照发；`download` 请求体不含 `Range`；新响应字段能解析。
- `UpClientRealEnvE2eTest` 增加 `Filename` 与新响应字段（`PresignKey`、`Size`、`Sha256`）的断言，在真实 UP 上补充验证。上传之后的所有断言都放在 `try` 里，任何一个失败，`finally` 都会删除已上传的文件。
- 先写测试确认失败，再改实现。
- JDK 8 上执行 `mvn -B -pl hiagent-api -am test`。
