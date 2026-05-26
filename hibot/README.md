# Hibot Java SDK

Java 17+ client for the Hibot Managed Agent platform. Mirrors the Go SDK
(`go/hibot/v1`) resource layout and request/response semantics, signing every
request with the VOLC v4 algorithm.

## Coordinates

Planned: `com.volcengine:hibot-sdk` (Maven Central).

For now, build from source:

```bash
mvn -B -ntp clean install -DskipTests
```

Then depend on it from another local Maven module:

```xml
<dependency>
  <groupId>com.volcengine</groupId>
  <artifactId>hibot</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Build & Test

```bash
mvn -B -ntp clean test
```

Current state: **33 tests, 0 failures, 0 errors** (all offline; tests spin up a
local `com.sun.net.httpserver.HttpServer` instead of hitting the live VOLC
gateway).

## Entry point

The top-level client is `com.volcengine.hibot.Hibot`:

```java
import com.volcengine.hibot.Hibot;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.v1.types.*;

HibotConfig cfg = HibotConfig.builder()
    .accessKeyId(System.getenv("VOLC_ACCESSKEY"))
    .secretAccessKey(System.getenv("VOLC_SECRETKEY"))
    .workspaceId(System.getenv("HIBOT_WORKSPACE_ID"))
    .baseUrl("https://open.volcengineapi.com")
    .region("cn-beijing")
    .build();

try (Hibot client = new Hibot(cfg)) {
    // Resource services
    var agent = client.v1.agents.create(V1AgentNewParams.builder()
        .name("demo-agent")
        .build());

    var session = client.v1.sessions.create(V1SessionNewParams.builder()
        .agentId(agent.getAgentId())
        .build());

    // Streaming chat: AutoCloseable + Iterable<V1SessionChatEvent>
    try (var stream = client.v1.sessions.chatStreaming(V1SessionChatParams.builder()
            .sessionId(session.getSessionId())
            .userMessage("hello")
            .build())) {
        for (V1SessionChatEvent event : stream) {
            System.out.println(event.getEvent() + " " + event.getDelta());
        }
        var finalMessage = stream.finalMessage();
        System.out.println("final: " + finalMessage.getContent());
    }
}
```

The blocking convenience method `client.v1.sessions.chat(...)` returns a single
`V1Message` directly (Go SDK parity).

## Resource layout

| Java service                         | Go counterpart            |
| ------------------------------------ | ------------------------- |
| `client.v1.agents`                   | `v1.AgentService`         |
| `client.v1.environments`             | `v1.EnvironmentService`   |
| `client.v1.models` / `.providers`    | `v1.ModelService`         |
| `client.v1.prompts`                  | `v1.PromptService`        |
| `client.v1.resources` / `.directories` | `v1.ResourceService`    |
| `client.v1.mcps`                     | `v1.MCPService`           |
| `client.v1.skills`                   | `v1.SkillService`         |
| `client.v1.sessions`                 | `v1.SessionService`       |
| `client.v1.uploads`                  | `v1.UploadService`        |

`com.volcengine.hibot.v1.BaseModels.ALL` exposes the same 100-entry catalog
as Go's `BaseModels` slice, with `BASE_MODEL_TYPE_*` and `BASE_MODEL_PROVIDER_*`
string constants.

## Streaming model

`V1ChatStream` is the Java analogue of Go's `*v1.ChatStream`:

- `boolean next()` advances to the next decoded SSE event (false on EOS).
- `V1SessionChatEvent current()` returns the latest event.
- `V1Message finalMessage()` returns the synthesized final assistant message.
- `String accumulate()` returns the concatenated `delta` text seen so far.
- `Throwable err()` exposes terminal stream errors after iteration.
- Implements `AutoCloseable` (use try-with-resources) and
  `Iterable<V1SessionChatEvent>` (works in `for-each`).

Internal HTTP read timeout for streams is 60 minutes (Go uses `context.Context`).

## Architecture & known deviations from the Go SDK

The Java SDK is a faithful port; a handful of intentional deviations are listed
below for reference.

- **Jackson lenient mode.** `internal/ResponseDecoder` configures the global
  `ObjectMapper` with `FAIL_ON_UNKNOWN_PROPERTIES=false` to match Go's
  `encoding/json` tolerance. `decodeChatEvent` additionally falls back to
  manual `code/Code` + `message/Message` lookups so that gateway error frames
  using either casing decode cleanly into `V1SessionChatError` (whose Java
  fields use lowercase to match the IDL).
- **Null suppression.** Every `@JsonInclude(JsonInclude.Include.NON_NULL)` on
  param types yields the same on-the-wire shape as Go's `omitempty`. Nested
  arrays/maps are emitted via service-level `LinkedHashMap` builders to keep
  PascalCase keys identical to TOP IDL.
- **No `context.Context`.** `RequestExecutor` uses `HttpClient` with explicit
  per-call timeouts; streaming reads use a 60-minute timeout.
- **Streaming iteration.** `V1ChatStream` exposes both an imperative
  `next()/current()` loop *and* `Iterable<V1SessionChatEvent>` so it plays well
  with `for-each` and try-with-resources. Calling `next()` after iterator
  exhaustion is a no-op that returns `false`.
- **Prompts payload nesting.** `PromptsService.create/update` wrap
  `Messages/Variables` under a `Prompt` envelope to match the TOP request
  schema; consumers keep using the flat `V1Prompt*Params` builders.
- **MCP endpoint field.** `V1MCP*Params.endpoint` maps to the wire field
  `Endpoint`; `V1MCP.URL` (response) preserves the upstream `URL` casing.
- **Agent dependencies pre-create.** `AgentsService.create` resolves the
  default environment via `EnvironmentsService.list` when the caller omits
  `EnvironmentID`, mirroring Go's auto-pick logic. Skills/MCPs/Resources are
  partitioned client-side into the corresponding TOP fields.
- **Uploads.** `UploadsService.uploadBlob` POSTs to the `/up` subpath with the
  `Filename` query parameter and forwards arbitrary `Content-Type`s, matching
  the Go SDK exactly.
- **Base models constant.** `BaseModels.ALL` is a `Collections.unmodifiableList<BaseModel>`;
  the Go counterpart is a package-level `[]BaseModel` slice.

## Test layout

```
src/test/java/com/volcengine/hibot/sdk/
├── BasicE2eOfflineTest.java          (4)  uploads + base models + workspace + config
├── internal/
│   ├── SignerTest.java               (4)  VOLC v4 canonical request / signature determinism
│   ├── ResponseDecoderTest.java      (5)  envelope decoding + ApiException surfacing
│   └── SseDecoderTest.java           (4)  multi-frame / CRLF / multi-line data / comments
├── v1/
│   ├── StreamNormalizationTest.java  (7)  event alias normalization + delta/final/error decode
│   ├── AgentsServiceTest.java        (4)  default-env auto-pick + tool partitioning
│   └── SessionsServiceTest.java      (5)  blocking chat / SSE streaming / error surfacing
└── testutil/
    └── MockHibotServer.java               local HttpServer used by all integration tests
```
