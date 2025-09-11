package com.volcengine.hiagent.api;

import com.volcengine.ApiClient;
import com.volcengine.ApiException;
import com.volcengine.ApiResponse;
import com.volcengine.Pair;
import com.volcengine.hiagent.api.model.*;
import com.volcengine.hiagent.api.model.base.EvaTaskItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaClient {
    private ApiClient apiClient;

    public EvaClient(ApiClient apiClient) {
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
     * @param body CreateEvaTaskRequest (required)
     * @return CreateEvaTaskResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     */
    public CreateEvaTaskResponse createEvaTask(CreateEvaTaskRequest body) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/CreateEvaTask/2025-02-01/eva/post/application_json/";

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

        ApiResponse<CreateEvaTaskResponse> apiResponse = apiClient.execute(call,
                CreateApiTokenResponse.class);
        return apiResponse.getData();
    }

    /**
     * Execute Eva Task Row Group
     *
     * @param body ExecEvaTaskRowGroupRequest (required)
     * @return ExecEvaTaskRowGroupResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     */
    public ExecEvaTaskRowGroupResponse execEvaTaskRowGroup(ExecEvaTaskRowGroupRequest body) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/ExecEvaTaskRowGroup/2025-02-01/eva/post/application_json/";

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

        ApiResponse<ExecEvaTaskRowGroupResponse> apiResponse = apiClient.execute(call,
                ExecEvaTaskRowGroupResponse.class);
        return apiResponse.getData();
    }

    /**
     * Get Eva Task Report
     *
     * @param body GetEvaTaskReportRequest (required)
     * @return GetEvaTaskReportResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     */
    public GetEvaTaskReportResponse getEvaTaskReport(GetEvaTaskReportRequest body) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/GetEvaTaskReport/2025-02-01/eva/post/application_json/";

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

        ApiResponse<GetEvaTaskReportResponse> apiResponse = apiClient.execute(call,
                GetEvaTaskReportResponse.class);
        return apiResponse.getData();
    }

    /**
     * Get Eva Task
     *
     * @param body GetEvaTaskRequest (required)
     * @return EvaTaskItem
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     */
    public EvaTaskItem getEvaTask(GetEvaTaskRequest body) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/GetEvaTask/2025-02-01/eva/post/application_json/";

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

        ApiResponse<EvaTaskItem> apiResponse = apiClient.execute(call,
                EvaTaskItem.class);
        return apiResponse.getData();
    }

    /**
     * List Eva Dataset Columns
     *
     * @param req ListEvaDatasetColumnsRequest (required)
     * @return ListEvaDatasetColumnsResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     */
    public ListEvaDatasetColumnsResponse listEvaDatasetColumns(ListEvaDatasetColumnsRequest req) throws ApiException {
        Object localVarPostBody = req;

        // create path and map variables
        String localVarPath = "/ListColumns/2025-02-01/eva/post/application_json/";

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

        ApiResponse<ListEvaDatasetColumnsResponse> apiResponse = apiClient.execute(call,
                ListEvaDatasetColumnsResponse.class);
        return apiResponse.getData();
    }

    /**
     * List Eva Dataset Conversations
     *
     * @param req ListEvaDatasetConversationsRequest (required)
     * @return ListEvaDatasetConversationsResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     */
    public ListEvaDatasetConversationsResponse listEvaDatasetConversations(ListEvaDatasetConversationsRequest req) throws ApiException {
        Object localVarPostBody = req;

        // create path and map variables
        String localVarPath = "/ListDatasetCases/2025-02-01/eva/post/application_json/";

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

        ApiResponse<ListEvaDatasetConversationsResponse> apiResponse = apiClient.execute(call,
                ListEvaDatasetConversationsResponse.class);
        return apiResponse.getData();
    }
}
