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
import com.volcengine.hiagent.api.model.GetEvaTaskRequest;
import com.volcengine.hiagent.api.model.base.EvaTaskItem;
import com.volcengine.sign.Credentials;

import java.util.List;

public class TestGetEvaTask {
    public static void main(String[] args) {
        // 从环境变量获取配置信息
        String ak = System.getenv("VOLC_ACCESSKEY");
        String sk = System.getenv("VOLC_SECRETKEY");
        String region = System.getenv("HIAGENT_TOP_REGION");
        String endpoint = System.getenv("HIAGENT_TOP_ENDPOINT");

        // 获取任务所需的基本参数
        String workspaceID = System.getenv("WORKSPACE_ID");
        String taskID = System.getenv("TASK_ID");
        // 可选：如果没有提供taskID，可以尝试通过taskName查询
        String taskName = System.getenv("TASK_NAME");

        // 创建API客户端
        ApiClient apiClient = new ApiClient()
                .setCredentials(Credentials.getCredentials(ak, sk))
                .setRegion(region).setEndpoint(endpoint).setDisableSSL(true);

        // 创建EvaClient实例
        EvaClient api = new EvaClient(apiClient);

        // 创建获取任务请求对象
        GetEvaTaskRequest getEvaTaskReq = new GetEvaTaskRequest();
        getEvaTaskReq.setWorkspaceID(workspaceID);

        // 设置任务ID或任务名称（至少设置一个）
        if (taskID != null && !taskID.isEmpty()) {
            getEvaTaskReq.setTaskID(taskID);
        } else if (taskName != null && !taskName.isEmpty()) {
            getEvaTaskReq.setTaskName(taskName);
        } else {
            System.err.println("错误：必须提供任务ID或任务名称");
            return;
        }

        try {
            // 调用API获取任务信息
            EvaTaskItem taskItem = api.getEvaTask(getEvaTaskReq);

            // 打印任务基本信息
            System.out.println("获取任务信息成功");
            System.out.println("任务ID: " + taskItem.getTaskID());
            System.out.println("任务名称: " + taskItem.getName());
            System.out.println("任务描述: " + (taskItem.getDescription() != null ? taskItem.getDescription() : "无"));
            System.out.println("租户ID: " + taskItem.getTenantID());
            System.out.println("工作区ID: " + taskItem.getWorkspaceID());
            System.out.println("目标类型: " + taskItem.getTargetType());
            System.out.println("状态: " + taskItem.getStatus());
            if (taskItem.getStatusMessage() != null) {
                System.out.println("状态消息: " + taskItem.getStatusMessage());
            }

            // 打印时间信息
            System.out.println("创建时间: " + taskItem.getCreatedAt());
            System.out.println("更新时间: " + taskItem.getUpdatedAt());
            System.out.println("创建人: " + taskItem.getCreatedBy());
            System.out.println("更新人: " + taskItem.getUpdatedBy());

            // 打印执行信息
            if (taskItem.getStartedAt() != null) {
                System.out.println("开始时间: " + taskItem.getStartedAt());
            }
            if (taskItem.getCompletedAt() != null) {
                System.out.println("完成时间: " + taskItem.getCompletedAt());
            }
            System.out.println("总耗时: " + taskItem.getDuration() + "ms");
            System.out.println("消耗Token数: " + taskItem.getCostTokens());

            // 打印进度信息（如果有）
            if (taskItem.getProgress() != null) {
                System.out.println("\n进度信息:");
                System.out.println("  总任务数: " + taskItem.getProgress().getTotal());
                System.out.println("  已完成数: " + taskItem.getProgress().getCompleted());
            }

            // 打印规则集信息（如果有）
            if (taskItem.getRuleset() != null) {
                System.out.println("\n规则集信息:");
                System.out.println("  规则集ID: " + taskItem.getRuleset().getRulesetID());
                System.out.println("  规则集名称: " + taskItem.getRuleset().getRulesetName());
            }

            // 打印数据集信息（如果有）
            if (taskItem.getDataset() != null) {
                System.out.println("\n数据集信息:");
                System.out.println("  数据集ID: " + taskItem.getDataset().getDatasetID());
                System.out.println("  数据集名称: " + taskItem.getDataset().getDatasetName());
            }

            // 打印目标信息（如果有）
            List<?> targets = taskItem.getTargets();
            if (targets != null && !targets.isEmpty()) {
                System.out.println("\n目标数量: " + targets.size());
                // 注意：这里没有打印具体的目标详情，因为EvaTaskItemTarget的结构没有在提供的代码中完全显示
                // 如果需要，可以根据实际的类结构添加相应的处理逻辑
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
}