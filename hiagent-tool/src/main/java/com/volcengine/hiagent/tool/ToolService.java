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
package com.volcengine.hiagent.tool;

import com.volcengine.ApiException;
import com.volcengine.hiagent.api.ApiClient;
import com.volcengine.hiagent.api.ToolClient;
import com.volcengine.hiagent.api.model.ExecArchivedToolRequest;
import com.volcengine.hiagent.api.model.ExecArchivedToolResponse;
import com.volcengine.hiagent.api.model.GetArchivedToolRequest;
import com.volcengine.hiagent.api.model.GetArchivedToolResponse;

import java.util.logging.Logger;

public class ToolService {
    private static final Logger logger = Logger.getLogger(ToolService.class.getName());

    private final ToolClient toolClient;

    public ToolService(String endpoint, String ak, String sk) {
        ApiClient apiClient = new ApiClient(endpoint, ak, sk, "cn-north-1");

        this.toolClient = new ToolClient(apiClient);
    }


    /**
     * Get Archived Tool
     *
     * @param request GetArchivedToolRequest (required)
     * @return GetArchivedToolResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     */
    public GetArchivedToolResponse getArchivedTool(GetArchivedToolRequest request) throws ApiException {
        return this.toolClient.getArchivedTool(request);
    }


    /**
     * Exec Archived Tool
     *
     * @param request ExecArchivedToolRequest (required)
     * @return ExecArchivedToolResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot
     *                      deserialize the response body
     */
    public ExecArchivedToolResponse execArchivedTool(ExecArchivedToolRequest request) throws ApiException {
        return this.toolClient.execArchivedTool(request);
    }

}
