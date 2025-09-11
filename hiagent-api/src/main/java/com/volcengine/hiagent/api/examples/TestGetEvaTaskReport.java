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
import com.volcengine.hiagent.api.model.GetEvaTaskReportRequest;
import com.volcengine.hiagent.api.model.GetEvaTaskReportResponse;
import com.volcengine.hiagent.api.model.base.GetEvaTaskReportRule;
import com.volcengine.hiagent.api.model.base.GetEvaTaskReportRuleTarget;
import com.volcengine.hiagent.api.model.base.GetEvaTaskReportTarget;
import com.volcengine.sign.Credentials;

import java.util.List;

public class TestGetEvaTaskReport {
    public static void main(String[] args) {
        // 从环境变量获取配置信息
        String ak = System.getenv("VOLC_ACCESSKEY");
        String sk = System.getenv("VOLC_SECRETKEY");
        String region = System.getenv("HIAGENT_TOP_REGION");
        String endpoint = System.getenv("HIAGENT_TOP_ENDPOINT");

        // 获取任务报告所需的基本参数
        String workspaceID = System.getenv("WORKSPACE_ID");
        String taskID = System.getenv("TASK_ID");

        // 创建API客户端
        ApiClient apiClient = new ApiClient()
                .setCredentials(Credentials.getCredentials(ak, sk))
                .setRegion(region).setEndpoint(endpoint).setDisableSSL(true);

        // 创建EvaClient实例
        EvaClient api = new EvaClient(apiClient);

        // 创建获取任务报告请求对象
        GetEvaTaskReportRequest getEvaTaskReportReq = new GetEvaTaskReportRequest();
        getEvaTaskReportReq.setWorkspaceID(workspaceID);
        getEvaTaskReportReq.setTaskID(taskID);

        try {
            // 调用API获取任务报告
            GetEvaTaskReportResponse response = api.getEvaTaskReport(getEvaTaskReportReq);

            // 打印响应结果
            System.out.println("获取任务报告成功");
            System.out.println("创建时间: " + response.getCreatedAt());
            System.out.println("更新时间: " + response.getUpdatedAt());
            System.out.println("创建人: " + response.getCreatedBy());
            System.out.println("更新人: " + response.getUpdatedBy());

            // 打印规则信息（如果有）
            List<GetEvaTaskReportRule> rules = response.getRules();
            if (rules != null && !rules.isEmpty()) {
                System.out.println("\n规则信息:");
                for (GetEvaTaskReportRule rule : rules) {
                    System.out.println("  规则ID: " + rule.getRuleID());

                    // 打印规则目标信息
                    List<GetEvaTaskReportRuleTarget> ruleTargets = rule.getTargets();
                    if (ruleTargets != null && !ruleTargets.isEmpty()) {
                        System.out.println("    规则目标:");
                        for (GetEvaTaskReportRuleTarget ruleTarget : ruleTargets) {
                            System.out.println("      目标ID: " + ruleTarget.getTargetID());
                            System.out.println("      得分: " + ruleTarget.getAvgScore());
                            System.out.println("      消耗token数: " + ruleTarget.getCostTokens() + "%");
                        }
                    }
                }
            }

            // 打印目标信息（如果有）
            List<GetEvaTaskReportTarget> targets = response.getTargets();
            if (targets != null && !targets.isEmpty()) {
                System.out.println("\n目标统计信息:");
                for (GetEvaTaskReportTarget target : targets) {
                    System.out.println("  目标ID: " + target.getTargetID());
                    System.out.println("  总耗时: " + target.getDuration() + "ms");
                    System.out.println("  总消耗token数: " + target.getCostTokens());
                    System.out.println("  平均消耗token数: " + target.getAvgCostTokens());
                    System.out.println("  平均耗时: " + target.getAvgDuration() + "ms");
                    System.out.println("  平均首字符响应时间: " + target.getAvgTTFT() + "ms");
                    System.out.println();
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
}