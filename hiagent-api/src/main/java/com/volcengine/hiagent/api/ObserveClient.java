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

import com.volcengine.hiagent.api.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.volcengine.ApiClient;
import com.volcengine.ApiException;
import com.volcengine.ApiResponse;
import com.volcengine.Pair;

public class ObserveClient {
    private ApiClient apiClient;

    public ObserveClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create API Token
     *
     * @param body CreateApiTokenRequest (required)
     * @return CreateApiTokenResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     */
    public CreateApiTokenResponse createApiToken(CreateApiTokenRequest body) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/CreateApiToken/2025-05-01/observe/post/application_json/";

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
        com.squareup.okhttp.Call call = apiClient.buildCall(localVarPath, "POST", localVarQueryParams,
                localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams,
                localVarAuthNames, null);

        ApiResponse<CreateApiTokenResponse> apiResponse = apiClient.execute(call,
                CreateApiTokenResponse.class);
        return apiResponse.getData();
    }

    /**
     * Create API Token
     *
     * @param body CreateApiTokenRequest (required)
     * @return CreateApiTokenResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     */
    public ListTraceSpansResponse listTraceSpans(ListTraceSpansRequest body) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/ListTraceSpans/2025-05-01/observe/post/application_json/";

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
        com.squareup.okhttp.Call call = apiClient.buildCall(localVarPath, "POST", localVarQueryParams,
                localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams,
                localVarAuthNames, null);

        ApiResponse<ListTraceSpansResponse> apiResponse = apiClient.execute(call,
                ListTraceSpansResponse.class);
        return apiResponse.getData();
    }
}
