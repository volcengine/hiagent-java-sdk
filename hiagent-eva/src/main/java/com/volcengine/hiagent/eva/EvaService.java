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
import com.volcengine.hiagent.api.model.eva.base.ModelAgentConfig;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.Null;

public class EvaService {
    public interface Interface {
        public CreateEvaTaskResponse CreateEvaTask(CreateEvaTaskRequest req) throws Error;

        public ExecEvaTaskRowGroupResponse ExecEvaTaskRowGroup(ExecEvaTaskRowGroupRequest req) throws Error;

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
    public CreateEvaTaskResponse createTask(
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
    public GetEvaTaskReportResponse run(String workspaceID, String datasetID, String datasetVersionID, String taskName, String rulesetID, long maxConversations, ModelAgentConfig targetConfig, InferenceFunction inferenceFunction) throws Error {
        System.out.println("EVA service running...");
        try {
            var client = new Client();
            // 1. Create evaluation task
            System.out.printf("Creating evaluation task: %s\n", taskName);
            var createTaskResp = createTask(client, workspaceID,datasetID,datasetVersionID,taskName,rulesetID,null, targetConfig, true);
            System.out.printf("Task created successfully: %s\n",createTaskResp.getTaskID());
            // 2. Get dataset column information
            System.out.println("Fetching dataset columns...");
            var listColumnsResponse = client.ListEvaDatasetColumns(new ListEvaDatasetColumnsRequest(workspaceID,datasetID,datasetVersionID,false));
            System.out.printf("Fetched %d columns",listColumnsResponse.getColumns().size());
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
            listCases.getItems().forEach(listCasesItem -> {

            });
            // 5. Wait for processing to complete
            // 6. Get evaluation report
            return new GetEvaTaskReportResponse();
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
