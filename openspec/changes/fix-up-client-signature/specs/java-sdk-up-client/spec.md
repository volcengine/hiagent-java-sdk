## ADDED Requirements

### Requirement: Sign the request that is sent

`UpClient` MUST sign exactly the request it sends: the canonical request MUST use the actual URL path and every query parameter on the wire, and the payload hash MUST be the value of the `X-Content-Sha256` header sent with the request.

#### Scenario: Query parameters are signed

- GIVEN an `UploadRawRequest` with `Id` and `Expire`
- WHEN `uploadRaw` sends the request
- THEN `Id` and `Expire` appear in the query and in the canonical query string
- AND UP's signature verifier accepts the request

#### Scenario: Endpoint with a path

- GIVEN the endpoint is `http://host:31010/up`
- WHEN any `UpClient` method sends a request
- THEN the request path is `/up/`
- AND the canonical URI is `/up/`

#### Scenario: Reserved and non-ASCII query values

- GIVEN a `DownloadKeyRequest` whose `Path` contains `/`, spaces, `+`, `=`, `&`, `!'()*` and Chinese characters
- WHEN `downloadKey` sends the request
- THEN UP decodes `Path` to the original value
- AND UP's signature verifier accepts the request

#### Scenario: No junk query parameter

- WHEN `uploadRaw` sends a file
- THEN the URL contains no query parameter with an empty name

### Requirement: Payload hash matches the body

Every `UpClient` request MUST carry `X-Content-Sha256`. For `uploadRaw` the value MUST be the caller's `Sha256` when provided, otherwise the SHA-256 of the file computed by the SDK before sending. For JSON requests it MUST be the SHA-256 of the serialized bytes.

#### Scenario: Binary file without caller hash

- GIVEN a file containing bytes that are not valid UTF-8
- AND the `UploadRawRequest` has no `Sha256`
- WHEN `uploadRaw` sends the file
- THEN `X-Content-Sha256` equals the SHA-256 of the file bytes received by the server

#### Scenario: Caller-provided hash

- GIVEN the `UploadRawRequest` has `Sha256`
- WHEN `uploadRaw` sends the file
- THEN `X-Content-Sha256` equals that value
- AND the SDK does not read the file to hash it

### Requirement: Signed headers exclude host

`UpClient` MUST sign `x-content-sha256` and `x-date` only, and MUST NOT sign `host`, so that a gateway rewriting the Host header does not break verification. The credential scope service MUST be `up`.

#### Scenario: Authorization shape

- WHEN any `UpClient` method sends a request
- THEN `Authorization` is `HMAC-SHA256 Credential=<AK>/<date>/<region>/up/request, SignedHeaders=x-content-sha256;x-date, Signature=<hex>`

### Requirement: Stream file transfers

`uploadRaw` MUST stream the file instead of loading it into memory. `download` MUST stream the response into a temporary file next to `saveTo` and move it into place only after the whole body is written.

#### Scenario: Download failure leaves no file

- GIVEN the server returns a non-2xx status for `Download`
- WHEN `download` is called
- THEN an `ApiException` is thrown
- AND no file exists at `saveTo`

#### Scenario: Download interrupted mid-body

- GIVEN a file already exists at `saveTo`
- AND the response body ends before its declared length
- WHEN `download` is called
- THEN an `ApiException` is thrown
- AND the existing file keeps its previous content
- AND no temporary file remains in the directory

#### Scenario: Save to a bare file name

- GIVEN `saveTo` has no parent directory
- WHEN `download` succeeds
- THEN the file is written in the current directory without a `NullPointerException`

### Requirement: Product code on every UpClient method

When the client is a `com.volcengine.hiagent.api.ApiClient` configured with a product code, every `UpClient` method MUST send `X-Trace-Product-Code`. When no product code is configured, the SDK MUST NOT add the header.

#### Scenario: Configured product code

- GIVEN the `ApiClient` is configured with product code `maas`
- WHEN `uploadRaw`, `longLive`, `downloadKey`, `download` or `delete` sends a request
- THEN the request contains `X-Trace-Product-Code: maas`

### Requirement: Errors keep server and transport detail

When UP answers with an error envelope, the `ApiException` thrown by an `UpClient` method MUST carry its `ResponseMetadata`. A transport failure MUST keep the underlying `IOException` as the cause. Error messages MUST NOT contain the access key, secret key or session token.

#### Scenario: Error envelope

- GIVEN UP returns HTTP 500 with `ResponseMetadata.Error.Code` = `InternalError`
- WHEN `delete` is called
- THEN `ApiException.getResponseMetadata().getError().getCode()` is `InternalError`

#### Scenario: Download error envelope

- GIVEN UP answers `Download` with HTTP 400 and `ResponseMetadata.Error.Message` = `sha256 or path must be provided`
- WHEN `download` is called
- THEN the `ApiException` has code 400 and carries that `ResponseMetadata`
- AND no file exists at `saveTo`

#### Scenario: Session token with control characters

- GIVEN the session token contains a line break inside it
- WHEN any `UpClient` method is called
- THEN an `ApiException` is thrown before any request is sent
- AND its message does not contain the token

### Requirement: LongLive sends no placeholder query

`longLive` MUST NOT send the `Id=test` query parameter. `Path` and `Id` MUST be sent in the JSON body only.

#### Scenario: LongLive query

- WHEN `longLive` sends a request
- THEN the query contains only `Action` and `Version`

### Requirement: Agreement with UP's verifier

The SDK test suite MUST include fixed signature vectors produced offline by UP's own signature verifier, and `UpSigner` MUST reproduce each vector's canonical request and signature.

#### Scenario: Fixed vectors

- GIVEN a vector with URL, `X-Date`, `X-Content-Sha256`, credentials and expected signature
- WHEN `UpSigner` signs the corresponding request
- THEN the canonical request and the signature equal the vector
