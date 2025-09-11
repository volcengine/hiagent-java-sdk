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
import com.volcengine.hiagent.api.model.ExecEvaTaskRowGroupRequest;
import com.volcengine.hiagent.api.model.ExecEvaTaskRowGroupResponse;
import com.volcengine.hiagent.api.model.base.EvaTaskResultTargetContentPair;
import com.volcengine.hiagent.api.model.base.EvaTaskResultUpdateTargetContent;
import com.volcengine.sign.Credentials;

import java.util.ArrayList;
import java.util.List;

public class TestExecEvaTaskRowGroup {
    public static void main(String[] args) {
        // 从环境变量获取配置信息
        String ak = System.getenv("VOLC_ACCESSKEY");
        String sk = System.getenv("VOLC_SECRETKEY");
        String region = System.getenv("HIAGENT_TOP_REGION");
        String endpoint = System.getenv("HIAGENT_TOP_ENDPOINT");

        // 执行任务所需的基本参数
        String workspaceID = System.getenv("WORKSPACE_ID");
        String taskID = System.getenv("TASK_ID");
        String rowID = System.getenv("ROW_ID");

        // 创建API客户端
        ApiClient apiClient = new ApiClient()
                .setCredentials(Credentials.getCredentials(ak, sk))
                .setRegion(region).setEndpoint(endpoint).setDisableSSL(true);

        // 创建EvaClient实例
        EvaClient api = new EvaClient(apiClient);

        // 创建执行任务行组请求对象
        ExecEvaTaskRowGroupRequest execEvaTaskRowGroupReq = new ExecEvaTaskRowGroupRequest();
        execEvaTaskRowGroupReq.setWorkspaceID(workspaceID);
        execEvaTaskRowGroupReq.setTaskID(taskID);
        execEvaTaskRowGroupReq.setRowID(rowID);

        // 创建目标结果列表（可选）
        List<EvaTaskResultUpdateTargetContent> targetResults = new ArrayList<>();

        // 创建一个目标结果
        EvaTaskResultUpdateTargetContent targetResult = new EvaTaskResultUpdateTargetContent();
        targetResult.setTargetType("builtin_model");
        targetResult.setTargetID("model123");

        // 创建结果对列表
        List<EvaTaskResultTargetContentPair> results = new ArrayList<>();

        // 创建一个结果对
        EvaTaskResultTargetContentPair resultPair = new EvaTaskResultTargetContentPair();
        resultPair.setContent("这是模型生成的响应内容");
        resultPair.setContentThought("这是模型生成内容的思考过程");
        resultPair.setRound(1);
        resultPair.setStatus("success");

        // 将结果对添加到结果列表
        results.add(resultPair);

        // 设置结果列表到目标结果
        targetResult.setResults(results);

        // 将目标结果添加到目标结果列表
        targetResults.add(targetResult);

        // 设置目标结果列表到请求对象（可选）
        execEvaTaskRowGroupReq.setTargetResults(targetResults);

        try {
            // 调用API执行任务行组
            ExecEvaTaskRowGroupResponse response = api.execEvaTaskRowGroup(execEvaTaskRowGroupReq);

            // 打印响应结果
            System.out.println("执行任务行组成功");
            // 注意：根据提供的类定义，ExecEvaTaskRowGroupResponse目前没有任何属性可以打印
            // 如果需要，可以根据实际API返回添加相应的处理逻辑
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