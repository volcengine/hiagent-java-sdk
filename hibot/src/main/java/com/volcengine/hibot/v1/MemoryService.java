package com.volcengine.hibot.v1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.internal.RequestExecutor;
import com.volcengine.hibot.internal.Versions;
import com.volcengine.hibot.v1.types.*;

/** Mirrors go/hibot/v1/memory.go. */
public final class MemoryService {
    private final RequestExecutor requester;
    private final HibotConfig config;

    public MemoryService(RequestExecutor requester, HibotConfig config) {
        this.requester = requester;
        this.config = config;
    }

    public V1CreateMemoryStoreResponse create(V1CreateMemoryStoreRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "CreateMemoryStore", params),
                new TypeReference<V1CreateMemoryStoreResponse>() {});
    }

    public V1ListMemoryStoresResponse list(V1ListMemoryStoresRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListMemoryStores", params),
                new TypeReference<V1ListMemoryStoresResponse>() {});
    }

    public V1MemoryStoreResponse get(V1GetMemoryStoreRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetMemoryStore", params),
                new TypeReference<V1MemoryStoreResponse>() {});
    }

    public void update(V1UpdateMemoryStoreRequest params) {
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "UpdateMemoryStore", params),
                null);
    }

    public void delete(V1DeleteMemoryStoreRequest params) {
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "DeleteMemoryStore", params),
                null);
    }

    public V1ListMemoryFilesResponse listFiles(V1ListMemoryFilesRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListMemoryFiles", params),
                new TypeReference<V1ListMemoryFilesResponse>() {});
    }

    public V1ListMemoryFilesResponse searchFiles(V1SearchMemoryFilesRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "SearchMemoryFiles", params),
                new TypeReference<V1ListMemoryFilesResponse>() {});
    }

    public V1MemoryFileResponse getFile(V1GetMemoryFileRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetMemoryFile", params),
                new TypeReference<V1MemoryFileResponse>() {});
    }

    public V1UpsertMemoryFileResponse upsertFile(V1UpsertMemoryFileRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "UpsertMemoryFile", params),
                new TypeReference<V1UpsertMemoryFileResponse>() {});
    }

    public void deleteFile(V1DeleteMemoryFileRequest params) {
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "DeleteMemoryFile", params),
                null);
    }
}
