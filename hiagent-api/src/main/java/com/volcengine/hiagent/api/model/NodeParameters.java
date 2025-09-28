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

import java.util.List;

public class NodeParameters {

    @SerializedName("Name")
    private String name;

    @SerializedName("Desc")
    private String desc;

    @SerializedName("Required")
    private Boolean required;

    @SerializedName("Type")
    private String type;

    @SerializedName("SubParameters")
    private List<NodeParameters> subParameters;

    @SerializedName("Default")
    private String defaultVale;

    @SerializedName("IsSecret")
    private Boolean isSecret;

    @SerializedName("IsVisible")
    private Boolean isVisible;

    @SerializedName("FileCategory")
    private List<String> fileCategory;

    @SerializedName("DescEn")
    private String descEn;

    @SerializedName("DescZhHans")
    private String descZhHans;

    @SerializedName("DescZhHant")
    private String descZhHant;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<NodeParameters> getSubParameters() {
        return subParameters;
    }

    public void setSubParameters(List<NodeParameters> subParameters) {
        this.subParameters = subParameters;
    }

    public String getDefaultVale() {
        return defaultVale;
    }

    public void setDefaultVale(String defaultVale) {
        this.defaultVale = defaultVale;
    }

    public String getDefault() {
        return defaultVale;
    }

    public void setDefault(String defaultVale) {
        this.defaultVale = defaultVale;
    }

    public Boolean getSecret() {
        return isSecret;
    }

    public void setSecret(Boolean secret) {
        isSecret = secret;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    public List<String> getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(List<String> fileCategory) {
        this.fileCategory = fileCategory;
    }

    public String getDescEn() {
        return descEn;
    }

    public void setDescEn(String descEn) {
        this.descEn = descEn;
    }

    public String getDescZhHans() {
        return descZhHans;
    }

    public void setDescZhHans(String descZhHans) {
        this.descZhHans = descZhHans;
    }

    public String getDescZhHant() {
        return descZhHant;
    }

    public void setDescZhHant(String descZhHant) {
        this.descZhHant = descZhHant;
    }
}
