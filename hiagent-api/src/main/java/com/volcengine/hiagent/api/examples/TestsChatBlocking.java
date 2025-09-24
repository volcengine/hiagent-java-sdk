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

import com.volcengine.hiagent.api.BaseApiClient;
import com.volcengine.hiagent.api.ChatClient;
import com.volcengine.hiagent.api.model.BlockingChatResponse;
import com.volcengine.hiagent.api.model.ChatRequest;
import com.volcengine.hiagent.api.model.CreateConversationRequest;
import com.volcengine.hiagent.api.model.CreateConversationResponse;

import java.io.IOException;


public class TestsChatBlocking {
    public static void main(String[] args) {
        String baseUrl = System.getenv("HIAGENT_APP_BASE_URL");
        String apiKey = System.getenv("HIAGENT_AGENT_APP_KEY");
        ChatClient client = new ChatClient(baseUrl, apiKey);
        try {
            String conversationID = createConversation(client);
            ChatRequest chatRequest = new ChatRequest();
            chatRequest.setAppConversationID(conversationID);
            chatRequest.setQuery("你好");
            chatRequest.setUserID("user123");
            BlockingChatResponse blockingChatResponse = client.chatBlocking(chatRequest);
            System.out.println("对话成功：" + blockingChatResponse.getAnswer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String createConversation(ChatClient client) throws IOException, InterruptedException, BaseApiClient.ApiException {
        CreateConversationRequest request = new CreateConversationRequest();
        request.setUserID("user123");
        CreateConversationResponse response = client.createConversation(request);
        String conversationID = response.getConversation().getAppConversationID();
        System.out.println("创建会话成功: " + conversationID);
        return conversationID;
    }
}
