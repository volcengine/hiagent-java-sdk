package com.volcengine.hiagent.components;

import com.volcengine.hiagent.api.ApiClient;

public class ComponentLoader {
    public void load() {
        ApiClient client = new ApiClient();
        System.out.println("Loading components with " + client.getVersion());
    }
}
