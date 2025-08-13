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
package com.volcengine.hiagent.examples;

import com.volcengine.hiagent.api.ApiClient;
import com.volcengine.hiagent.components.ComponentLoader;
import com.volcengine.hiagent.eva.EvaService;
import com.volcengine.hiagent.observe.Observer;

public class ExampleMain {
    public static void main(String[] args) {
        System.out.println("=== HiAgent Java SDK Example ===");

        // 调用 API 模块
        ApiClient api = new ApiClient();
        api.getVersion();

        // 调用 Components 模块
        ComponentLoader component = new ComponentLoader();
        component.load();

        // 调用 EVA 模块
        EvaService eva = new EvaService();
        eva.run();

        // 调用 Observe 模块
        Observer observe = new Observer();
        observe.observe();

        System.out.println("=== Example Finished ===");
    }
}
