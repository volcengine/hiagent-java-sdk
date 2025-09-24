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

public class EvaExecParam{
    @Nullable
    private String Name; 
    private boolean Required; 
    private boolean Builtin; 
    @Nullable
    private String Source; 
    @Nullable
    private String Field; 
    @Nullable
    private String JsonPath;

    public EvaExecParam() {
    }

    public EvaExecParam(@Nullable String name, boolean required, boolean builtin, @Nullable String source, @Nullable String field, @Nullable String jsonPath) {
        Name = name;
        Required = required;
        Builtin = builtin;
        Source = source;
        Field = field;
        JsonPath = jsonPath;
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
}
