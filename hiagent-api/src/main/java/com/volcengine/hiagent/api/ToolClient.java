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

import com.volcengine.ApiException;
import com.volcengine.ApiResponse;
import com.volcengine.Pair;
import com.volcengine.hiagent.api.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolClient {
    private ApiClient apiClient;

    public ToolClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get Archived Tool
     *
     * @param request GetArchivedToolRequest (required)
     * @return GetArchivedToolResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     */
    public GetArchivedToolResponse getArchivedTool(GetArchivedToolRequest request) throws ApiException {
        Object localVarPostBody = request;

        // create path and map variables
        String localVarPath = "/GetArchivedTool/2023-08-01/app/post/application_json/";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "volcengineSign" };
        com.squareup.okhttp.Call call = apiClient.buildCall(localVarPath, "POST", localVarQueryParams,
                localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams,
                localVarAuthNames, null);

        ApiResponse<GetArchivedToolResponse> apiResponse = apiClient.execute(call,
                GetArchivedToolResponse.class);
        return apiResponse.getData();
    }


    /**
     * Exec Archived Tool
     *
     * @param request ExecArchivedToolRequest (required)
     * @return ExecArchivedToolResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     */
    public ExecArchivedToolResponse execArchivedTool(ExecArchivedToolRequest request) throws ApiException {
        Object localVarPostBody = request;

        // create path and map variables
        String localVarPath = "/ExecArchivedTool/2023-08-01/app/post/application_json/";

        List<Pair> localVarQueryParams = new ArrayList<>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<>();

        Map<String, String> localVarHeaderParams = new HashMap<>();

        Map<String, Object> localVarFormParams = new HashMap<>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
                "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "volcengineSign" };
        com.squareup.okhttp.Call call = apiClient.buildCall(localVarPath, "POST", localVarQueryParams,
                localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams,
                localVarAuthNames, null);

        ApiResponse<ExecArchivedToolResponse> apiResponse = apiClient.execute(call,
                ExecArchivedToolResponse.class);
        return apiResponse.getData();
    }
}
