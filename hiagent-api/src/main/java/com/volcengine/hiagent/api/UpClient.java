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

import com.volcengine.ApiClient;
import com.volcengine.ApiException;
import com.volcengine.ApiResponse;
import com.volcengine.Pair;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpClient {
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
   * Upload Raw File
   *
   * @param file File to upload (required)
   * @return UploadRawResponse
   * @throws ApiException If fail to call the API, e.g. server error or cannot
   *                      deserialize the response body
   */
  public UploadRawResponse uploadRaw(UploadRawRequest body, File file) throws ApiException {
    byte[] fileBytes;
    try {
      fileBytes = Files.readAllBytes(file.toPath());
    } catch (IOException e) {
      throw new ApiException("Failed to read file: " + e.getMessage());
    }

    Object localVarPostBody = fileBytes;

    String localVarPath = "/UploadRaw/2023-08-01/app/post/application_json/";

    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

    if (body.getExpire() != null) {
      localVarQueryParams.add(new Pair("Expire", body.getExpire()));
    }
    if (body.getId() != null) {
      localVarQueryParams.add(new Pair("Id", body.getId()));
    }

    Map<String, String> localVarHeaderParams = new HashMap<String, String>();

    if (body.getSha256() != null) {
      localVarHeaderParams.put("x-content-sha256", body.getSha256());
    }

    if (body.getContentType() != null) {
      localVarHeaderParams.put("Content-Type", body.getContentType());
    } else {
      localVarHeaderParams.put("Content-Type", "application/octet-stream");
    }

    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    String[] localVarAuthNames = new String[] { "volcengineSign" };

    com.squareup.okhttp.Request request = apiClient.buildRequest(localVarPath, "POST", localVarQueryParams,
        localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams,
        localVarAuthNames, null);

    com.squareup.okhttp.Call call = apiClient.getHttpClient().newCall(request);
    com.squareup.okhttp.Response response;
    try {
      response = call.execute();
    } catch (IOException e) {
      throw new ApiException("Failed to execute request: " + e.getMessage());
    }

    UploadRawResponse result = apiClient.handleResponse(response, UploadRawResponse.class);
    return result;
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
    Object localVarPostBody = body;

    String localVarPath = "/LongLive/2023-08-01/app/post/application_json/";

    List<Pair> localVarQueryParams = new ArrayList<Pair>();

    localVarQueryParams.add(new Pair("Id", "test"));

    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

    Map<String, String> localVarHeaderParams = new HashMap<String, String>();

    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {
        "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    if (localVarAccept != null)
      localVarHeaderParams.put("Accept", localVarAccept);

    final String[] localVarContentTypes = {
        "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
    localVarHeaderParams.put("Content-Type", localVarContentType);

    String[] localVarAuthNames = new String[] { "volcengineSign" };
    com.squareup.okhttp.Call call = apiClient.buildCall(localVarPath, "POST", localVarQueryParams,
        localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams,
        localVarAuthNames, null);

    ApiResponse<LongLiveResponse> apiResponse = apiClient.execute(call,
        LongLiveResponse.class);
    return apiResponse.getData();
  }

  /**
   * Get Download Key
   *
   * @param body DownloadKeyRequest (required)
   * @return DownloadKeyResponse
   * @throws ApiException If fail to call the API, e.g. server error or cannot
   *                      deserialize the response body
   */
  public DownloadKeyResponse downloadKey(DownloadKeyRequest body) throws ApiException {
    Object localVarPostBody = null; // No body - use query params instead

    String localVarPath = "/DownloadKey/2023-08-01/app/post/application_json/";

    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

    // Add parameters as query params like uploadRaw
    if (body.getPath() != null) {
      localVarQueryParams.add(new Pair("path", body.getPath()));
    }
    if (body.getSha256() != null) {
      localVarQueryParams.add(new Pair("sha256", body.getSha256()));
    }

    Map<String, String> localVarHeaderParams = new HashMap<String, String>();

    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {
        "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    if (localVarAccept != null)
      localVarHeaderParams.put("Accept", localVarAccept);

    final String[] localVarContentTypes = {
        "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
    localVarHeaderParams.put("Content-Type", localVarContentType);

    String[] localVarAuthNames = new String[] { "volcengineSign" };

    com.squareup.okhttp.Request request = apiClient.buildRequest(localVarPath, "POST", localVarQueryParams,
        localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams,
        localVarAuthNames, null);

    com.squareup.okhttp.Call call = apiClient.getHttpClient().newCall(request);
    com.squareup.okhttp.Response response;
    try {
      response = call.execute();
    } catch (IOException e) {
      throw new ApiException("Failed to execute request: " + e.getMessage());
    }

    DownloadKeyResponse result = apiClient.handleResponse(response, DownloadKeyResponse.class);
    return result;
  }

  /**
   * Download File
   *
   * @param body DownloadRequest (required)
   * @return DownloadResponse
   * @throws ApiException If fail to call the API, e.g. server error or cannot
   *                      deserialize the response body
   */
  public DownloadResponse download(DownloadRequest body) throws ApiException {
    Object localVarPostBody = body;

    // create path and map variables
    String localVarPath = "/Download/2023-08-01/app/post/application_json/";

    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

    Map<String, String> localVarHeaderParams = new HashMap<String, String>();

    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {
        "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    if (localVarAccept != null)
      localVarHeaderParams.put("Accept", localVarAccept);

    final String[] localVarContentTypes = {
        "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
    localVarHeaderParams.put("Content-Type", localVarContentType);

    String[] localVarAuthNames = new String[] { "volcengineSign" };

    // Use buildRequest directly
    com.squareup.okhttp.Request request = apiClient.buildRequest(localVarPath, "POST", localVarQueryParams,
        localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams,
        localVarAuthNames, null);

    com.squareup.okhttp.Call call = apiClient.getHttpClient().newCall(request);
    com.squareup.okhttp.Response response;
    try {
      response = call.execute();
    } catch (IOException e) {
      throw new ApiException("Failed to execute request: " + e.getMessage());
    }

    if (!response.isSuccessful()) {
      throw new ApiException("Download failed with status: " + response.code());
    }

    // Save response body to file
    try {
      byte[] fileBytes = response.body().bytes();
      java.nio.file.Path filePath = java.nio.file.Paths.get(body.getSaveTo());

      // Create parent directories if they don't exist
      java.nio.file.Files.createDirectories(filePath.getParent());

      java.nio.file.Files.write(filePath, fileBytes);

      // Return a simple response indicating success
      DownloadResponse result = new DownloadResponse();
      // Set any relevant fields if needed
      return result;
    } catch (IOException e) {
      throw new ApiException("Failed to save file: " + e.getMessage());
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
    Object localVarPostBody = body;

    String localVarPath = "/Delete/2023-08-01/app/post/application_json/";

    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

    Map<String, String> localVarHeaderParams = new HashMap<String, String>();

    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {
        "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    if (localVarAccept != null)
      localVarHeaderParams.put("Accept", localVarAccept);

    final String[] localVarContentTypes = {
        "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
    localVarHeaderParams.put("Content-Type", localVarContentType);

    String[] localVarAuthNames = new String[] { "volcengineSign" };

    com.squareup.okhttp.Request request = apiClient.buildRequest(localVarPath, "POST", localVarQueryParams,
        localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams,
        localVarAuthNames, null);

    com.squareup.okhttp.Call call = apiClient.getHttpClient().newCall(request);
    com.squareup.okhttp.Response response;
    try {
      response = call.execute();
    } catch (IOException e) {
      throw new ApiException("Failed to execute request: " + e.getMessage());
    }

    DeleteResponse result = apiClient.handleResponse(response, DeleteResponse.class);
    return result;
  }
}
