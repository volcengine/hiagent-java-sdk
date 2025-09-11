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
package com.volcengine.hiagent.eva;

import com.volcengine.ApiClient;
import com.volcengine.ApiException;
import com.volcengine.hiagent.api.EvaClient;
import com.volcengine.hiagent.api.model.*;
import com.volcengine.hiagent.api.model.base.*;
import com.volcengine.sign.Credentials;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class EvaService {
    private static final Logger logger = Logger.getLogger(EvaService.class.getName());

    private final String workspaceID;
    private final String appID;
    private final EvaClient evaClient;

    public EvaService(String endpoint, String ak, String sk, String workspaceID, String appID) {
        this.workspaceID = workspaceID;
        this.appID = appID;
        ApiClient apiClient = new ApiClient()
                .setCredentials(Credentials.getCredentials(ak, sk))
                .setRegion("cn-north-1")
                .setEndpoint(endpoint)
                .setDisableSSL(true);

        this.evaClient = new EvaClient(apiClient);
    }

    private CreateEvaTaskResponse createTask(
            EvaClient client,
            String workspaceID,
            String datasetID,
            String datasetVersionID,
            String taskName,
            String rulesetID,
            @Nullable String description,
            @Nullable ModelAgentConfig modelAgentConfig,
            boolean runImmediately
    ){
        try {
            // 创建评估任务请求对象
            CreateEvaTaskRequest request = new CreateEvaTaskRequest();
            request.setWorkspaceID(workspaceID);
            request.setDatasetID(datasetID);
            request.setDatasetVersionID(datasetVersionID);
            request.setName(taskName);
            request.setRulesetID(rulesetID);
            request.setDescription(description);
            request.setRunImmediately(runImmediately);

            // 创建目标列表
            ArrayList<EvaTaskTarget> targets = new ArrayList<>();

            // 创建评估目标
            EvaTaskTarget target = new EvaTaskTarget();
            target.setType("CustomAPP");
            target.setTargetID(appID);
            target.setTargetName("Custom Application");
            target.setQPS(10); // 设置每秒查询率

            // 创建目标配置
            EvaTargetConfig targetConfig = new EvaTargetConfig();

            // 如果提供了模型代理配置，则创建内置模型配置
            if (modelAgentConfig != null) {
                EvaTargetBuiltinModelConfig builtinModelConfig = new EvaTargetBuiltinModelConfig();
                builtinModelConfig.setModelAgentConfig(modelAgentConfig);
                targetConfig.setBuiltinModelConfig(builtinModelConfig);
            }

            // 设置目标配置
            target.setTargetConfig(targetConfig);

            // 添加目标到列表
            targets.add(target);
            request.setTargets(targets);

            // 调用API创建任务
            CreateEvaTaskResponse response = client.createEvaTask(request);
            logger.info("Created evaluation task: " + response.getTaskID());
            return response;
        } catch (ApiException e) {
            logger.severe("Failed to create evaluation task: " + e.getMessage());
            throw new RuntimeException("Failed to create evaluation task", e);
        }
    }

    public GetEvaTaskReportResponse run(String datasetID, String datasetVersionID, String taskName, String rulesetID, long maxConversations, ModelAgentConfig targetConfig, InferenceFunction inferenceFunction) throws ApiException {
        System.out.println("EVA service running...");
        try {
            // 1. Create evaluation task
            System.out.printf("Creating evaluation task: %s\n", taskName);
            var taskID = createTask(this.evaClient, this.workspaceID, datasetID, datasetVersionID, taskName, rulesetID, null, targetConfig, true).getTaskID();
            System.out.printf("Task created successfully: %s\n", taskID);
            // 2. Get dataset column information
            System.out.println("Fetching dataset columns...");
            var columns = this.evaClient.listEvaDatasetColumns(new ListEvaDatasetColumnsRequest(workspaceID, datasetID, datasetVersionID, false)).getColumns();
            System.out.printf("Fetched %d columns", columns.size());
            var columnID2Name = new HashMap<String, String>();
            columns.forEach(column -> {
                columnID2Name.put(column.getID(), column.getName());
            });
            // 3. Get dataset conversations
            System.out.println("Fetching dataset conversations...");
            var listCases = this.evaClient.listEvaDatasetConversations(new ListEvaDatasetConversationsRequest(
                    workspaceID,
                    datasetID,
                    datasetVersionID,
                    false,
                    null,
                    null,
                    1,
                    maxConversations,
                    0
            ));
            System.out.printf("Fetched %d cases",listCases.getItems().size());
            // 4. Execute inference and submit results
            System.out.println("Running inference and submitting results...");
            listCases.getItems().forEach(caseItem -> {
                var caseData = new ArrayList<Map<String, Cell>>();
                assert caseItem.getRepeatedData() != null;
                caseItem.getRepeatedData().forEach(repeatedDataItem -> {
                    var rowData = new HashMap<String, Cell>();
                    repeatedDataItem.keySet().forEach(key -> {
                        rowData.put(columnID2Name.get(key), repeatedDataItem.get(key));
                    });
                    caseData.add(rowData);
                });
                var appID = this.appID;
                try {
                    this.evaClient.execEvaTaskRowGroup(new ExecEvaTaskRowGroupRequest(
                            workspaceID,
                            taskID,
                            caseItem.getDatasetCaseID(),
                            new ArrayList<EvaTaskResultUpdateTargetContent>() {{
                                add(new EvaTaskResultUpdateTargetContent(
                                        "CustomAPP",
                                        appID,
                                        inferenceFunction.execute(caseData)
                                ));
                            }}
                    ));
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
                System.out.printf("Results submitted for row [%s]\n", caseItem.getDatasetCaseID());
            });
            // 5. Wait for processing to complete
            System.out.println("Waiting for evaluation to complete...");
            var terminalEvaTaskStatus = new ArrayList<String>() {{
                add("Succeed");
                add("PartialSucceed");
                add("Failed");
                add("Cancelled");
                add("Paused");
            }};
            var retryCount = 0;
            do {
                sleep(1000);
                retryCount++;
                if (retryCount > 100) {
                    break;
                }
            } while (!terminalEvaTaskStatus.contains(this.evaClient.getEvaTask(new GetEvaTaskRequest(
                    workspaceID,
                    taskID,
                    null
            )).getStatus()));
            // 6. Get evaluation report
            var getReportResp = this.evaClient.getEvaTaskReport(new GetEvaTaskReportRequest(
                    workspaceID,
                    taskID
            ));
            assert getReportResp.getRules() != null;
            System.out.printf("Evaluation completed with status: [%s]\n", getReportResp.getRules().isEmpty() ? "Running" : "Completed");
            return getReportResp;
        } catch (InterruptedException e) {
            throw new ApiException(e);
        }
    }
}
