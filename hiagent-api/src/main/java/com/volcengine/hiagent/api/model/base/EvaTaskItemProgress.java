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

public class EvaTaskItemProgress {
    private long Total;
    private long Completed;

    public EvaTaskItemProgress() {
    }

    public EvaTaskItemProgress(long total, long completed) {
        Total = total;
        Completed = completed;
    }

    public long getTotal() {
        return Total;
    }

    public void setTotal(long total) {
        Total = total;
    }

    public long getCompleted() {
        return Completed;
    }

    public void setCompleted(long completed) {
        Completed = completed;
    }
}
