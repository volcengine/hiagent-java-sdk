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
import com.volcengine.hiagent.api.EvaClient;
import com.volcengine.hiagent.api.model.ListEvaDatasetColumnsRequest;
import com.volcengine.hiagent.api.model.ListEvaDatasetColumnsResponse;
import com.volcengine.hiagent.api.model.base.EvaDatasetColumn;
import com.volcengine.sign.Credentials;

import java.util.List;

public class TestListEvaDatasetColumns {
    public static void main(String[] args) {
        // 从环境变量获取配置信息
        String ak = System.getenv("VOLC_ACCESSKEY");
        String sk = System.getenv("VOLC_SECRETKEY");
        String region = System.getenv("HIAGENT_TOP_REGION");
        String endpoint = System.getenv("HIAGENT_TOP_ENDPOINT");

        // 列出数据集列所需的参数
        String workspaceID = System.getenv("WORKSPACE_ID");
        String datasetID = System.getenv("DATASET_ID");
        String versionID = System.getenv("VERSION_ID");
        // 设置是否使用最新发布的版本
        boolean useLatestPublishedVersion = false;

        // 创建API客户端
        ApiClient apiClient = new ApiClient()
                .setCredentials(Credentials.getCredentials(ak, sk))
                .setRegion(region).setEndpoint(endpoint).setDisableSSL(true);

        // 创建EvaClient实例
        EvaClient api = new EvaClient(apiClient);

        // 创建列出数据集列请求对象
        ListEvaDatasetColumnsRequest listEvaDatasetColumnsReq = new ListEvaDatasetColumnsRequest();
        listEvaDatasetColumnsReq.setWorkspaceID(workspaceID);
        listEvaDatasetColumnsReq.setDatasetID(datasetID);
        listEvaDatasetColumnsReq.setVersionID(versionID);
        listEvaDatasetColumnsReq.setUseLatestPublishedVersion(useLatestPublishedVersion);

        try {
            // 调用API列出数据集列
            ListEvaDatasetColumnsResponse response = api.listEvaDatasetColumns(listEvaDatasetColumnsReq);

            // 获取列列表
            List<EvaDatasetColumn> columns = response.getColumns();

            // 打印响应结果
            System.out.println("列出数据集列成功");
            System.out.println("数据集ID: " + datasetID);
            System.out.println("版本ID: " + versionID);
            System.out.println("使用最新发布版本: " + useLatestPublishedVersion);
            System.out.println("列总数: " + (columns != null ? columns.size() : 0));

            // 打印每列的详细信息
            if (columns != null && !columns.isEmpty()) {
                System.out.println("\n列详细信息:");
                System.out.println("--------------------------------------------------");
                System.out.printf("%-15s %-20s %-40s\n", "列ID", "列名称", "描述");
                System.out.println("--------------------------------------------------");

                for (EvaDatasetColumn column : columns) {
                    String description = column.getDescription() != null ? column.getDescription() : "无";
                    System.out.printf("%-15s %-20s %-40s\n",
                            column.getID(),
                            column.getName(),
                            truncate(description, 38));
                }

                System.out.println("--------------------------------------------------");
            }
        } catch (ApiException e) {
            // 处理API调用异常
            System.err.println("API调用发生异常:");
            System.err.println("错误码: " + e.getCode());
            System.err.println("错误信息: " + e.getMessage());
            System.err.println("响应体: " + e.getResponseBody());
            System.err.println("完整堆栈跟踪:");
            e.printStackTrace(); // 打印完整的堆栈跟踪
        }
    }

    /**
     * 辅助方法：如果字符串长度超过指定长度，截断并添加省略号
     */
    private static String truncate(String str, int maxLength) {
        if (str == null) {
            return "无";
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength) + "...";
    }
}