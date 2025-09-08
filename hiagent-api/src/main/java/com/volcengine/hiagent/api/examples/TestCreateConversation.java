package com.volcengine.hiagent.api.examples;

import com.volcengine.hiagent.api.ChatClient;
import com.volcengine.hiagent.api.model.CreateConversationRequest;
import com.volcengine.hiagent.api.model.CreateConversationResponse;


public class TestCreateConversation {
    public static void main(String[] args) {
        String baseUrl = System.getenv("HIAGENT_BASE_URL");
        String apiKey = System.getenv("HIAGENT_API_KEY");
        ChatClient client = new ChatClient(baseUrl, apiKey);
        CreateConversationRequest request = new CreateConversationRequest();
        request.setUserID("user123");
        try {
            CreateConversationResponse response = client.createConversation(request);
            System.out.println("创建会话成功: " + response.getConversation().getAppConversationID());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
