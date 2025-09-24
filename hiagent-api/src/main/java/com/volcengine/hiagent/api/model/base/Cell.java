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
package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Cell {
    @Nullable
    private  List<CellContentPart> CellContent;
    @Nullable
    private List<Message> Messages;
    @Nullable
    private  List<MessageWithMultiResponse> MessagesWithMultiResponse;
    @Nullable
    private  String Text;
    @Nullable
    private  String Json;
    private  String Type;

    public Cell() {
    }

    public Cell(@Nullable List<CellContentPart> cellContent, @Nullable List<Message> messages, @Nullable List<MessageWithMultiResponse> messagesWithMultiResponse, @Nullable String text, @Nullable String json, String type) {
        CellContent = cellContent;
        Messages = messages;
        MessagesWithMultiResponse = messagesWithMultiResponse;
        Text = text;
        Json = json;
        Type = type;
    }

    @Nullable
    public List<CellContentPart> getCellContent() {
        return CellContent;
    }

    public void setCellContent(@Nullable List<CellContentPart> cellContent) {
        CellContent = cellContent;
    }

    @Nullable
    public List<Message> getMessages() {
        return Messages;
    }

    public void setMessages(@Nullable List<Message> messages) {
        Messages = messages;
    }

    @Nullable
    public List<MessageWithMultiResponse> getMessagesWithMultiResponse() {
        return MessagesWithMultiResponse;
    }

    public void setMessagesWithMultiResponse(@Nullable List<MessageWithMultiResponse> messagesWithMultiResponse) {
        MessagesWithMultiResponse = messagesWithMultiResponse;
    }

    @Nullable
    public String getText() {
        return Text;
    }

    public void setText(@Nullable String text) {
        Text = text;
    }

    @Nullable
    public String getJson() {
        return Json;
    }

    public void setJson(@Nullable String json) {
        Json = json;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
