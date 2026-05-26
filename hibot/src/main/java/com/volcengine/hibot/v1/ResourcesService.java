package com.volcengine.hibot.v1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.internal.Bodies;
import com.volcengine.hibot.internal.RequestExecutor;
import com.volcengine.hibot.internal.Versions;
import com.volcengine.hibot.v1.types.V1Directory;
import com.volcengine.hibot.v1.types.V1DirectoryDeleteParams;
import com.volcengine.hibot.v1.types.V1DirectoryGetByNameParams;
import com.volcengine.hibot.v1.types.V1DirectoryList;
import com.volcengine.hibot.v1.types.V1DirectoryListParams;
import com.volcengine.hibot.v1.types.V1DirectoryNewParams;
import com.volcengine.hibot.v1.types.V1DirectoryUpdateParams;
import com.volcengine.hibot.v1.types.V1Resource;
import com.volcengine.hibot.v1.types.V1ResourceBatchGetParams;
import com.volcengine.hibot.v1.types.V1ResourceDeleteParams;
import com.volcengine.hibot.v1.types.V1ResourceGetByNameParams;
import com.volcengine.hibot.v1.types.V1ResourceList;
import com.volcengine.hibot.v1.types.V1ResourceListParams;
import com.volcengine.hibot.v1.types.V1ResourceNewParams;
import com.volcengine.hibot.v1.types.V1ResourceUpdateParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Mirrors go/hibot/v1/resources.go. */
public final class ResourcesService {
    private final RequestExecutor requester;
    private final HibotConfig config;
    public final DirectoriesService directories;

    public ResourcesService(RequestExecutor requester, HibotConfig config) {
        this.requester = requester;
        this.config = config;
        this.directories = new DirectoriesService(requester, config);
    }

