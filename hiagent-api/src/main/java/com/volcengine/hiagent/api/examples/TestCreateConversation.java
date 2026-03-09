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

import com.volcengine.hiagent.api.ChatClient;
import com.volcengine.hiagent.api.model.CreateConversationRequest;
import com.volcengine.hiagent.api.model.CreateConversationResponse;
import io.github.cdimascio.dotenv.Dotenv;


public class TestCreateConversation {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();
        String baseUrl = dotenv.get("HIAGENT_APP_BASE_URL");
        String apiKey = dotenv.get("HIAGENT_AGENT_APP_KEY");
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
