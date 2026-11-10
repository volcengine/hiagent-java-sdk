// License: Apache 2.0
// Copyright (c) 2024 Bytedance Ltd. and/or its affiliates
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.volcengine.hiagent.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.volcengine.ApiClient;
import com.volcengine.ApiException;
import com.volcengine.auth.CredentialProvider;
import com.volcengine.auth.CredentialValue;
import com.volcengine.hiagent.api.model.DeleteRequest;
import com.volcengine.hiagent.api.model.DeleteResponse;
import com.volcengine.hiagent.api.model.DownloadKeyRequest;
import com.volcengine.hiagent.api.model.DownloadKeyResponse;
import com.volcengine.hiagent.api.model.DownloadRequest;
import com.volcengine.hiagent.api.model.DownloadResponse;
import com.volcengine.hiagent.api.model.LongLiveRequest;
import com.volcengine.hiagent.api.model.LongLiveResponse;
import com.volcengine.hiagent.api.model.UploadRawRequest;
import com.volcengine.hiagent.api.model.UploadRawResponse;
import com.volcengine.model.ResponseMetadata;
import com.volcengine.sign.Credentials;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Client for the UP (upload) service.
 *
 * <p>Requests are built and signed here rather than by the core
 * {@code ApiClient.buildRequest}: that method signs a different query, body
 * hash and path than it sends, which UP rejects whenever it verifies
 * signatures. Every request is signed by {@link UpSigner} from the final
 * {@link Request} that goes on the wire. Endpoint, region, credentials and the
 * {@code OkHttpClient} still come from the {@link ApiClient} passed in.
 */
public class UpClient {
  private static final String VERSION = "2023-08-01";
  private static final String JSON_CONTENT_TYPE = "application/json; charset=utf-8";
  private static final MediaType JSON = MediaType.parse(JSON_CONTENT_TYPE);
  private static final String DEFAULT_UPLOAD_CONTENT_TYPE = "application/octet-stream";
  private static final String CONTENT_TYPE = "Content-Type";
  private static final String ACCEPT = "Accept";
  private static final String JSON_ACCEPT = "application/json";
  private static final String RESPONSE_METADATA = "ResponseMetadata";
  private static final String TEMP_FILE_PREFIX = ".up-download-";
  private static final String TEMP_FILE_SUFFIX = ".part";

  private ApiClient apiClient;

  public UpClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Upload Raw File. The file is streamed; when {@code body.getSha256()} is not
   * set, the SDK first streams the file once to compute its SHA-256.
   *
   * @param file File to upload (required)
   * @return UploadRawResponse
   * @throws ApiException If fail to call the API, e.g. server error or cannot
   *                      deserialize the response body
   */
  public UploadRawResponse uploadRaw(UploadRawRequest body, File file) throws ApiException {
    if (file == null || !file.isFile() || !file.canRead()) {
      throw new ApiException("Failed to read file: " + (file == null ? "null" : file.getPath()));
    }
    String contentType = body.getContentType() != null ? body.getContentType() : DEFAULT_UPLOAD_CONTENT_TYPE;
    Map<String, String> query = new LinkedHashMap<String, String>();
    putIfPresent(query, "Expire", body.getExpire());
    putIfPresent(query, "Id", body.getId());

    RequestBody requestBody = RequestBody.create(MediaType.parse(contentType), file);
    Response response = execute("UploadRaw", query, headers(contentType, uploadHash(body, file)), requestBody);
    return handle(response, UploadRawResponse.class);
  }

  /**
   * Long Live File
   *
   * @param body LongLiveRequest (required)
   * @return LongLiveResponse
   * @throws ApiException If fail to call the API, e.g. server error or cannot
   *                      deserialize the response body
   */
  public LongLiveResponse longLive(LongLiveRequest body) throws ApiException {
    Response response = executeJson("LongLive", new LinkedHashMap<String, String>(), toJson(body));
    return handle(response, LongLiveResponse.class);
  }

  /**
   * Get Download Key. UP reads {@code path} and {@code sha256} from the query only.
   *
   * @param body DownloadKeyRequest (required)
   * @return DownloadKeyResponse
   * @throws ApiException If fail to call the API, e.g. server error or cannot
   *                      deserialize the response body
   */
  public DownloadKeyResponse downloadKey(DownloadKeyRequest body) throws ApiException {
    Map<String, String> query = new LinkedHashMap<String, String>();
    putIfPresent(query, "path", body.getPath());
    putIfPresent(query, "sha256", body.getSha256());
    Response response = executeJson("DownloadKey", query, new byte[0]);
    return handle(response, DownloadKeyResponse.class);
  }

