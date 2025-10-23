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
import com.volcengine.hiagent.api.model.CreateEvaTaskRequest;
import com.volcengine.hiagent.api.model.CreateEvaTaskResponse;
import com.volcengine.hiagent.api.model.base.*;
import com.volcengine.sign.Credentials;

import java.util.ArrayList;
import java.util.List;

import static com.volcengine.hiagent.api.model.base.EvaTargetType.TargetTypeBuiltinModel;

public class TestCreateEvaTask {
    public static void main(String[] args) {
        // 从环境变量获取配置信息
        String ak = System.getenv("VOLC_ACCESSKEY");
        String sk = System.getenv("VOLC_SECRETKEY");
        String region = System.getenv("HIAGENT_TOP_REGION");
        String endpoint = System.getenv("HIAGENT_TOP_ENDPOINT");

        // 任务所需的基本参数
        String workspaceID = System.getenv("WORKSPACE_ID");
        String datasetID = System.getenv("DATASET_ID");
        String rulesetID = System.getenv("RULESET_ID");
        String taskName = "测试评估任务";
        String taskDescription = "这是一个通过API创建的测试评估任务";

        // 创建API客户端
        ApiClient apiClient = new ApiClient()
                .setCredentials(Credentials.getCredentials(ak, sk))
                .setRegion(region).setEndpoint(endpoint).setDisableSSL(true);

        // 创建EvaClient实例
        EvaClient api = new EvaClient(apiClient);

        // 创建评估任务请求对象
        CreateEvaTaskRequest createEvaTaskReq = new CreateEvaTaskRequest();
        createEvaTaskReq.setWorkspaceID(workspaceID);
        createEvaTaskReq.setName(taskName);
        createEvaTaskReq.setDescription(taskDescription);
        createEvaTaskReq.setDatasetID(datasetID);
        createEvaTaskReq.setRulesetID(rulesetID);
        createEvaTaskReq.setRunImmediately(true);

        // 可选的数据集配置
        DatasetTaskConfig datasetConfig = new DatasetTaskConfig();
        datasetConfig.setDataStartIndex(0);
        datasetConfig.setDataEndIndex(100);
        datasetConfig.setUseLatestVersion(true);
        createEvaTaskReq.setDatasetConfig(datasetConfig);

        // 创建评估目标列表
        List<EvaTaskTarget> targets = new ArrayList<>();

        // 创建一个内置模型目标
        EvaTaskTarget target = new EvaTaskTarget();
        target.setType(TargetTypeBuiltinModel); // 设置目标类型
        target.setTargetID("model123");  // 设置目标ID
        target.setTargetName("测试模型"); // 设置目标名称
        target.setQPS(10);              // 设置每秒查询率

        // 创建目标配置
        EvaTargetConfig targetConfig = new EvaTargetConfig();

        // 创建内置模型配置
        EvaTargetBuiltinModelConfig modelConfig = new EvaTargetBuiltinModelConfig();
        modelConfig.setModelID("your_model_id");
        modelConfig.setModelName("测试模型名称");
        modelConfig.setModelType("llm");

        // 创建模型代理配置
        ModelAgentConfig modelAgentConfig = new ModelAgentConfig();
        modelAgentConfig.setTemperature(0.7);
        modelAgentConfig.setTopP(0.9);
        modelAgentConfig.setMaxTokens(2048);
        modelAgentConfig.setRoundsReserved(5);
        modelAgentConfig.setRagEnabled(false);

        // 设置模型代理配置到模型配置
        modelConfig.setModelAgentConfig(modelAgentConfig);

        // 设置模型配置到目标配置
        targetConfig.setBuiltinModelConfig(modelConfig);

        // 设置目标配置到评估目标
        target.setTargetConfig(targetConfig);

        // 将目标添加到目标列表
        targets.add(target);

        // 设置目标列表到请求对象
        createEvaTaskReq.setTargets(targets);

        try {
            // 调用API创建评估任务
            CreateEvaTaskResponse response = api.createEvaTask(createEvaTaskReq);

            // 打印响应结果
            System.out.println("评估任务创建成功");
            System.out.println("任务ID: " + response.getTaskID());
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