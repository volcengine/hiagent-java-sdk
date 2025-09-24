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

public class FileInfo {
    @Nullable
    private String Path;
    @Nullable
    private String Name;
    private long Size;
    @Nullable
    private String Hash;
    @Nullable
    private String Url;

    public FileInfo() {
    }

    public FileInfo(@Nullable String path, @Nullable String name, long size, @Nullable String hash, @Nullable String url) {
        Path = path;
        Name = name;
        Size = size;
        Hash = hash;
        Url = url;
    }

    @Nullable
    public String getPath() {
        return Path;
    }

    public void setPath(@Nullable String path) {
        Path = path;
    }

    @Nullable
    public String getName() {
        return Name;
    }

    public void setName(@Nullable String name) {
        Name = name;
    }

    public long getSize() {
        return Size;
    }

    public void setSize(long size) {
        Size = size;
    }

    @Nullable
    public String getHash() {
        return Hash;
    }

    public void setHash(@Nullable String hash) {
        Hash = hash;
    }

    @Nullable
    public String getUrl() {
        return Url;
    }

    public void setUrl(@Nullable String url) {
        Url = url;
    }
}
