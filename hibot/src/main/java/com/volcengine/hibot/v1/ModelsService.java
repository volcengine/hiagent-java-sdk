package com.volcengine.hibot.v1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.internal.Bodies;
import com.volcengine.hibot.internal.RequestExecutor;
import com.volcengine.hibot.internal.Versions;
import com.volcengine.hibot.v1.types.V1Model;
import com.volcengine.hibot.v1.types.V1ModelDeleteParams;
import com.volcengine.hibot.v1.types.V1ModelGetParams;
import com.volcengine.hibot.v1.types.V1ModelList;
import com.volcengine.hibot.v1.types.V1ModelListParams;
import com.volcengine.hibot.v1.types.V1ModelNewParams;
import com.volcengine.hibot.v1.types.V1ModelProvider;
import com.volcengine.hibot.v1.types.V1ModelProviderCredentialSchemaParams;
import com.volcengine.hibot.v1.types.V1ModelProviderGetParams;
import com.volcengine.hibot.v1.types.V1ModelProviderList;
import com.volcengine.hibot.v1.types.V1ModelProviderListParams;
import com.volcengine.hibot.v1.types.V1ModelUpdateParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Mirrors go/hibot/v1/models.go. */
public final class ModelsService {
    private final RequestExecutor requester;
    private final HibotConfig config;

    public ModelsService(RequestExecutor requester, HibotConfig config) {
        this.requester = requester;
        this.config = config;
    }

