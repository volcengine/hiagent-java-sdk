package com.volcengine.hiagent.api.examples;

import com.volcengine.ApiClient;
import com.volcengine.ApiException;
import com.volcengine.hiagent.api.ObserveClient;
import com.volcengine.hiagent.api.model.*;
import com.volcengine.sign.Credentials;

public class TestCreateApiToken {
  public static void main(String[] args) {
    String ak = System.getenv("VOLC_ACCESSKEY");
    String sk = System.getenv("VOLC_SECRETKEY");
    String region = System.getenv("HIAGENT_TOP_REGION");
    String endpoint = System.getenv("HIAGENT_TOP_ENDPOINT");

    String workspaceID = System.getenv("WORKSPACE_ID");
    String customAppID = System.getenv("CUSTOM_APP_ID");

    ApiClient apiClient = new ApiClient()
        .setCredentials(Credentials.getCredentials(ak, sk))
        .setRegion(region).setEndpoint(endpoint).setDisableSSL(true);

    ObserveClient api = new ObserveClient(apiClient);
    CreateApiTokenRequest createApiTokenReq = new CreateApiTokenRequest();
    createApiTokenReq.setWorkspaceID(workspaceID);
    createApiTokenReq.setCustomAppID(customAppID);

    try {
      CreateApiTokenResponse response = api.createApiToken(createApiTokenReq);
      System.out.println(response.getToken());
      System.out.println(response.getExpiresIn());
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
