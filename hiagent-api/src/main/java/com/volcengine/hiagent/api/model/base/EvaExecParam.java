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

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

public class EvaExecParam{
    @Nullable
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("Required")
    private boolean Required;
    @JsonProperty("Builtin")
    private boolean Builtin;
    @Nullable
    @JsonProperty("Source")
    private String Source;
    @Nullable
    @JsonProperty("Field")
    private String Field; 
    @Nullable
    @JsonProperty("JsonPath")
    private String JsonPath;
    @Nullable
    @JsonProperty("Type")
    private String Type;

    public EvaExecParam() {
    }

    public EvaExecParam(@Nullable String name, boolean required, boolean builtin, @Nullable String source, @Nullable String field, @Nullable String jsonPath, @Nullable String type) {
        Name = name;
        Required = required;
        Builtin = builtin;
        Source = source;
        Field = field;
        JsonPath = jsonPath;
        Type = type;
    }

    @Nullable
    public String getName() {
        return Name;
    }

    public void setName(@Nullable String name) {
        Name = name;
    }

    public boolean isRequired() {
        return Required;
    }

    public void setRequired(boolean required) {
        Required = required;
    }

    public boolean isBuiltin() {
        return Builtin;
    }

    public void setBuiltin(boolean builtin) {
        Builtin = builtin;
    }

    @Nullable
    public String getSource() {
        return Source;
    }

    public void setSource(@Nullable String source) {
        Source = source;
    }

    @Nullable
    public String getField() {
        return Field;
    }

    public void setField(@Nullable String field) {
        Field = field;
    }

    @Nullable
    public String getJsonPath() {
        return JsonPath;
    }

    public void setJsonPath(@Nullable String jsonPath) {
        JsonPath = jsonPath;
    }

    @Nullable
    public String getType() {
        return Type;
    }

    public void setType(@Nullable String type) {
        Type = type;
    }
}
