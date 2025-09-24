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

import com.volcengine.ApiClient;
import com.volcengine.ApiException;
import com.volcengine.ApiResponse;
import com.volcengine.Pair;
import com.volcengine.hiagent.api.EvaClient;
import com.volcengine.hiagent.api.ToolClient;
import com.volcengine.hiagent.api.model.*;
import com.volcengine.hiagent.api.model.base.*;
import com.volcengine.sign.Credentials;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class ToolService {
    private static final Logger logger = Logger.getLogger(ToolService.class.getName());

    private final ToolClient toolClient;

    public ToolService(String endpoint, String ak, String sk) {
        ApiClient apiClient = new ApiClient()
                .setCredentials(Credentials.getCredentials(ak, sk))
                .setRegion("cn-north-1")
                .setEndpoint(endpoint)
                .setDisableSSL(true);

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
