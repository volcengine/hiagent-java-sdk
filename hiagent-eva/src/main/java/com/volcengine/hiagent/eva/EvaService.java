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

import com.volcengine.hiagent.api.EvaClient;
import com.volcengine.hiagent.api.model.eva.*;
import com.volcengine.hiagent.api.model.eva.base.Cell;
import com.volcengine.hiagent.api.model.eva.base.EvaTaskItem;
import com.volcengine.hiagent.api.model.eva.base.EvaTaskResultUpdateTargetContent;
import com.volcengine.hiagent.api.model.eva.base.ModelAgentConfig;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class EvaService {
    public interface Interface {
        public CreateEvaTaskResponse CreateEvaTask(CreateEvaTaskRequest req) throws Error;

        public ExecEvaTaskRowGroupResponse ExecEvaTaskRowGroup(ExecEvaTaskRowGroupRequest req) throws Error;

        public EvaTaskItem GetEvaTask(GetEvaTaskRequest req) throws Error;

        public GetEvaTaskReportResponse GetEvaTaskReport(GetEvaTaskReportRequest req) throws Error;

        public ListEvaDatasetColumnsResponse ListEvaDatasetColumns(ListEvaDatasetColumnsRequest req) throws Error;

        public ListEvaDatasetConversationsResponse ListEvaDatasetConversations(ListEvaDatasetConversationsRequest req) throws Error;
    }

    public static class Client implements Interface {
        private EvaClient client;
        @Override
        public CreateEvaTaskResponse CreateEvaTask(CreateEvaTaskRequest req) throws Error {
            return null;
        }

        @Override
        public ExecEvaTaskRowGroupResponse ExecEvaTaskRowGroup(ExecEvaTaskRowGroupRequest req) throws Error {
            return null;
        }

        @Override
        public EvaTaskItem GetEvaTask(GetEvaTaskRequest req) throws Error {
            return null;
        }

        @Override
        public GetEvaTaskReportResponse GetEvaTaskReport(GetEvaTaskReportRequest req) throws Error {
            return null;
        }

        @Override
        public ListEvaDatasetColumnsResponse ListEvaDatasetColumns(ListEvaDatasetColumnsRequest req) throws Error {
            return null;
        }

        @Override
        public ListEvaDatasetConversationsResponse ListEvaDatasetConversations(ListEvaDatasetConversationsRequest req) throws Error {
            return null;
        }
    }

    private CreateEvaTaskResponse createTask(
            Client client,
            String workspaceID,
            String datasetID,
            String datasetVersionID,
            String taskName,
            String rulesetID,
            @Nullable String description,
            @Nullable ModelAgentConfig modelAgentConfig,
            boolean runImmediately
    ){
        return new CreateEvaTaskResponse();
    }

    private CreateEvaTaskResponse convert_to_case_data_list(
            Client client,
            String workspaceID,
            String datasetID,
            String datasetVersionID,
            String taskName,
            String rulesetID,
            @Nullable String description,
            @Nullable ModelAgentConfig modelAgentConfig,
            boolean runImmediately
    ) {
        return new CreateEvaTaskResponse();
    }

    public GetEvaTaskReportResponse run(String workspaceID, String targetID, String datasetID, String datasetVersionID, String taskName, String rulesetID, long maxConversations, ModelAgentConfig targetConfig, InferenceFunction inferenceFunction) throws Error {
        System.out.println("EVA service running...");
        try {
            var client = new Client();
            // 1. Create evaluation task
            System.out.printf("Creating evaluation task: %s\n", taskName);
            var taskID = createTask(client, workspaceID, datasetID, datasetVersionID, taskName, rulesetID, null, targetConfig, true).getTaskID();
            System.out.printf("Task created successfully: %s\n", taskID);
            // 2. Get dataset column information
            System.out.println("Fetching dataset columns...");
            var columns = client.ListEvaDatasetColumns(new ListEvaDatasetColumnsRequest(workspaceID, datasetID, datasetVersionID, false)).getColumns();
            System.out.printf("Fetched %d columns", columns.size());
            var columnID2Name = new HashMap<String, String>();
            columns.forEach(column -> {
                columnID2Name.put(column.getID(), column.getName());
            });
            // 3. Get dataset conversations
            System.out.println("Fetching dataset conversations...");
            var listCases = client.ListEvaDatasetConversations(new ListEvaDatasetConversationsRequest(
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
                client.ExecEvaTaskRowGroup(new ExecEvaTaskRowGroupRequest(
                        workspaceID,
                        taskID,
                        caseItem.getDatasetCaseID(),
                        new ArrayList<EvaTaskResultUpdateTargetContent>() {{
                            add(new EvaTaskResultUpdateTargetContent(
                                    "CustomAPP",
                                    targetID,
                                    inferenceFunction.execute(caseData)
                            ));
                        }}
                ));
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
            } while (!terminalEvaTaskStatus.contains(client.GetEvaTask(new GetEvaTaskRequest(
                    workspaceID,
                    taskID,
                    null
            )).getStatus()));
            // 6. Get evaluation report
            var getReportResp = client.GetEvaTaskReport(new GetEvaTaskReportRequest(
                    workspaceID,
                    taskID
            ));
            assert getReportResp.getRules() != null;
            System.out.printf("Evaluation completed with status: [%s]\n", getReportResp.getRules().isEmpty() ? "Running" : "Completed");
            return getReportResp;
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
