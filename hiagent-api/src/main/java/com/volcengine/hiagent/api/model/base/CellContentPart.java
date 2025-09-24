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

public class CellContentPart {
    private  String Type;
    @Nullable
    private  String Text;
    @Nullable
    private  FileInfo ImageUrl;
    @Nullable
    private  FileInfo FileUrl;

    public CellContentPart() {
    }

    public CellContentPart(String type, @Nullable String text, @Nullable FileInfo imageUrl, @Nullable FileInfo fileUrl) {
        Type = type;
        Text = text;
        ImageUrl = imageUrl;
        FileUrl = fileUrl;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Nullable
    public String getText() {
        return Text;
    }

    public void setText(@Nullable String text) {
        Text = text;
    }

    @Nullable
    public FileInfo getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(@Nullable FileInfo imageUrl) {
        ImageUrl = imageUrl;
    }

    @Nullable
    public FileInfo getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(@Nullable FileInfo fileUrl) {
        FileUrl = fileUrl;
    }
}
