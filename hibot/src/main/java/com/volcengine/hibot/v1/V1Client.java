package com.volcengine.hibot.v1;

import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.internal.RequestExecutor;

/** Aggregates V1 services. Mirrors go/hibot/v1/client.go. */
public final class V1Client {
    public final UploadsService uploads;
    public final EnvironmentsService environments;
    public final ModelsService models;
    public final PromptsService prompts;
    public final ResourcesService resources;
    public final McpsService mcps;
    public final SkillsService skills;
    public final AgentsService agents;
    public final SessionsService sessions;

    public V1Client(RequestExecutor requester, HibotConfig config) {
        this.uploads = new UploadsService(requester, config);
        this.environments = new EnvironmentsService(requester, config);
        this.models = new ModelsService(requester, config);
        this.prompts = new PromptsService(requester, config);
        this.resources = new ResourcesService(requester, config);
        this.mcps = new McpsService(requester, config);
        this.skills = new SkillsService(requester, config);
        this.agents = new AgentsService(requester, config, this.environments);
        this.sessions = new SessionsService(requester, config);
    }
}
