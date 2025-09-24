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
package com.volcengine.hiagent.tool.examples;

import com.volcengine.hiagent.api.model.ExecArchivedToolRequest;
import com.volcengine.hiagent.api.model.ExecArchivedToolResponse;
import com.volcengine.hiagent.api.model.GetArchivedToolRequest;
import com.volcengine.hiagent.tool.ToolService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestToolService {
    private static final Logger logger = Logger.getLogger(TestToolService.class.getName());

    public static void main(String[] args) {
        try {
            // 从环境变量获取配置信息
            String ak = System.getenv("VOLC_ACCESSKEY");
            String sk = System.getenv("VOLC_SECRETKEY");
            String endpoint = System.getenv("HIAGENT_TOP_ENDPOINT");
            String workspaceID = System.getenv("WORKSPACE_ID");
            String toolID = System.getenv("TOOL_ID");

            // ToolService
            ToolService toolService = new ToolService(endpoint, ak, sk);

            // 查询工具
            GetArchivedToolRequest getArchivedToolRequest = new GetArchivedToolRequest();
            getArchivedToolRequest.setWorkspaceID(workspaceID);
            getArchivedToolRequest.setId(toolID);
            String pluginID = toolService.getArchivedTool(getArchivedToolRequest).getPluginID();

            // 执行工具
            logger.info("开始执行...");
            ExecArchivedToolRequest execArchivedToolRequest = new ExecArchivedToolRequest();
            execArchivedToolRequest.setPluginID(pluginID);
            execArchivedToolRequest.setToolID(toolID);
            execArchivedToolRequest.setWorkspaceID(workspaceID);
            execArchivedToolRequest.setInputData("{\"timezone\": \"Asia/Shanghai\"}");
            ExecArchivedToolResponse execArchivedToolResponse = toolService.execArchivedTool(execArchivedToolRequest);
            // 执行结果
            logger.info("执行结果: " + execArchivedToolResponse.getOutput());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "测试工具异常: " + e.getMessage(), e);
        }
    }
}