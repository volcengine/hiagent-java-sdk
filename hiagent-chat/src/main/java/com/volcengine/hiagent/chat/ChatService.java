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
package com.volcengine.hiagent.chat;

import com.volcengine.hiagent.api.BaseApiClient;
import com.volcengine.hiagent.api.ChatClient;
import com.volcengine.hiagent.api.model.*;
import com.volcengine.hiagent.api.model.base.ChatEvent;
import com.volcengine.hiagent.api.model.base.WorkflowEvent;

import java.io.IOException;
import java.util.logging.Logger;

public class ChatService {
    private static final Logger logger = Logger.getLogger(ChatService.class.getName());

    private final ChatClient chatClient;

    public ChatService(String baseUrl, String apiKey) {
        this.chatClient = new ChatClient(baseUrl, apiKey);
    }


    /**
     * 创建会话
     *
     * @param request 请求体
     * @return 响应体
     * @throws IOException                IO异常
     * @throws InterruptedException       线程中断异常
     * @throws BaseApiClient.ApiException API调用异常
     */
    public CreateConversationResponse createConversation(CreateConversationRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.createConversation(request);
    }

    public GetAppConfigPreviewResponse getApp(GetAppConfigPreviewRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.getApp(request);
    }

    public BlockingChatResponse chatBlocking(ChatRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.chatBlocking(request);
    }

    public Iterable<ChatEvent> chatStreaming(ChatRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.chatStreaming(request);
    }

    public Iterable<ChatEvent> chatAgain(ChatAgainRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.chatAgain(request);
    }

    public GetConversationListResponse getConversationList(GetConversationListRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.getConversationList(request);
    }

    public GetConversationInputsResponse getConversationInputs(GetConversationInputsRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.getConversationInputs(request);
    }

    public EmptyResponse updateConversation(UpdateConversationRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.updateConversation(request);
    }

    public EmptyResponse deleteConversation(DeleteConversationRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.deleteConversation(request);
    }

    public EmptyResponse stopMessage(StopMessageRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.stopMessage(request);
    }

    public EmptyResponse clearMessage(ClearMessageRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.clearMessage(request);
    }

    public GetConversationMessageResponse getConversationMessages(GetConversationMessageRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.getConversationMessages(request);
    }

    public GetMessageInfoResponse getMessageInfo(GetMessageInfoRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.getMessageInfo(request);
    }

    public EmptyResponse deleteMessage(DeleteMessageRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.deleteMessage(request);
    }

    public EmptyResponse feedback(FeedbackRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.feedback(request);
    }

    public EmptyResponse setMessageAnswerUsed(SetMessageAnswerUsedRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.setMessageAnswerUsed(request);
    }

    public GetSuggestedQuestionsResponse getSuggestedQuestions(GetSuggestedQuestionsRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.getSuggestedQuestions(request);
    }

    public RunAppWorkflowResponse runAppWorkflow(RunAppWorkflowRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.runAppWorkflow(request);
    }

    public SyncRunAppWorkflowResponse syncRunAppWorkflow(SyncRunAppWorkflowRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.syncRunAppWorkflow(request);
    }

    public SyncRunAppWorkflowResponse queryRunAppProcess(QueryRunAppProcessRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.queryRunAppProcess(request);
    }

    public ListOauth2TokenResponse listOauth2Token(ListOauth2TokenRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.listOauth2Token(request);
    }

    public EventTriggerWebhookResponse eventTriggerWebhook(String webhookKey, String webhookToken)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.eventTriggerWebhook(webhookKey, webhookToken);
    }

    public Iterable<ChatEvent> chatContinue(ChatContinueRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.chatContinue(request);
    }

    public ListLongMemoryResponse listLongMemory(ListLongMemoryRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.listLongMemory(request);
    }

    public EmptyResponse updateLongMemory(UpdateLongMemoryRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.updateLongMemory(request);
    }

    public EmptyResponse deleteLongMemory(DeleteLongMemoryRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.deleteLongMemory(request);
    }

    public EmptyResponse clearLongMemory(ClearLongMemoryRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.clearLongMemory(request);
    }

    public SyncRunAppWorkflowResponse asyncResumeAppWorkflow(AsyncResumeAppWorkflowRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.asyncResumeAppWorkflow(request);
    }

    public EmptyResponse setConversationTop(SetConversationTopRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.setConversationTop(request);
    }

    public EmptyResponse cancelConversationTop(CancelConversationTopRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.cancelConversationTop(request);
    }

    public QueryAppSkillAsyncTaskResponse querySkillAsyncTask(QueryAppSkillAsyncTaskRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.querySkillAsyncTask(request);
    }

    public SyncRunAppWorkflowResponse syncResumeAppWorkflowBlocking(SyncResumeAppWorkflowRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.syncResumeAppWorkflowBlocking(request);
    }

    public Iterable<WorkflowEvent> syncResumeAppWorkflowStreaming(SyncResumeAppWorkflowRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.syncResumeAppWorkflowStreaming(request);
    }

    public GetAppUserVariablesResponse getAppUserVariables(GetAppUserVariablesRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.getAppUserVariables(request);
    }

    public EmptyResponse setAppUserVariables(SetAppUserVariablesRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.setAppUserVariables(request);
    }

    public QueryTriggerRunRecordsResponse queryTriggerRunRecords(QueryTriggerRunRecordsRequest request)
            throws IOException, InterruptedException, BaseApiClient.ApiException {
        return this.chatClient.queryTriggerRunRecords(request);
    }

}
