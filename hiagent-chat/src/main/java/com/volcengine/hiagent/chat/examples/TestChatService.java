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
package com.volcengine.hiagent.chat.examples;

import com.google.gson.Gson;
import com.volcengine.hiagent.api.model.BlockingChatResponse;
import com.volcengine.hiagent.api.model.ChatRequest;
import com.volcengine.hiagent.api.model.CreateConversationRequest;
import com.volcengine.hiagent.chat.ChatService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestChatService {
    private static final Logger logger = Logger.getLogger(TestChatService.class.getName());

    public static void main(String[] args) {
        try {
            // 从环境变量获取配置信息
            String baseUrl = System.getenv("HIAGENT_APP_BASE_URL");
            String apiKey = System.getenv("HIAGENT_AGENT_APP_KEY");
            String userID = "user123";

            // ChatService
            ChatService chatService = new ChatService(baseUrl, apiKey);

            // 创建会话
            CreateConversationRequest createConversationRequest = new CreateConversationRequest();
            createConversationRequest.setUserID(userID);
            String conversationID = chatService.createConversation(createConversationRequest).getConversation().getAppConversationID();

            // 对话
            logger.info("开始对话...");
            ChatRequest chatRequest = new ChatRequest();
            chatRequest.setAppConversationID(conversationID);
            chatRequest.setUserID(userID);
            chatRequest.setQuery("你好");
            BlockingChatResponse blockingChatResponse = chatService.chatBlocking(chatRequest);
            // 对话结果
            logger.info("对话结果: " + blockingChatResponse.getAnswer());

            // 流式对话
            logger.info("开始流式对话...");
            Gson gson = new Gson();
            chatService.chatStreaming(chatRequest).forEach((chatEvent) -> {
                gson.toJson(chatEvent);
                logger.info("收到事件：" + gson.toJson(chatEvent));
            });
            // 流式对话结果
            logger.info("流式对话结束");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "测试会话异常: " + e.getMessage(), e);
        }
    }
}