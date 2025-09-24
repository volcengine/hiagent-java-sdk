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

public class ListOauth2TokenResponse extends AbstractResponse {

    @SerializedName("Total")
    private Integer total;

    @SerializedName("Items")
    private List<Oauth2TokenItem> items;

    public static class Oauth2TokenItem {

        @SerializedName("PluginID")
        private String pluginID;

        @SerializedName("APPID")
        private String appID;

        @SerializedName("APPUserID")
        private String appUserID;

        @SerializedName("TokenExpiresAt")
        private String tokenExpiresAt;

        @SerializedName("IsTokenValid")
        private Boolean isTokenValid;

        @SerializedName("IsRefreshTokenValid")
        private Boolean isRefreshTokenValid;

        public String getPluginID() {
            return pluginID;
        }

        public void setPluginID(String pluginID) {
            this.pluginID = pluginID;
        }

        public String getAppID() {
            return appID;
        }

        public void setAppID(String appID) {
            this.appID = appID;
        }

        public String getAppUserID() {
            return appUserID;
        }

        public void setAppUserID(String appUserID) {
            this.appUserID = appUserID;
        }

        public String getTokenExpiresAt() {
            return tokenExpiresAt;
        }

        public void setTokenExpiresAt(String tokenExpiresAt) {
            this.tokenExpiresAt = tokenExpiresAt;
        }

        public Boolean getTokenValid() {
            return isTokenValid;
        }

        public void setTokenValid(Boolean tokenValid) {
            isTokenValid = tokenValid;
        }

        public Boolean getRefreshTokenValid() {
            return isRefreshTokenValid;
        }

        public void setRefreshTokenValid(Boolean refreshTokenValid) {
            isRefreshTokenValid = refreshTokenValid;
        }
    }
}
