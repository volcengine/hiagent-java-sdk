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
package com.volcengine.hiagent.api.examples;

import com.volcengine.ApiClient;
import com.volcengine.ApiException;
import com.volcengine.hiagent.api.KbsClient;
import com.volcengine.hiagent.api.model.*;
import com.volcengine.sign.Credentials;

import java.util.ArrayList;
import java.util.List;

public class TestQuery {
    public static void main(String[] args) {
        String ak = System.getenv("VOLC_ACCESSKEY");
        String sk = System.getenv("VOLC_SECRETKEY");
        String region = System.getenv("HIAGENT_TOP_REGION");
        String endpoint = System.getenv("HIAGENT_TOP_ENDPOINT");

        String workspaceID = System.getenv("WORKSPACE_ID");
        String datasetID = System.getenv("DATASET_ID");

        ApiClient apiClient = new ApiClient()
                .setCredentials(Credentials.getCredentials(ak, sk))
                .setRegion(region).setEndpoint(endpoint).setDisableSSL(true);

        KbsClient api = new KbsClient(apiClient);
        QueryRequest queryReq = new QueryRequest();

        queryReq.setWorkspaceID(workspaceID);

        List<String> datasetIDs = new ArrayList<>();
        datasetIDs.add(datasetID);
        queryReq.setDatasetIDs(datasetIDs);

        List<String> keywords = new ArrayList<>();
        keywords.add("你好");
        queryReq.setKeywords(keywords);

        queryReq.setTopK(3);
        queryReq.setType(1);
        queryReq.setRetrievalSearchMethod(0);

        try {
            QueryResponse response = api.query(queryReq);
            System.out.println(response);
        } catch (ApiException e) {
            System.err.println("API调用发生异常:");
            System.err.println("错误码: " + e.getCode());
            System.err.println("错误信息: " + e.getMessage());
            System.err.println("响应体: " + e.getResponseBody());
            System.err.println("完整堆栈跟踪:");
            e.printStackTrace(); // 这会打印完整的堆栈跟踪
        }
    }
}
