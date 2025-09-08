// License: Apache 2.0
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
package com.volcengine.hiagent.api;

import com.volcengine.hiagent.api.model.CreateConversationRequest;
import com.volcengine.hiagent.api.model.CreateConversationResponse;

import java.io.IOException;

/**
 * ChatClient类，用于与Chat API进行交互
 */
public class ChatClient extends BaseApiClient {
    public ChatClient(String baseUrl, String apiKey) {
        super(baseUrl, apiKey);
    }
    
    /**
     * 创建会话
     * @param apikey API密钥
     * @param request 请求体
     * @return 响应体
     * @throws IOException IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException API调用异常
     */
    public CreateConversationResponse createConversation(CreateConversationRequest request) 
            throws IOException, InterruptedException, ApiException {
        // 调用流式请求方法
        return post("create_conversation", request, CreateConversationResponse.class);
    }
    
    // 其他API方法...
}
