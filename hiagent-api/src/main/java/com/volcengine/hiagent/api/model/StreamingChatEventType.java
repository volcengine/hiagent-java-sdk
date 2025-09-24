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
package com.volcengine.hiagent.api.model;

public enum StreamingChatEventType {
    /**
     * chat event type enum
     */
    MessageStart("message_start"),
    AgentJump("agent_jump"),
    AgentTakeOver("agent_take_over"),
    QaRetrieve("qa_retrieve"),
    QaRetrieveEnd("qa_retrieve_end"),
    TerminologyRetrieve("terminology_retrieve"),
    TerminologyRetrieveEnd("terminology_retrieve_end"),
    LongTermMemoryRetrieve("long_term_memory_retrieve"),
    LongTermMemoryRetrieve_end("long_term_memory_retrieve_end"),
    KnowledgeRetrieve("knowledge_retrieve"),
    KnowledgeRetrieveEnd("knowledge_retrieve_end"),
    KnowledgeGraphRetrieve("knowledge_graph_retrieve"),
    KnowledgeGraphRetrieveEnd("knowledge_graph_retrieve_end"),
    AgentThought("agent_thought"),
    AgentThoughtEnd("agent_thought_end"),
    AgentThoughtUpdate("agent_thought_update"),
    ToolMessageOutputStart("tool_message_output_start"),
    ToolMessage("tool_message"),
    ToolMessageOutputEnd("tool_message_output_end"),
    Interrupted("interrupted"),
    AgentIntention("agent_intention"),
    MessageOutputStart("message_output_start"),
    Message("message"),
    MessageOutputEnd("message_output_end"),
    MessageCost("message_cost"),
    Suggestion("suggestion"),
    SuggestionCost("suggestion_cost"),
    MessageEnd("message_end"),
    MessageFailed("message_failed"),
    ThinkMessageOutputStart("think_message_output_start"),
    ThinkMessage("think_message"),
    ThinkMessageOutputEnd("think_message_output_end");

    private final String value;

    StreamingChatEventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static StreamingChatEventType fromValue(String value) {
        for (StreamingChatEventType streamingChatEventType : StreamingChatEventType.values()) {
            if (streamingChatEventType.getValue().equals(value)) {
                return streamingChatEventType;
            }
        }
        return null;
    }
}
