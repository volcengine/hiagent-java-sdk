## ADDED Requirements

### Requirement: Models follow UP's Action contract

The request and response models used by `UpClient` MUST carry the fields UP defines for `UploadRaw`, `LongLive`, `DownloadKey`, `Download` and `Delete`, except `Download.IsAnonymous`, and MUST send each request field where UP binds it: `UploadRaw` fields in the query, `LongLive` and `Download` fields in the JSON body, and `DownloadKey` fields in the query under the names `Path` and `Sha256`. Optional fields left `null` MUST NOT be sent.

#### Scenario: UploadRaw optional query fields

- GIVEN an `UploadRawRequest` with `Filename` = `报告 a+b.pdf` and `ImageCompress` = `true`
- WHEN `uploadRaw` sends the file
- THEN the query contains `Filename=报告 a+b.pdf` and `ImageCompress=true`
- AND the signature covers both parameters as sent

#### Scenario: ImageCompress false

- GIVEN an `UploadRawRequest` with `ImageCompress` = `false`
- WHEN `uploadRaw` sends the file
- THEN the query contains `ImageCompress=false`

#### Scenario: Unset optional fields

- GIVEN an `UploadRawRequest` with only `Id`
- WHEN `uploadRaw` sends the file
- THEN the query contains no `Filename` and no `ImageCompress`

#### Scenario: LongLive filename

- GIVEN a `LongLiveRequest` with `Filename`
- WHEN `longLive` sends the request
- THEN the JSON body contains `Filename`

#### Scenario: Download optional fields

- GIVEN a `DownloadRequest` with `Sha256`, `ID`, `Range`, `ImageCompress` and `Filename`
- WHEN `download` sends the request
- THEN the JSON body contains `Sha256`, `ID`, `Range`, `ImageCompress` and `Filename`

#### Scenario: DownloadKey parameter names

- GIVEN a `DownloadKeyRequest` with `Path` and `Sha256`
- WHEN `downloadKey` sends the request
- THEN the query contains `Path` and `Sha256`
- AND the query contains no `path` and no `sha256`

#### Scenario: Response fields

- GIVEN UP returns `PresignKey` for `UploadRaw`, `Sha256` and `ShortLink` for `LongLive`, and `Size` and `ContentType` for `DownloadKey`
- WHEN the corresponding method returns
- THEN the response object exposes each of these values

### Requirement: Sizes are 64-bit

Every `Size` returned by UP MUST be parsed as a 64-bit value. `LongLiveResponse` MUST keep its existing `Integer` accessors for compatibility and MUST expose the full value through `getSizeAsLong()`.

#### Scenario: LongLive of a file larger than 2 GiB

- GIVEN UP answers `LongLive` with `Size` = `3000000000`
- WHEN `longLive` is called
- THEN it returns without an exception
- AND `getSizeAsLong()` returns `3000000000`
- AND `getSize()` throws `ArithmeticException`

### Requirement: Local-only fields stay local

`DownloadRequest.saveTo` MUST NOT be sent to UP.

#### Scenario: Download body

- GIVEN a `DownloadRequest` with `Key`, `Path` and `SaveTo`
- WHEN `download` sends the request
- THEN the JSON body contains `Key` and `Path`
- AND the JSON body contains neither `SaveTo` nor the local path

### Requirement: SHA-256 values are sent in lowercase

Every SHA-256 value `UpClient` sends MUST be trimmed and converted to lowercase hexadecimal, because UP writes, stores and compares hashes as lowercase strings. This covers the `Sha256` query parameter of `downloadKey` and the `Sha256` member of the JSON body of `download` and `delete`. The caller's request object MUST NOT be modified.

#### Scenario: Uppercase hash for a download key

- GIVEN a `DownloadKeyRequest` whose `Sha256` is uppercase
- WHEN `downloadKey` sends the request
- THEN the `Sha256` query parameter is lowercase

#### Scenario: Uppercase hash in a JSON body

- GIVEN a `DeleteRequest` and a `DownloadRequest` whose `Sha256` is uppercase and surrounded by spaces
- WHEN `delete` and `download` send their requests
- THEN the `Sha256` in each JSON body is the same hash in lowercase without spaces
- AND `X-Content-Sha256` is the hash of the body actually sent
- AND the `Sha256` of both request objects is unchanged

### Requirement: UploadRaw requires Id before sending

`uploadRaw` MUST throw an `ApiException` when `UploadRawRequest.id` is `null` or empty, before it reads the file and without sending any request, matching UP's `required` validation. Any other value, including whitespace, MUST be sent as given.

#### Scenario: Missing Id

- GIVEN an `UploadRawRequest` without `Id`
- AND the file does not exist
- WHEN `uploadRaw` is called
- THEN an `ApiException` about the missing `Id` is thrown
- AND no request reaches the server

#### Scenario: Whitespace Id

- GIVEN an `UploadRawRequest` whose `Id` is a single space
- WHEN `uploadRaw` sends the file
- THEN the `Id` query parameter is a single space

### Requirement: Range downloads save the returned part

When `DownloadRequest.range` is set and UP answers 206 Partial Content, `download` MUST save the returned bytes to `saveTo`.

#### Scenario: Partial content

- GIVEN a `DownloadRequest` with `Range` = `bytes=0-3`
- AND UP answers with status 206 and a 4-byte body
- WHEN `download` is called
- THEN `saveTo` contains exactly those 4 bytes

## MODIFIED Requirements

### Requirement: Payload hash matches the body

Every `UpClient` request MUST carry `X-Content-Sha256`. For `uploadRaw` the value MUST be the caller's `Sha256`, trimmed and converted to lowercase, when provided, otherwise the SHA-256 of the file computed by the SDK before sending. For JSON requests it MUST be the SHA-256 of the serialized bytes.

#### Scenario: Binary file without caller hash

- GIVEN a file containing bytes that are not valid UTF-8
- AND the `UploadRawRequest` has no `Sha256`
- WHEN `uploadRaw` sends the file
- THEN `X-Content-Sha256` equals the SHA-256 of the file bytes received by the server

#### Scenario: Caller-provided hash

- GIVEN the `UploadRawRequest` has `Sha256`
- WHEN `uploadRaw` sends the file
- THEN `X-Content-Sha256` equals that value, trimmed and in lowercase
- AND the SDK does not read the file to hash it

#### Scenario: Uppercase caller hash

- GIVEN an `UploadRawRequest` whose `Sha256` is an uppercase hexadecimal string surrounded by spaces
- WHEN `uploadRaw` sends the file
- THEN `X-Content-Sha256` is the same hash in lowercase without spaces
- AND the signature covers that lowercase value
