## 为什么

`fix-up-client-signature` 解决了签名问题，但 `UpClient` 五个方法的请求和响应模型与 UP 的接口契约仍有差异。对照的基准是 UP `v3.1.0`、`release-v3.1.1` 与 `main` 的 `idl/upload.thrift`、`idl/download.thrift` 和 `idl/api-contract.golden`，这五个 Action 的结构体在三处完全一致。差异有：

- `LongLiveResponse.size` 是 `Integer`，UP 返回 `int64`。文件不小于 2 GiB 时，Gson 解析失败，`longLive` 抛异常，但服务端此时已经写入了长期关联。
- `DownloadRequest.saveTo` 是本地保存路径，却被序列化进 `Download` 的 JSON 请求体，把调用方的本地路径发给了 UP。UP 不读这个字段。
- 模型缺少 UP 已有的字段，调用方无法使用：
  - 请求：`UploadRaw` 的 `Filename`、`ImageCompress`；`LongLive` 的 `Filename`；`Download` 的 `Sha256`、`ID`、`Range`、`ImageCompress`、`Filename`。
  - 响应：`UploadRaw` 的 `PresignKey`；`LongLive` 的 `Sha256`、`ShortLink`；`DownloadKey` 的 `Size`、`ContentType`。
- `uploadRaw` 把调用方的 `Sha256` 原样放进 `X-Content-Sha256`，`downloadKey` 原样放进 query。UP 生成和保存的都是小写十六进制，按字符串比较，大写会被判为不匹配或查不到。
- `downloadKey` 的 query 参数名是 `path`、`sha256`，UP 契约是 `Path`、`Sha256`。现在能用，只是因为 echo 绑定 query 时有不区分大小写的兜底。
- `UploadRaw` 的 `Id` 是 UP 的必填项，SDK 不检查，调用方要等整个文件发出去后才收到 400。
- `Expire` 的注释只有示例，没有说明格式（Go duration）和"不传、空串、`0`、`-1` 表示不过期"。

## 变更内容

- `LongLiveResponse` 内部改用 `Long` 保存 `Size`，解析不再失败。保留原有 `Integer getSize()`、`setSize(Integer)` 并标记 `@Deprecated`；`getSize()` 在值超出 `int` 范围时抛 `ArithmeticException`。新增 `getSizeAsLong()`、`setSizeAsLong(Long)`。
- `DownloadRequest.saveTo` 改为 `transient`，不再序列化。
- 按 UP 契约补齐上面列出的请求和响应字段。`UploadRaw` 的 `Filename`、`ImageCompress` 走 query，`LongLive` 和 `Download` 的新字段走 JSON 请求体，与 UP 的绑定方式一致。
- SDK 发出的所有 SHA-256 去首尾空白并转小写：`uploadRaw` 的 `X-Content-Sha256`、`downloadKey` 的 `Sha256` query、`download` 和 `delete` 请求体里的 `Sha256`。请求体在序列化后的 JSON 树上改写，不修改调用方的请求对象。只规范化上传会造成不一致：大写哈希的上传从失败变成成功，同一个哈希的 `delete` 却在大小写敏感的库上什么也不删、也不报错。
- `downloadKey` 的 query 参数名改为 `Path`、`Sha256`；`DownloadKeyRequest` 的序列化名同步改为 `Path`、`Sha256`。
- `uploadRaw` 在 `Id` 为 `null` 或空串时直接抛 `ApiException`，不发请求。判断条件与 UP 的 `validate:"required"` 一致。
- 补全相关字段的注释：`Expire` 格式与不过期写法、`ContentType` 建议填真实类型、`Range` 只保存所请求的片段。

## 非目标

- 不改 `uploadRaw` 未设置 `ContentType` 时的默认值 `application/octet-stream`。按扩展名推断类型，会让 UP `contentType.mustMatch` 开启时原本能上传的文件变成 400（UP known-issues 实测：线上默认配置下声明 `video/x-msvideo`、`video/avi` 的 AVI 被拒，只有 `application/octet-stream` 通过）。只在注释里建议调用方填真实类型。
- 不补 `Download` 的 `IsAnonymous`。匿名放行是部署级开关，SDK 的请求总是签名，不引导调用方依赖它。
- 不新增 Action（分片上传、Batch、Presign、Blob、Artifact）、`InputStream` 上传、下载流式落盘、国密。
- 不改签名、URL 形状以外的请求行为，不改 `hibot`。

## 功能 (Capabilities)

### 新增功能

无。

### 修改功能

- `java-sdk-up-client`: 增加"模型与 UP 接口契约一致"等要求；修改 `fix-up-client-signature` 的 "Payload hash matches the body"：调用方哈希由"原样发送"改为"去空白并转小写后发送"。

## 影响

- 代码：`hiagent-api` 的 `UpClient` 与 `model` 下 `UploadRawRequest`、`UploadRawResponse`、`LongLiveRequest`、`LongLiveResponse`、`DownloadKeyRequest`、`DownloadKeyResponse`、`DownloadRequest`；`UpClientTest`。
- 公开 API：只有新增方法和 `@Deprecated` 标记，没有删除或改签名，二进制与源码兼容。
- 线上行为：
  - `download` 的请求体不再含 `SaveTo`。
  - `downloadKey` 的 query 参数名由 `path`、`sha256` 变为 `Path`、`Sha256`，UP 按精确名匹配，结果不变。
  - 调用方传大写哈希时，SDK 发出小写。原先这类请求会被 UP 拒绝或查不到。
  - `uploadRaw` 缺 `Id` 时在本地失败，不再发出请求。
- 发版说明需要写明的行为变化：
  - `uploadRaw` 缺 `Id` 时抛出的 `ApiException` 状态码为 0、没有 `ResponseMetadata`，原先是 UP 返回的 400。
  - `DownloadRequest.saveTo` 是 `transient`，用 Gson 反序列化含 `SaveTo` 的 JSON 时同样会被忽略。
  - `LongLiveResponse.getSize()` 在 `Size` 超出 `int` 时抛 `ArithmeticException`；用反射序列化该对象的框架（如 Jackson）会因此失败，并会看到新属性 `sizeAsLong`。
  - `DownloadKeyRequest` 的 Gson 序列化名由 `path`、`sha256` 变为 `Path`、`Sha256`，只影响自行序列化这个类的调用方。
- 发布：随下一个 3.1.x 补丁版本发布。