    public V1Model get(V1ModelGetParams params) {
        if (params == null) params = new V1ModelGetParams();
        if ((params.id != null && !params.id.isEmpty()) && (params.ids == null || params.ids.isEmpty())) {
            params.ids = new ArrayList<>(Arrays.asList(params.id));
        }
        if (params.ids == null || params.ids.isEmpty()) {
            if (isEmpty(params.name) && isEmpty(params.modelName) && isEmpty(params.provider)
                    && isEmpty(params.type) && isEmpty(params.spec)) {
                throw new IllegalArgumentException(
                        "hibot: model id is required (or provide Name/ModelName/Provider/Type/Spec)");
            }
            return findByFilter(params);
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        Bodies.putIfNotEmpty(body, "ID", params.id);
        if (params.ids != null && !params.ids.isEmpty()) {
            body.put("IDs", params.ids);
        }
        Bodies.putIfNotEmpty(body, "Name", params.name);
        Bodies.putIfNotEmpty(body, "ModelName", params.modelName);
        Bodies.putIfNotEmpty(body, "Provider", params.provider);
        Bodies.putIfNotEmpty(body, "Type", params.type);
        Bodies.putIfNotEmpty(body, "Spec", params.spec);
        ModelItems result = requester.doAction(
                new RequestExecutor.Action(config.modelService(), Versions.MODEL, "GetModel", body),
                new TypeReference<ModelItems>() {});
        if (result == null || result.items == null || result.items.isEmpty()) {
            throw new IllegalStateException("hibot: model not found");
        }
        V1Model match = matchModel(result.items, params);
        if (match == null) {
            throw new IllegalStateException("hibot: model not found");
        }
        return match;
    }

    private V1Model findByFilter(V1ModelGetParams params) {
        V1ModelListParams listParams = new V1ModelListParams();
        listParams.workspaceId = params.workspaceId;
        listParams.name = params.name;
        V1ModelList list = list(listParams);
        if (list == null || list.items == null || list.items.isEmpty()) {
            throw new IllegalStateException("hibot: model not found");
        }
        V1Model match = matchModel(list.items, params);
        if (match != null) {
            return match;
        }
        throw new IllegalStateException("hibot: model not found matching filter");
    }

    private static V1Model matchModel(List<V1Model> items, V1ModelGetParams params) {
        for (V1Model m : items) {
            if (notEmpty(params.name) && !params.name.equals(m.name)) continue;
            if (notEmpty(params.modelName) && !params.modelName.equals(m.modelName)) continue;
            if (notEmpty(params.provider) && !params.provider.equals(m.provider)) continue;
            if (notEmpty(params.type) && !params.type.equals(m.type)) continue;
            if (notEmpty(params.spec) && !params.spec.equals(m.spec)) continue;
            return m;
        }
        if (isEmpty(params.name) && isEmpty(params.modelName) && isEmpty(params.provider)
                && isEmpty(params.type) && isEmpty(params.spec) && !items.isEmpty()) {
            return items.get(0);
        }
        return null;
    }

    public V1ModelList list(V1ModelListParams params) {
        Map<String, Object> body = Bodies.map();
        if (params != null) {
            Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
            if (params.page != null) body.put("Page", params.page);
            Bodies.putIfNotEmpty(body, "SortBy", params.sortBy);
            Bodies.putIfNotEmpty(body, "SortOrder", params.sortOrder);
            if (notEmpty(params.name)) {
                Map<String, Object> filter = new LinkedHashMap<>();
                filter.put("Name", params.name);
                body.put("Filter", filter);
            }
        }
        return requester.doAction(
                new RequestExecutor.Action(config.modelService(), Versions.MODEL, "ListModel", body),
                new TypeReference<V1ModelList>() {});
    }

    public V1Model create(V1ModelNewParams params) {
        if (params == null || isEmpty(params.name)) {
            throw new IllegalArgumentException("hibot: model Name is required");
        }
        if (isEmpty(params.type)) {
            throw new IllegalArgumentException("hibot: model Type is required");
        }
        Map<String, Object> body = paramsToMap(params);
        IdResult result = requester.doAction(
                new RequestExecutor.Action(config.modelService(), Versions.MODEL, "CreateModel", body),
                new TypeReference<IdResult>() {});
        if (result == null || isEmpty(result.id)) {
            throw new IllegalStateException("hibot: create model response missing ID");
        }
        V1Model out = new V1Model();
        out.id = result.id;
        out.name = params.name;
        out.type = params.type;
        out.provider = params.provider;
        out.spec = params.spec;
        out.modelName = params.modelName;
        return out;
    }

    public void update(V1ModelUpdateParams params) {
        if (params == null || isEmpty(params.id)) {
            throw new IllegalArgumentException("hibot: model id is required");
        }
        if (isEmpty(params.type)) {
            throw new IllegalArgumentException("hibot: model Type is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("ID", params.id);
        body.put("Type", params.type);
        Bodies.putIfNotEmpty(body, "Description", params.description);
        Bodies.putIfNotEmpty(body, "Provider", params.provider);
        Bodies.putIfNotEmpty(body, "Spec", params.spec);
        Bodies.putIfNotEmpty(body, "ModelName", params.modelName);
        putJson(body, "FeaturesConfig", params.featuresConfig);
        putJson(body, "Property", params.property);
        putJson(body, "CredentialSchema", params.credentialSchema);
        if (params.credential != null) body.put("Credential", params.credential);
        requester.doAction(
                new RequestExecutor.Action(config.modelService(), Versions.MODEL, "UpdateModel", body),
                null);
    }

    public void delete(V1ModelDeleteParams params) {
        if (params == null || isEmpty(params.id)) {
            throw new IllegalArgumentException("hibot: model id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("ID", params.id);
        requester.doAction(
                new RequestExecutor.Action(config.modelService(), Versions.MODEL, "DeleteModel", body),
                null);
    }

    public List<String> listProviders() {
        Map<String, Object> body = Bodies.map();
        ProvidersResult result = requester.doAction(
                new RequestExecutor.Action(config.modelService(), Versions.MODEL, "ListProvider", body),
                new TypeReference<ProvidersResult>() {});
        if (result == null || result.providers == null) return new ArrayList<>();
        return result.providers;
    }

    public V1ModelProviderList listModelProviders(V1ModelProviderListParams params) {
        Map<String, Object> body = Bodies.map();
        if (params != null) {
            Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
            if (params.page != null) body.put("Page", params.page);
            Bodies.putIfNotEmpty(body, "SortBy", params.sortBy);
            Bodies.putIfNotEmpty(body, "SortOrder", params.sortOrder);
            Map<String, Object> filter = new LinkedHashMap<>();
            if (notEmpty(params.provider)) filter.put("Provider", params.provider);
            if (notEmpty(params.type)) filter.put("Type", params.type);
            if (notEmpty(params.modelName)) filter.put("ModelName", params.modelName);
            if (params.features != null && !params.features.isEmpty()) filter.put("Features", params.features);
            if (!filter.isEmpty()) body.put("Filter", filter);
        }
        return requester.doAction(
                new RequestExecutor.Action(config.modelService(), Versions.MODEL, "ListModelProvider", body),
                new TypeReference<V1ModelProviderList>() {});
    }

    public List<V1ModelProvider> getModelProvider(V1ModelProviderGetParams params) {
        if (params == null || params.ids == null || params.ids.isEmpty()) {
            throw new IllegalArgumentException("hibot: provider IDs are required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("IDs", params.ids);
        ProviderItems result = requester.doAction(
                new RequestExecutor.Action(config.modelService(), Versions.MODEL, "GetProvider", body),
                new TypeReference<ProviderItems>() {});
        if (result == null || result.items == null) return new ArrayList<>();
        return result.items;
    }

    public Object getModelProviderCredentialSchema(V1ModelProviderCredentialSchemaParams params) {
        if (params == null || isEmpty(params.provider)) {
            throw new IllegalArgumentException("hibot: provider is required");
        }
        if (isEmpty(params.type)) {
            throw new IllegalArgumentException("hibot: model type is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("Provider", params.provider);
        body.put("Type", params.type);
        Bodies.putIfNotEmpty(body, "Spec", params.spec);
        if (params.features != null && !params.features.isEmpty()) body.put("Features", params.features);
        SchemaResult result = requester.doAction(
                new RequestExecutor.Action(config.modelService(), Versions.MODEL,
                        "GetModelProviderCredentialSchema", body),
                new TypeReference<SchemaResult>() {});
        return result == null ? null : result.credentialSchema;
    }

    private static Map<String, Object> paramsToMap(V1ModelNewParams p) {
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", p.workspaceId);
        Bodies.putIfNotEmpty(body, "ID", p.id);
        Bodies.putIfNotEmpty(body, "Name", p.name);
        Bodies.putIfNotEmpty(body, "Description", p.description);
        Bodies.putIfNotEmpty(body, "Type", p.type);
        Bodies.putIfNotEmpty(body, "Provider", p.provider);
        Bodies.putIfNotEmpty(body, "Spec", p.spec);
        Bodies.putIfNotEmpty(body, "ModelName", p.modelName);
        putJson(body, "FeaturesConfig", p.featuresConfig);
        putJson(body, "Property", p.property);
        putJson(body, "CredentialSchema", p.credentialSchema);
        if (p.credential != null) body.put("Credential", p.credential);
        return body;
    }

    private static void putJson(Map<String, Object> body, String key, JsonNode v) {
        if (v != null && !v.isMissingNode() && !v.isNull()) {
            body.put(key, v);
        }
    }

    private static boolean isEmpty(String s) { return s == null || s.isEmpty(); }
    private static boolean notEmpty(String s) { return s != null && !s.isEmpty(); }

    private static final class ModelItems {
        @com.fasterxml.jackson.annotation.JsonProperty("Items")
        public List<V1Model> items;
    }
    private static final class IdResult {
        @com.fasterxml.jackson.annotation.JsonProperty("ID") public String id;
    }
    private static final class ProvidersResult {
        @com.fasterxml.jackson.annotation.JsonProperty("Providers") public List<String> providers;
    }
    private static final class ProviderItems {
        @com.fasterxml.jackson.annotation.JsonProperty("Items") public List<V1ModelProvider> items;
    }
    private static final class SchemaResult {
        @com.fasterxml.jackson.annotation.JsonProperty("CredentialSchema") public Object credentialSchema;
    }
}
