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

import com.google.gson.Gson;
import com.volcengine.hiagent.api.model.*;
import com.volcengine.hiagent.api.model.base.ChatEvent;
import com.volcengine.hiagent.api.model.base.WorkflowEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ChatClient类，用于与Chat API进行交互
 */
public class ChatClient extends BaseApiClient {
    public ChatClient(String baseUrl, String apiKey) {
        super(baseUrl, apiKey);
    }

    /**
     * 创建会话
     *
     * @param request 请求体
     * @return 响应体
     * @throws IOException          IO异常
     * @throws InterruptedException 线程中断异常
     * @throws ApiException         API调用异常
     */
    public CreateConversationResponse createConversation(CreateConversationRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("create_conversation", request, CreateConversationResponse.class);
    }

    public GetAppConfigPreviewResponse getApp(GetAppConfigPreviewRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("get_app", request, GetAppConfigPreviewResponse.class);
    }

    public BlockingChatResponse chatBlocking(ChatRequest request)
            throws IOException, InterruptedException, ApiException {
        request.setResponseMode("blocking");
        return post("chat_query_v2", request, BlockingChatResponse.class);
    }

    public Iterable<ChatEvent> chatStreaming(ChatRequest request)
            throws IOException, InterruptedException, ApiException {
        request.setResponseMode("streaming");
        return postStream("chat_query_v2", request, this::parseChatEvent);
    }

    public Iterable<ChatEvent> chatAgain(ChatAgainRequest request)
            throws IOException, InterruptedException, ApiException {
        return postStream("query_again_v2", request, this::parseChatEvent);
    }

    public GetConversationListResponse getConversationList(GetConversationListRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("get_conversation_list", request, GetConversationListResponse.class);
    }

    public GetConversationInputsResponse getConversationInputs(GetConversationInputsRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("get_conversation_inputs", request, GetConversationInputsResponse.class);
    }

    public EmptyResponse updateConversation(UpdateConversationRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("update_conversation", request, EmptyResponse.class);
    }

    public EmptyResponse deleteConversation(DeleteConversationRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("delete_conversation", request, EmptyResponse.class);
    }

    public EmptyResponse stopMessage(StopMessageRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("stop_message", request, EmptyResponse.class);
    }

    public EmptyResponse clearMessage(ClearMessageRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("clear_message", request, EmptyResponse.class);
    }

    public GetConversationMessageResponse getConversationMessages(GetConversationMessageRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("get_conversation_messages", request, GetConversationMessageResponse.class);
    }

    public GetMessageInfoResponse getMessageInfo(GetMessageInfoRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("get_message_info", request, GetMessageInfoResponse.class);
    }

    public EmptyResponse deleteMessage(DeleteMessageRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("delete_message", request, EmptyResponse.class);
    }

    public EmptyResponse feedback(FeedbackRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("feedback", request, EmptyResponse.class);
    }

    public EmptyResponse setMessageAnswerUsed(SetMessageAnswerUsedRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("set_message_answer_used", request, EmptyResponse.class);
    }

    public GetSuggestedQuestionsResponse getSuggestedQuestions(GetSuggestedQuestionsRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("get_suggested_questions", request, GetSuggestedQuestionsResponse.class);
    }

    public RunAppWorkflowResponse runAppWorkflow(RunAppWorkflowRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("run_app_workflow", request, RunAppWorkflowResponse.class);
    }

    public SyncRunAppWorkflowResponse syncRunAppWorkflow(SyncRunAppWorkflowRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("sync_run_app_workflow", request, SyncRunAppWorkflowResponse.class);
    }

    public SyncRunAppWorkflowResponse queryRunAppProcess(QueryRunAppProcessRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("query_run_app_process", request, SyncRunAppWorkflowResponse.class);
    }

    public ListOauth2TokenResponse listOauth2Token(ListOauth2TokenRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("list_oauth2_token", request, ListOauth2TokenResponse.class);
    }

