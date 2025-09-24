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
import com.volcengine.hiagent.api.ObserveClient;
import com.volcengine.hiagent.api.ToolClient;
import com.volcengine.hiagent.api.model.GetArchivedToolRequest;
import com.volcengine.hiagent.api.model.GetArchivedToolResponse;
import com.volcengine.hiagent.api.model.ListTraceSpansRequest;
import com.volcengine.hiagent.api.model.ListTraceSpansResponse;
import com.volcengine.sign.Credentials;

public class TestGetArchivedTool {
  public static void main(String[] args) {
    String ak = System.getenv("VOLC_ACCESSKEY");
    String sk = System.getenv("VOLC_SECRETKEY");
    String region = System.getenv("HIAGENT_TOP_REGION");
    String endpoint = System.getenv("HIAGENT_TOP_ENDPOINT");

    String workspaceID = System.getenv("WORKSPACE_ID");
    String toolID = System.getenv("TOOL_ID");

    ApiClient apiClient = new ApiClient()
        .setCredentials(Credentials.getCredentials(ak, sk))
        .setRegion(region).setEndpoint(endpoint).setDisableSSL(true);

    ToolClient api = new ToolClient(apiClient);

    try {
      GetArchivedToolResponse response = getArchivedTool(api, workspaceID, toolID);
      System.out.println("PluginID: " + response.getPluginID());
      System.out.println("Name: " + response.getName());
      System.out.println("Description: " + response.getDescription());
      System.out.println("InputSchema: " + response.getInputSchema());
    } catch (ApiException e) {
      System.err.println("API调用发生异常:");
      System.err.println("错误码: " + e.getCode());
      System.err.println("错误信息: " + e.getMessage());
      System.err.println("响应体: " + e.getResponseBody());
      System.err.println("完整堆栈跟踪:");
      e.printStackTrace(); // 这会打印完整的堆栈跟踪
    }
  }

  private static GetArchivedToolResponse getArchivedTool(ToolClient api, String workspaceID, String toolID) throws ApiException {
    GetArchivedToolRequest request = new GetArchivedToolRequest();
    request.setId(toolID);
    request.setWorkspaceID(workspaceID);
      return api.getArchivedTool(request);
  }
}