  /**
   * Download File into {@code body.getSaveTo()}. The response is streamed into a
   * temporary file next to the target and moved into place only once complete,
   * so a failed download leaves no file behind and keeps any existing one. A
   * symbolic link at {@code saveTo} is followed: the linked file is replaced.
   *
   * @param body DownloadRequest (required)
   * @return DownloadResponse
   * @throws ApiException If fail to call the API, e.g. server error or cannot
   *                      save the file
   */
  public DownloadResponse download(DownloadRequest body) throws ApiException {
    if (body.getSaveTo() == null || body.getSaveTo().trim().isEmpty()) {
      throw new ApiException("DownloadRequest.saveTo must be set");
    }
    Path target = Paths.get(body.getSaveTo());
    Response response = executeJson("Download", new LinkedHashMap<String, String>(), toJson(body));
    try {
      if (!response.isSuccessful()) {
        throw downloadFailure(response);
      }
      writeAtomically(response.body(), target);
      return new DownloadResponse();
    } finally {
      closeQuietly(response.body());
    }
  }

  /**
   * Delete File
   *
   * @param body DeleteRequest (required)
   * @return DeleteResponse
   * @throws ApiException If fail to call the API, e.g. server error or cannot
   *                      deserialize the response body
   */
  public DeleteResponse delete(DeleteRequest body) throws ApiException {
    Response response = executeJson("Delete", new LinkedHashMap<String, String>(), toJson(body));
    return handle(response, DeleteResponse.class);
  }

  private Response executeJson(String action, Map<String, String> query, byte[] json) throws ApiException {
    Map<String, String> headers = headers(JSON_CONTENT_TYPE, UpSigner.sha256Hex(json));
    headers.put(ACCEPT, JSON_ACCEPT);
    return execute(action, query, headers, RequestBody.create(JSON, json));
  }

  private Map<String, String> headers(String contentType, String contentSha256) {
    Map<String, String> headers = new LinkedHashMap<String, String>();
    headers.put(CONTENT_TYPE, contentType);
    headers.put(UpSigner.HEADER_CONTENT_SHA256, contentSha256);
    String productCode = productCode();
    if (productCode != null) {
      headers.put(ProductCode.HEADER_NAME, productCode);
    }
    return headers;
  }

  private Response execute(String action, Map<String, String> query, Map<String, String> headers,
      RequestBody requestBody) throws ApiException {
    Request signed;
    try {
      signed = signer().sign(buildRequest(action, query, headers, requestBody), Instant.now());
    } catch (IllegalArgumentException e) {
      // Credential problems are reported by UpSigner without their values; what
      // remains here (a malformed caller hash or header) holds no secrets.
      throw new ApiException(e.getMessage(), e, 0, null, null);
    }
    try {
      return apiClient.getHttpClient().newCall(signed).execute();
    } catch (IOException e) {
      throw new ApiException("Failed to execute request: " + e.getMessage(), e, 0, null, null);
    }
  }

  private Request buildRequest(String action, Map<String, String> query, Map<String, String> headers,
      RequestBody requestBody) throws ApiException {
    HttpUrl.Builder url = baseUrl().newBuilder()
        .addEncodedQueryParameter("Action", UpSigner.signEncode(action))
        .addEncodedQueryParameter("Version", VERSION);
    for (Map.Entry<String, String> param : query.entrySet()) {
      // Written with the signing encoding, so the bytes on the wire are what gets signed.
      url.addEncodedQueryParameter(UpSigner.signEncode(param.getKey()), UpSigner.signEncode(param.getValue()));
    }
    Request.Builder builder = new Request.Builder().url(url.build()).post(requestBody);
    // Applies our headers plus the ApiClient's default headers (User-Agent, ...).
    apiClient.processHeaderParams(headers, builder);
    return builder.build();
  }

  /**
   * Same as {@link ApiClient#handleResponse} but, like the core's own
   * {@code execute}, attaches the UP error envelope as the exception's
   * {@code ResponseMetadata} when the response carries one.
   */
  private <T> T handle(Response response, Class<T> type) throws ApiException {
    try {
      return apiClient.handleResponse(response, type);
    } catch (ApiException e) {
      if (e.getResponseMetadata() == null && e.getResponseBody() != null) {
        e.setResponseMetadata(responseMetadata(e.getResponseBody()));
      }
      throw e;
    }
  }

  /**
   * Download answers with the file, not an envelope, so it cannot go through
   * {@link #handle}. On failure UP still sends its JSON error envelope; keep it
   * on the exception the same way {@code handle} does.
   */
  private ApiException downloadFailure(Response response) {
    String body = null;
    try {
      body = response.body() == null ? null : response.body().string();
    } catch (IOException unreadable) {
      // The status code alone is still reported below.
    }
    ApiException e = new ApiException("Download failed with status: " + response.code(),
        response.code(), response.headers().toMultimap(), body);
    if (body != null) {
      e.setResponseMetadata(responseMetadata(body));
    }
    return e;
  }

