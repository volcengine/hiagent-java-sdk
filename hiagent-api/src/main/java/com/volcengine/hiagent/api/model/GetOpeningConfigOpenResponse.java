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
import com.volcengine.model.AbstractResponse;

import java.util.List;

public class GetOpeningConfigOpenResponse extends AbstractResponse {

    @SerializedName("OpeningConfig")
    private OpeningConfig openingConfig;

    public OpeningConfig getOpeningConfig() {
        return openingConfig;
    }

    public void setOpeningConfig(OpeningConfig openingConfig) {
        this.openingConfig = openingConfig;
    }

    public static class OpeningConfig {

        @SerializedName("OpeningText")
        private String openingText;

        @SerializedName("OpeningQuestions")
        private List<String> openingQuestions;

        @SerializedName("OpeningEnabled")
        private Boolean openingEnabled;

        public String getOpeningText() {
            return openingText;
        }

        public void setOpeningText(String openingText) {
            this.openingText = openingText;
        }

        public List<String> getOpeningQuestions() {
            return openingQuestions;
        }

        public void setOpeningQuestions(List<String> openingQuestions) {
            this.openingQuestions = openingQuestions;
        }

        public Boolean getOpeningEnabled() {
            return openingEnabled;
        }

        public void setOpeningEnabled(Boolean openingEnabled) {
            this.openingEnabled = openingEnabled;
        }
    }
}
