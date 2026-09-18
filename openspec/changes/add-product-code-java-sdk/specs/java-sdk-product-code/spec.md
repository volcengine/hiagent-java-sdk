## ADDED Requirements

### Requirement: Client-level product code configuration

Each supported Java client family MUST provide an optional client-level product code while preserving existing constructors.

#### Scenario: Configured product code

- GIVEN a client is configured with `maas`
- WHEN it sends an eligible request
- THEN the request contains `X-Trace-Product-Code: maas`

#### Scenario: Omitted product code

- GIVEN a client is created with an existing constructor
- WHEN it sends a request
- THEN the SDK does not add an `X-Trace-Product-Code` header

### Requirement: Request header precedence

The SDK MUST preserve an explicit request-level `X-Trace-Product-Code` value over the client-level value.

#### Scenario: Explicit override

- GIVEN the client is configured with `hiagent`
- AND the request contains `X-Trace-Product-Code: maas`
- WHEN the request is sent
- THEN the wire request contains `maas`

### Requirement: Signed request ordering

Signed TOP clients MUST inject product code before canonical request construction and signature generation.

#### Scenario: Signature verification

- GIVEN a signed client has product code `maas`
- WHEN it builds a request
- THEN the product header is included according to the signing contract before signing
- AND the server can verify the request

### Requirement: Observe token and trace separation

Observe product-code support MUST NOT add ProductCode to `CreateApiTokenRequest`. Configured product code MUST be added to OTLP Trace export requests.

#### Scenario: Observe export

- GIVEN an Observe client is configured with `hiagent`
- WHEN it initializes and exports a trace
- THEN the token request body contains only its existing fields
- AND the OTLP request contains `X-Trace-Product-Code: hiagent`

### Requirement: Safe values

The SDK MUST treat blank values as absent and MUST reject HTTP control characters before network I/O.

#### Scenario: Blank configuration

- GIVEN product code is whitespace-only
- WHEN the client is built
- THEN no product-code header is emitted

#### Scenario: Invalid configuration

- GIVEN product code contains an HTTP control character
- WHEN the client is built
- THEN configuration fails before a request is sent
