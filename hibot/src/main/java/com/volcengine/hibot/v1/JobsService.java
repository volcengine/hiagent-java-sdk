package com.volcengine.hibot.v1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.internal.RequestExecutor;
import com.volcengine.hibot.internal.Versions;
import com.volcengine.hibot.v1.types.*;

/** Mirrors go/hibot/v1/jobs.go. */
public final class JobsService {
    private final RequestExecutor requester;
    private final HibotConfig config;

    public JobsService(RequestExecutor requester, HibotConfig config) {
        this.requester = requester;
        this.config = config;
    }

    public V1ListCronJobsResponse list(V1ListCronJobsRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListCronJobs", params),
                new TypeReference<V1ListCronJobsResponse>() {});
    }

    public V1ListCronJobRunsResponse listRuns(V1ListCronJobRunsRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListCronJobRuns", params),
                new TypeReference<V1ListCronJobRunsResponse>() {});
    }

    public V1ListCronJobRunsForSyncResponse listRunsForSync(V1ListCronJobRunsForSyncRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListCronJobRunsForSync", params),
                new TypeReference<V1ListCronJobRunsForSyncResponse>() {});
    }

    public V1CronJobResponse get(V1GetCronJobRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetCronJob", params),
                new TypeReference<V1CronJobResponse>() {});
    }

    public V1CreateCronJobResponse create(V1CreateCronJobRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "CreateCronJob", params),
                new TypeReference<V1CreateCronJobResponse>() {});
    }

    public void update(V1UpdateCronJobRequest params) {
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "UpdateCronJob", params),
                null);
    }

    public void delete(V1DeleteCronJobRequest params) {
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "DeleteCronJob", params),
                null);
    }

    public V1CronJobResponse toggle(V1ToggleCronJobRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ToggleCronJob", params),
                new TypeReference<V1CronJobResponse>() {});
    }

    public V1RunCronJobNowResponse runNow(V1RunCronJobNowRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "RunCronJobNow", params),
                new TypeReference<V1RunCronJobNowResponse>() {});
    }
}
