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
package com.volcengine.hiagent.api;

import com.volcengine.sign.Credentials;

/**
 * API客户端，用于与HiAgent TOP API进行交互。
 */
public class ApiClient extends com.volcengine.ApiClient {

    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";
    private volatile String productCode;

    /**
     * 构造函数，初始化API客户端。
     *
     * @param endpoint API服务的端点URL
     * @param ak       访问密钥ID
     * @param sk       访问密钥密钥
     * @param region   区域
     */
    public ApiClient(String endpoint, String ak, String sk, String region) {
        super();
        this.setCredentials(Credentials.getCredentials(ak, sk))
                .setRegion(region);
        if (endpoint.toLowerCase().startsWith(HTTP)) {
            this.setEndpoint(endpoint.substring(HTTP.length()))
                    .setDisableSSL(true);
        } else if (endpoint.toLowerCase().startsWith(HTTPS)) {
            this.setEndpoint(endpoint.substring(HTTPS.length()))
                    .setDisableSSL(false);
        } else {
            this.setEndpoint(endpoint)
                    .setDisableSSL(true);
        }
    }

    public ApiClient(String endpoint, String ak, String sk, String region, String productCode) {
        this(endpoint, ak, sk, region);
        this.productCode = ProductCode.normalize(productCode);
    }

    public String getProductCode() {
        return productCode;
    }

    public ApiClient setProductCode(String productCode) {
        this.productCode = ProductCode.normalize(productCode);
        return this;
    }

    @Override
    public com.volcengine.interceptor.InterceptorContext buildCall(
            String path, String method, java.util.List<com.volcengine.Pair> queryParams,
            java.util.List<com.volcengine.Pair> collectionFormats, Object body,
            java.util.Map<String, String> headerParams, java.util.Map<String, Object> formParams,
            String[] authNames, com.volcengine.ProgressRequestBody.ProgressRequestListener listener,
            boolean... async) throws com.volcengine.ApiException {
        if (productCode != null && headerParams != null
                && !ProductCode.containsIgnoreCase(headerParams, ProductCode.HEADER_NAME)) {
            headerParams.put(ProductCode.HEADER_NAME, productCode);
        }
        return super.buildCall(path, method, queryParams, collectionFormats, body, headerParams,
                formParams, authNames, listener, async);
    }

}