    public V1Resource create(V1ResourceNewParams params) {
        if (params == null || isEmpty(params.name)) {
            throw new IllegalArgumentException("hibot: resource Name is required");
        }
        if (isEmpty(params.blobId)) {
            throw new IllegalArgumentException("hibot: resource BlobID is required (call Uploads.NewBlob first)");
        }
        Map<String, Object> body = Bodies.map();
        body.put("Name", params.name);
        body.put("BlobID", params.blobId);
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        Bodies.putIfNotEmpty(body, "DirectoryID", params.directoryId);
        IdResult r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "CreateResource", body),
                new TypeReference<IdResult>() {});
        if (r == null || isEmpty(r.id)) {
            throw new IllegalStateException("hibot: create resource response missing ID");
        }
        V1Resource out = new V1Resource();
        out.id = r.id;
        out.name = params.name;
        out.type = params.type;
        out.directoryId = params.directoryId;
        return out;
    }

    public V1ResourceList list(V1ResourceListParams params) {
        Map<String, Object> body = Bodies.map();
        if (params != null) {
            Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
            Bodies.putIfNotEmpty(body, "DirectoryID", params.directoryId);
            Bodies.putIfNotEmpty(body, "Name", params.name);
            if (params.page != null) body.put("Page", params.page);
        }
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListResources", body),
                new TypeReference<V1ResourceList>() {});
    }

    public void update(V1ResourceUpdateParams params) {
        if (params == null || isEmpty(params.resourceId)) {
            throw new IllegalArgumentException("hibot: resource id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("ResourceID", params.resourceId);
        body.put("Name", params.name);
        if (params.directoryId != null) body.put("DirectoryID", params.directoryId);
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "UpdateResource", body),
                null);
    }

    public void delete(V1ResourceDeleteParams params) {
        if (params == null || isEmpty(params.resourceId)) {
            throw new IllegalArgumentException("hibot: resource id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("ResourceID", params.resourceId);
        Bodies.putIfNotEmpty(body, "DirectoryID", params.directoryId);
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "DeleteResource", body),
                null);
    }

    public V1Resource getByName(V1ResourceGetByNameParams params) {
        if (params == null || isEmpty(params.name)) {
            throw new IllegalArgumentException("hibot: resource name is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("Name", params.name);
        Bodies.putIfNotEmpty(body, "DirectoryID", params.directoryId);
        Wrapper r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER,
                        "GetResourceByName", body),
                new TypeReference<Wrapper>() {});
        if (r == null || r.resource == null || isEmpty(r.resource.id)) {
            throw new IllegalStateException("hibot: get resource by name response missing ID");
        }
        return r.resource;
    }

    public List<V1Resource> batchGet(V1ResourceBatchGetParams params) {
        if (params == null || params.ids == null || params.ids.isEmpty()) {
            throw new IllegalArgumentException("hibot: resource IDs are required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("IDs", params.ids);
        Items r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER,
                        "BatchGetResources", body),
                new TypeReference<Items>() {});
        if (r == null || r.items == null) return new ArrayList<>();
        return r.items;
    }

    private static boolean isEmpty(String s) { return s == null || s.isEmpty(); }

    private static final class IdResult {
        @com.fasterxml.jackson.annotation.JsonProperty("ID") public String id;
    }
    private static final class Wrapper {
        @com.fasterxml.jackson.annotation.JsonProperty("Resource") public V1Resource resource;
    }
    private static final class Items {
        @com.fasterxml.jackson.annotation.JsonProperty("Items") public List<V1Resource> items;
    }

    /** Sub-service for directories. */
    public static final class DirectoriesService {
        private final RequestExecutor requester;
        private final HibotConfig config;

        public DirectoriesService(RequestExecutor requester, HibotConfig config) {
            this.requester = requester;
            this.config = config;
        }

        public V1Directory create(V1DirectoryNewParams params) {
            if (params == null || isEmpty(params.name)) {
                throw new IllegalArgumentException("hibot: directory name is required");
            }
            Map<String, Object> body = Bodies.map();
            Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
            body.put("Name", params.name);
            DirIdResult r = requester.doAction(
                    new RequestExecutor.Action(config.serverService(), Versions.SERVER,
                            "CreateDirectory", body),
                    new TypeReference<DirIdResult>() {});
            if (r == null || isEmpty(r.id)) {
                throw new IllegalStateException("hibot: create directory response missing ID");
            }
            V1Directory d = new V1Directory();
            d.id = r.id;
            d.name = params.name;
            d.workspaceId = params.workspaceId;
            return d;
        }

        public V1DirectoryList list(V1DirectoryListParams params) {
            Map<String, Object> body = Bodies.map();
            if (params != null) {
                Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
                Bodies.putIfNotEmpty(body, "Name", params.name);
                if (params.page != null) body.put("Page", params.page);
            }
            return requester.doAction(
                    new RequestExecutor.Action(config.serverService(), Versions.SERVER,
                            "ListDirectories", body),
                    new TypeReference<V1DirectoryList>() {});
        }

        public void update(V1DirectoryUpdateParams params) {
            if (params == null || isEmpty(params.directoryId)) {
                throw new IllegalArgumentException("hibot: directory id is required");
            }
            Map<String, Object> body = Bodies.map();
            Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
            body.put("DirectoryID", params.directoryId);
            Bodies.putIfNotEmpty(body, "Name", params.name);
            requester.doAction(
                    new RequestExecutor.Action(config.serverService(), Versions.SERVER,
                            "UpdateDirectory", body),
                    null);
        }

        public void delete(V1DirectoryDeleteParams params) {
            if (params == null || isEmpty(params.directoryId)) {
                throw new IllegalArgumentException("hibot: directory id is required");
            }
            Map<String, Object> body = Bodies.map();
            Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
            body.put("DirectoryID", params.directoryId);
            requester.doAction(
                    new RequestExecutor.Action(config.serverService(), Versions.SERVER,
                            "DeleteDirectory", body),
                    null);
        }

        public V1Directory getByName(V1DirectoryGetByNameParams params) {
            if (params == null || isEmpty(params.name)) {
                throw new IllegalArgumentException("hibot: directory name is required");
            }
            Map<String, Object> body = Bodies.map();
            Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
            body.put("Name", params.name);
            DirWrapper r = requester.doAction(
                    new RequestExecutor.Action(config.serverService(), Versions.SERVER,
                            "GetDirectoryByName", body),
                    new TypeReference<DirWrapper>() {});
            if (r == null || r.directory == null || isEmpty(r.directory.id)) {
                throw new IllegalStateException("hibot: get directory by name response missing ID");
            }
            return r.directory;
        }

        private static boolean isEmpty(String s) { return s == null || s.isEmpty(); }

        private static final class DirIdResult {
            @com.fasterxml.jackson.annotation.JsonProperty("ID") public String id;
        }
        private static final class DirWrapper {
            @com.fasterxml.jackson.annotation.JsonProperty("Directory") public V1Directory directory;
        }
    }
}
