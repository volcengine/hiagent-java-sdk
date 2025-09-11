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
import com.volcengine.hiagent.api.model.ListEvaDatasetConversationsRequest;
import com.volcengine.hiagent.api.model.ListEvaDatasetConversationsResponse;
import com.volcengine.hiagent.api.model.base.DatasetCaseItem;
import com.volcengine.hiagent.api.model.base.ListDatasetDataFilter;
import com.volcengine.hiagent.api.model.base.ListSort;
import com.volcengine.hiagent.api.model.base.Cell;
import com.volcengine.sign.Credentials;

import java.util.*;

public class TestListEvaDatasetConversations {
    public static void main(String[] args) {
        // 从环境变量获取配置信息
        String ak = System.getenv("VOLC_ACCESSKEY");
        String sk = System.getenv("VOLC_SECRETKEY");
        String region = System.getenv("HIAGENT_TOP_REGION");
        String endpoint = System.getenv("HIAGENT_TOP_ENDPOINT");

        // 列出数据集对话所需的参数
        String workspaceID = System.getenv("WORKSPACE_ID");
        String datasetID = System.getenv("DATASET_ID");
        String versionID = System.getenv("VERSION_ID");
        // 设置是否使用最新发布的版本
        boolean useLatestPublishedVersion = Boolean.parseBoolean(System.getenv("USE_LATEST_VERSION"));

        // 分页参数
        long pageNumber = 1;
        long pageSize =10;
        long offset = 0;

        // 创建API客户端
        ApiClient apiClient = new ApiClient()
                .setCredentials(Credentials.getCredentials(ak, sk))
                .setRegion(region).setEndpoint(endpoint).setDisableSSL(true);

        // 创建EvaClient实例
        EvaClient api = new EvaClient(apiClient);

        // 创建列出数据集对话请求对象
        ListEvaDatasetConversationsRequest listEvaDatasetConversationsReq = new ListEvaDatasetConversationsRequest();
        listEvaDatasetConversationsReq.setWorkspaceID(workspaceID);
        listEvaDatasetConversationsReq.setDatasetID(datasetID);
        listEvaDatasetConversationsReq.setVersionID(versionID);
        listEvaDatasetConversationsReq.setUseLatestPublishedVersion(useLatestPublishedVersion);
        listEvaDatasetConversationsReq.setPageNumber(pageNumber);
        listEvaDatasetConversationsReq.setPageSize(pageSize);
        listEvaDatasetConversationsReq.setOffset(offset);

        // 可选：设置筛选条件
        boolean filterAnnotated = false;
        if (filterAnnotated) {
            ListDatasetDataFilter filter = new ListDatasetDataFilter();
            filter.setAnnotated(true);
            listEvaDatasetConversationsReq.setFilter(filter);
        }

        // 可选：设置排序规则
        List<ListSort> sorts = new ArrayList<>();
        ListSort sort = new ListSort();
        sort.setSortBy("created_at");
        sort.setSortOrder("desc");
        sorts.add(sort);
        listEvaDatasetConversationsReq.setSorts(sorts);

        try {
            // 调用API列出数据集对话
            ListEvaDatasetConversationsResponse response = api.listEvaDatasetConversations(listEvaDatasetConversationsReq);

            // 获取对话列表
            List<DatasetCaseItem> items = response.getItems();

            // 打印响应结果
            System.out.println("列出数据集对话成功");
            System.out.println("数据集ID: " + datasetID);
            System.out.println("版本ID: " + versionID);
            System.out.println("使用最新发布版本: " + useLatestPublishedVersion);
            System.out.println("总对话数: " + response.getTotal());
            System.out.println("已标注对话数: " + response.getAnnotatedTotal());
            System.out.println("当前页对话数: " + (items != null ? items.size() : 0));
            System.out.println("当前页码: " + pageNumber);
            System.out.println("每页大小: " + pageSize);
            System.out.println("偏移量: " + offset);

            // 打印每个对话的详细信息
            if (items != null && !items.isEmpty()) {
                System.out.println("\n对话详细信息:");
                System.out.println("--------------------------------------------------");

                int index = 0;
                for (DatasetCaseItem item : items) {
                    System.out.println("对话 " + (++index) + ":");
                    System.out.println("  对话ID: " + (item.getDatasetCaseID() != null ? item.getDatasetCaseID() : "无"));
                    System.out.println("  是否已标注: " + (item.isAnnotated() ? "是" : "否"));

                    // 打印对话数据（如果有）
                    if (item.getData() != null && !item.getData().isEmpty()) {
                        System.out.println("  对话数据:");
                        for (Map.Entry<String, Cell> entry : item.getData().entrySet()) {
                            System.out.println("    " + entry.getKey() + ": " + getCellContent(entry.getValue()));
                        }
                    }

                    // 打印重复数据（如果有）
                    if (item.getRepeatedData() != null && !item.getRepeatedData().isEmpty()) {
                        System.out.println("  重复数据项数量: " + item.getRepeatedData().size());
                    }

                    System.out.println("--------------------------------------------------");
                }
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
     * 辅助方法：获取Cell对象的内容（简化版）
     */
    private static String getCellContent(Cell cell) {
        if (cell == null) {
            return "null";
        }

        // 注意：这里简化处理了Cell对象的内容获取
        // 实际使用中可能需要根据Cell类的完整定义来正确解析其内容
        try {
            // 假设Cell类有getContent或类似方法可以获取内容
            return "(Cell内容)";
        } catch (Exception e) {
            return "无法解析的Cell内容";
        }
    }

    /**
     * 辅助方法：获取环境变量，如果不存在则返回默认值
     */
    private static String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return value != null ? value : defaultValue;
    }
}