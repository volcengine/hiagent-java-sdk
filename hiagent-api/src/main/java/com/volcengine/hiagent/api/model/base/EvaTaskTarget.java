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

public class EvaTaskTarget {
    private EvaTargetType Type;
    private String TargetID;
    private String TargetName;
    @Nullable
    public String TargetIcon;
    private EvaTargetConfig TargetConfig;
    private long QPS;
    @Nullable
    private EvaTaskTargetParams Params;

    public EvaTaskTarget() {
    }

    public EvaTaskTarget(EvaTargetType type, String targetID, String targetName, @Nullable String targetIcon, EvaTargetConfig targetConfig, long QPS, @Nullable EvaTaskTargetParams params) {
        Type = type;
        TargetID = targetID;
        TargetName = targetName;
        TargetIcon = targetIcon;
        TargetConfig = targetConfig;
        this.QPS = QPS;
        Params = params;
    }

    public EvaTargetType getType() {
        return Type;
    }

    public void setType(EvaTargetType type) {
        Type = type;
    }

    public String getTargetID() {
        return TargetID;
    }

    public void setTargetID(String targetID) {
        TargetID = targetID;
    }

    public String getTargetName() {
        return TargetName;
    }

    public void setTargetName(String targetName) {
        TargetName = targetName;
    }

    @Nullable
    public String getTargetIcon() {
        return TargetIcon;
    }

    public void setTargetIcon(@Nullable String targetIcon) {
        TargetIcon = targetIcon;
    }

    public EvaTargetConfig getTargetConfig() {
        return TargetConfig;
    }

    public void setTargetConfig(EvaTargetConfig targetConfig) {
        TargetConfig = targetConfig;
    }

    public long getQPS() {
        return QPS;
    }

    public void setQPS(long QPS) {
        this.QPS = QPS;
    }

    @Nullable
    public EvaTaskTargetParams getParams() {
        return Params;
    }

    public void setParams(@Nullable EvaTaskTargetParams params) {
        Params = params;
    }
}