    public EventTriggerWebhookResponse eventTriggerWebhook(String webhookKey, String webhookToken)
            throws IOException, InterruptedException, ApiException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + webhookToken);
        return post(
                String.format("trigger/webhook?key=%s", webhookKey),
                new Object(),
                EventTriggerWebhookResponse.class,
                headers
        );
    }

    public Iterable<ChatEvent> chatContinue(ChatContinueRequest request)
            throws IOException, InterruptedException, ApiException {
        return postStream("chat_continue", request, this::parseChatEvent);
    }

    public ListLongMemoryResponse listLongMemory(ListLongMemoryRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("list_long_memory", request, ListLongMemoryResponse.class);
    }

    public EmptyResponse updateLongMemory(UpdateLongMemoryRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("update_long_memory", request, EmptyResponse.class);
    }

    public EmptyResponse deleteLongMemory(DeleteLongMemoryRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("delete_long_memory", request, EmptyResponse.class);
    }

    public EmptyResponse clearLongMemory(ClearLongMemoryRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("clear_long_memory", request, EmptyResponse.class);
    }

    public SyncRunAppWorkflowResponse asyncResumeAppWorkflow(AsyncResumeAppWorkflowRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("async_resume_app_workflow", request, SyncRunAppWorkflowResponse.class);
    }

    public EmptyResponse setConversationTop(SetConversationTopRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("set_conversation_top", request, EmptyResponse.class);
    }

    public EmptyResponse cancelConversationTop(CancelConversationTopRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("cancel_conversation_top", request, EmptyResponse.class);
    }

    public QueryAppSkillAsyncTaskResponse querySkillAsyncTask(QueryAppSkillAsyncTaskRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("query_skill_async_task", request, QueryAppSkillAsyncTaskResponse.class);
    }

    public SyncRunAppWorkflowResponse syncResumeAppWorkflowBlocking(SyncResumeAppWorkflowRequest request)
            throws IOException, InterruptedException, ApiException {
        request.setStream(false);
        return post("sync_resume_app_workflow_blocking", request, SyncRunAppWorkflowResponse.class);
    }

    public Iterable<WorkflowEvent> syncResumeAppWorkflowStreaming(SyncResumeAppWorkflowRequest request)
            throws IOException, InterruptedException, ApiException {
        request.setStream(true);
        return postStream("sync_resume_app_workflow_streaming", request, this::parseWorkflowEvent);
    }

    public GetAppUserVariablesResponse getAppUserVariables(GetAppUserVariablesRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("get_app_user_variables", request, GetAppUserVariablesResponse.class);
    }

    public EmptyResponse setAppUserVariables(SetAppUserVariablesRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("set_app_user_variables", request, EmptyResponse.class);
    }

    public QueryTriggerRunRecordsResponse queryTriggerRunRecords(QueryTriggerRunRecordsRequest request)
            throws IOException, InterruptedException, ApiException {
        return post("query_trigger_run_records", request, QueryTriggerRunRecordsResponse.class);
    }


    public ChatEvent parseChatEvent(String eventData) {
        Gson gson = new Gson();
        ChatEvent chatEvent = gson.fromJson(eventData, ChatEvent.class);
        StreamingChatEventType eventType = StreamingChatEventType.fromValue(chatEvent.getEvent());
        if (eventType == null) {
            return chatEvent;
        }
        switch (eventType) {
            case MessageStart:
                return gson.fromJson(eventData, MessageStartChatEvent.class);
            case MessageEnd:
                return gson.fromJson(eventData, MessageEndChatEvent.class);
            case MessageFailed:
                return gson.fromJson(eventData, MessageFailedChatEvent.class);
            case MessageOutputStart:
                return gson.fromJson(eventData, MessageOutputStartChatEvent.class);
            case MessageOutputEnd:
                return gson.fromJson(eventData, MessageOutputEndChatEvent.class);
            case Message:
                return gson.fromJson(eventData, MessageChatEvent.class);
            case MessageCost:
                return gson.fromJson(eventData, MessageCostChatEvent.class);
            case AgentJump:
                return gson.fromJson(eventData, AgentJumpChatEvent.class);
            case AgentTakeOver:
                return gson.fromJson(eventData, AgentTakeOverChatEvent.class);
            case QaRetrieve:
                return gson.fromJson(eventData, QARetrieveChatEvent.class);
            case QaRetrieveEnd:
                return gson.fromJson(eventData, QARetrieveEndChatEvent.class);
            case TerminologyRetrieve:
                return gson.fromJson(eventData, TerminologyRetrieveChatEvent.class);
            case TerminologyRetrieveEnd:
                return gson.fromJson(eventData, TerminologyRetrieveEndChatEvent.class);
            case KnowledgeRetrieve:
                return gson.fromJson(eventData, KnowledgeRetrieveChatEvent.class);
            case KnowledgeRetrieveEnd:
                return gson.fromJson(eventData, KnowledgeRetrieveEndChatEvent.class);
            case KnowledgeGraphRetrieve:
                return gson.fromJson(eventData, KnowledgeGraphRetrieveChatEvent.class);
            case KnowledgeGraphRetrieveEnd:
                return gson.fromJson(eventData, KnowledgeGraphRetrieveEndChatEvent.class);
            case AgentThought:
                return gson.fromJson(eventData, AgentThoughtChatEvent.class);
            case AgentThoughtEnd:
                return gson.fromJson(eventData, AgentThoughtEndChatEvent.class);
            case AgentThoughtUpdate:
                return gson.fromJson(eventData, AgentThoughtUpdateChatEvent.class);
            case ToolMessageOutputStart:
                return gson.fromJson(eventData, ToolMessageOutputStartChatEvent.class);
            case ToolMessage:
                return gson.fromJson(eventData, ToolMessageChatEvent.class);
            case ToolMessageOutputEnd:
                return gson.fromJson(eventData, ToolMessageOutputEndChatEvent.class);
            case Interrupted:
                return gson.fromJson(eventData, InterruptedChatEvent.class);
            case AgentIntention:
                return gson.fromJson(eventData, AgentIntentionChatEvent.class);
            case Suggestion:
                return gson.fromJson(eventData, SuggestionChatEvent.class);
            case SuggestionCost:
                return gson.fromJson(eventData, SuggestionCostChatEvent.class);
            case ThinkMessageOutputStart:
                return gson.fromJson(eventData, ThinkMessageOutputStartChatEvent.class);
            case ThinkMessage:
                return gson.fromJson(eventData, ThinkMessageChatEvent.class);
            case ThinkMessageOutputEnd:
                return gson.fromJson(eventData, ThinkMessageOutputEndChatEvent.class);
            default:
                return null;
        }
    }

    public WorkflowEvent parseWorkflowEvent(String eventData) {
        Gson gson = new Gson();
        WorkflowEvent workflowEvent = gson.fromJson(eventData, WorkflowEvent.class);
        if (workflowEvent == null) {
            return null;
        }
        StreamingWorkflowEventType eventType = StreamingWorkflowEventType.fromValue(workflowEvent.getEvent());
        if (eventType == null) {
            return workflowEvent;
        }
        switch (eventType) {
            case FlowStart:
                return gson.fromJson(eventData, FlowStartWorkflowEvent.class);
            case FlowInterrupted:
                return gson.fromJson(eventData, FlowInterruptedWorkflowEvent.class);
            case FlowEnd:
                return gson.fromJson(eventData, FlowEndWorkflowEvent.class);
            case FlowCost:
                return gson.fromJson(eventData, FlowCostWorkflowEvent.class);
            case FlowError:
                return gson.fromJson(eventData, FlowErrorWorkflowEvent.class);
            case ToolMessageOutputStart:
                return gson.fromJson(eventData, ToolMessageOutputStartWorkflowEvent.class);
            case ToolMessage:
                return gson.fromJson(eventData, ToolMessageWorkflowEvent.class);
            case ToolMessageOutputEnd:
                return gson.fromJson(eventData, ToolMessageOutputEndWorkflowEvent.class);
            case MessageOutputStart:
                return gson.fromJson(eventData, MessageOutputStartWorkflowEvent.class);
            case MessageOutputEnd:
                return gson.fromJson(eventData, MessageOutputEndWorkflowEvent.class);
            case Message:
                return gson.fromJson(eventData, MessageWorkflowEvent.class);
            default:
                return null;
        }
    }

}
