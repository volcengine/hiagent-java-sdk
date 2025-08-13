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