  private ResponseMetadata responseMetadata(String body) {
    try {
      JsonElement root = JsonParser.parseString(body);
      if (root.isJsonObject() && root.getAsJsonObject().has(RESPONSE_METADATA)) {
        return apiClient.getJSON().getGson().fromJson(root.getAsJsonObject().get(RESPONSE_METADATA),
            ResponseMetadata.class);
      }
    } catch (JsonParseException notAnEnvelope) {
      // A non-JSON error body (a proxy page, for instance) simply has no metadata.
    }
    return null;
  }

  private HttpUrl baseUrl() throws ApiException {
    String endpoint = apiClient.getEndpoint();
    if (endpoint == null || endpoint.trim().isEmpty()) {
      throw new ApiException("Endpoint must be set on the ApiClient");
    }
    String host = endpoint.trim();
    while (host.endsWith("/")) {
      host = host.substring(0, host.length() - 1);
    }
    String scheme = apiClient.getDisableSSL() ? "http://" : "https://";
    HttpUrl url = HttpUrl.parse(scheme + host + "/");
    if (url == null) {
      throw new ApiException("Invalid endpoint: " + endpoint);
    }
    return url;
  }

  /** Static credentials first, then the credential provider, as the core signer does. */
  private UpSigner signer() throws ApiException {
    Credentials credentials = apiClient.getCredentials();
    if (credentials != null) {
      return new UpSigner(credentials.getAccessKey(), credentials.getSecretKey(),
          credentials.getSessionToken(), apiClient.getRegion());
    }
    CredentialProvider provider = apiClient.getCredentialProvider();
    if (provider == null) {
      throw new ApiException("one of credentials and credentialProvider must set");
    }
    CredentialValue value = provider.get();
    if (value == null) {
      throw new ApiException("credentialProvider return null");
    }
    return new UpSigner(value.getAk(), value.getSk(), value.getSessionToken(), apiClient.getRegion());
  }

  private String productCode() {
    if (apiClient instanceof com.volcengine.hiagent.api.ApiClient) {
      return ((com.volcengine.hiagent.api.ApiClient) apiClient).getProductCode();
    }
    return null;
  }

  private byte[] toJson(Object body) {
    return apiClient.getJSON().serialize(body).getBytes(StandardCharsets.UTF_8);
  }

  private static String uploadHash(UploadRawRequest body, File file) throws ApiException {
    String given = body.getSha256();
    if (given != null && !given.trim().isEmpty()) {
      return given.trim();
    }
    try {
      return UpSigner.sha256Hex(file);
    } catch (IOException e) {
      throw new ApiException("Failed to read file: " + e.getMessage());
    }
  }

  private static void putIfPresent(Map<String, String> query, String name, String value) {
    if (value != null) {
      query.put(name, value);
    }
  }

  private static void writeAtomically(ResponseBody responseBody, Path target) throws ApiException {
    Path absolute = target.toAbsolutePath();
    Path dir = absolute.getParent();
    if (dir == null || absolute.getFileName() == null) {
      throw new ApiException("DownloadRequest.saveTo must name a file: " + target);
    }
    // Fixed-length name, so a target name near the filesystem limit still fits.
    Path temp = dir.resolve(TEMP_FILE_PREFIX + UUID.randomUUID() + TEMP_FILE_SUFFIX);
    try {
      Files.createDirectories(dir);
      try (InputStream in = responseBody.byteStream()) {
        Files.copy(in, temp);
      }
      // Replace the file a link points to, not the link itself.
      Path destination = Files.isSymbolicLink(absolute) ? absolute.toRealPath() : absolute;
      moveIntoPlace(temp, destination);
    } catch (IOException e) {
      throw new ApiException("Failed to save file: " + e.getMessage(), e, 0, null, null);
    } finally {
      try {
        Files.deleteIfExists(temp);
      } catch (IOException ignored) {
        // Best effort: the temp file is hidden and uniquely named.
      }
    }
  }

  private static void moveIntoPlace(Path temp, Path target) throws IOException {
    try {
      Files.move(temp, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
    } catch (AtomicMoveNotSupportedException e) {
      Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING);
    }
  }

  private static void closeQuietly(ResponseBody body) {
    if (body == null) {
      return;
    }
    try {
      body.close();
    } catch (IOException ignored) {
      // The response has already been consumed or failed; nothing to report.
    }
  }
}
