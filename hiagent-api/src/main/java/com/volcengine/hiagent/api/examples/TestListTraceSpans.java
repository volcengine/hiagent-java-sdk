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
import com.volcengine.hiagent.api.model.*;
import com.volcengine.sign.Credentials;

public class TestListTraceSpans {
  public static void main(String[] args) {
    String ak = System.getenv("VOLC_ACCESSKEY");
    String sk = System.getenv("VOLC_SECRETKEY");
    String region = System.getenv("HIAGENT_TOP_REGION");
    String endpoint = System.getenv("HIAGENT_TOP_ENDPOINT");

    String workspaceID = System.getenv("WORKSPACE_ID");

    ApiClient apiClient = new ApiClient()
        .setCredentials(Credentials.getCredentials(ak, sk))
        .setRegion(region).setEndpoint(endpoint).setDisableSSL(true);

    ObserveClient api = new ObserveClient(apiClient);
    ListTraceSpansRequest createApiTokenReq = new ListTraceSpansRequest();
    createApiTokenReq.setWorkspaceID(workspaceID);

    try {
      ListTraceSpansResponse response = api.listTraceSpans(createApiTokenReq);
      System.out.println(response);
    } catch (ApiException e) {
      System.err.println("API调用发生异常:");
      System.err.println("错误码: " + e.getCode());
      System.err.println("错误信息: " + e.getMessage());
      System.err.println("响应体: " + e.getResponseBody());
      System.err.println("完整堆栈跟踪:");
      e.printStackTrace(); // 这会打印完整的堆栈跟踪
    }
  }
}
