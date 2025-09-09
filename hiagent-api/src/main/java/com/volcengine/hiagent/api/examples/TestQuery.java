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
