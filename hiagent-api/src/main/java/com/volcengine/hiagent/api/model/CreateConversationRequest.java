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

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class CreateConversationRequest {
    @SerializedName("UserID")
    private String userID = null;

    @SerializedName("Inputs")
    private Map<String, String> inputs = null;

    public CreateConversationRequest() {
    }

    public CreateConversationRequest(String userID) {
        this.userID = userID;
    }

    public CreateConversationRequest(String userID, Map<String, String> inputs) {
        this.userID = userID;
        this.inputs = inputs;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Map<String, String> getInputs() {
        return inputs;
    }

    public void setInputs(Map<String, String> inputs) {
        this.inputs = inputs;
    }
}
