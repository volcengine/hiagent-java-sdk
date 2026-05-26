package com.volcengine.hibot.v1.types;

import java.util.List;

public final class V1ModelProviderListParams {
    public String workspaceId;
    public String provider;
    public String type;
    public String modelName;
    public List<String> features;
    public V1PageInput page;
    public String sortBy;
    public String sortOrder;
}
