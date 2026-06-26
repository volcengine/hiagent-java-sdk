package com.volcengine.hibot.v1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.hibot.HibotConfig;
import com.volcengine.hibot.internal.Bodies;
import com.volcengine.hibot.internal.RequestExecutor;
import com.volcengine.hibot.internal.Versions;
import com.volcengine.hibot.v1.types.V1BatchGetSkillsRequest;
import com.volcengine.hibot.v1.types.V1BatchGetSkillsResponse;
import com.volcengine.hibot.v1.types.V1CredentialSecretInputParams;
import com.volcengine.hibot.v1.types.V1GetArkSkillHubSkillRequest;
import com.volcengine.hibot.v1.types.V1GetArkSkillHubSkillResponse;
import com.volcengine.hibot.v1.types.V1ImportArkSkillHubSkillRequest;
import com.volcengine.hibot.v1.types.V1ListArkSkillHubSkillsRequest;
import com.volcengine.hibot.v1.types.V1ListArkSkillHubSkillsResponse;
import com.volcengine.hibot.v1.types.V1ParseSkillRequest;
import com.volcengine.hibot.v1.types.V1ParseSkillResponse;
import com.volcengine.hibot.v1.types.V1Skill;
import com.volcengine.hibot.v1.types.V1SkillCredentialInputParams;
import com.volcengine.hibot.v1.types.V1SkillDeleteParams;
import com.volcengine.hibot.v1.types.V1SkillGetParams;
import com.volcengine.hibot.v1.types.V1SkillListParams;
import com.volcengine.hibot.v1.types.V1SkillNewParams;
import com.volcengine.hibot.v1.types.V1SkillResolveVersionParams;
import com.volcengine.hibot.v1.types.V1SkillUpdateParams;
import com.volcengine.hibot.v1.types.V1SkillVersion;
import com.volcengine.hibot.v1.types.V1SkillVersionListParams;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Mirrors go/hibot/v1/skills.go. */
public final class SkillsService {
    private final RequestExecutor requester;
    private final HibotConfig config;

    public SkillsService(RequestExecutor requester, HibotConfig config) {
        this.requester = requester;
        this.config = config;
    }

    public V1ParseSkillResponse parse(V1ParseSkillRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ParseSkill", params),
                new TypeReference<V1ParseSkillResponse>() {});
    }

    public V1ListArkSkillHubSkillsResponse listArkSkillHubSkills(V1ListArkSkillHubSkillsRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListArkSkillHubSkills", params),
                new TypeReference<V1ListArkSkillHubSkillsResponse>() {});
    }

    public V1GetArkSkillHubSkillResponse getArkSkillHubSkill(V1GetArkSkillHubSkillRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetArkSkillHubSkill", params),
                new TypeReference<V1GetArkSkillHubSkillResponse>() {});
    }

    public V1ParseSkillResponse importArkSkillHubSkill(V1ImportArkSkillHubSkillRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ImportArkSkillHubSkill", params),
                new TypeReference<V1ParseSkillResponse>() {});
    }

    /**
     * Creates a skill and returns a {@link V1Skill} handle for the created record.
     *
     * <p>The upstream {@code CreateSkill} API currently returns only the created {@code ID}.
     * The SDK therefore populates the returned object with that {@code ID} and echoes the
     * caller-supplied fields from {@code params} when available.
     */
    public V1Skill create(V1SkillNewParams params) {
        if (params == null) params = new V1SkillNewParams();
        if (Bodies.isEmpty(params.source)) params.source = "manual";
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        Bodies.putIfNotEmpty(body, "SkillID", params.skillId);
        Bodies.putIfNotEmpty(body, "Name", params.name);
        Bodies.putIfNotEmpty(body, "Description", params.description);
        Bodies.putIfNotEmpty(body, "Source", params.source);
        Bodies.putIfNotEmpty(body, "BlobID", params.blobId);
        if (params.enabled != null) body.put("Enabled", params.enabled);
        Bodies.putIfNotEmpty(body, "Version", params.version);
        Bodies.putIfNotEmpty(body, "SlugID", params.slugId);
        if (params.credentialConfig != null) {
            body.put("CredentialConfig", credentialConfigToMap(params.credentialConfig));
        }
        IdResult r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "CreateSkill", body),
                new TypeReference<IdResult>() {});
        if (r == null || Bodies.isEmpty(r.id)) {
            throw new IllegalStateException("hibot: create skill response missing ID");
        }
        V1Skill skill = new V1Skill();
        skill.id = r.id;
        skill.skillId = params.skillId;
        skill.name = params.name;
        skill.description = params.description;
        skill.source = params.source;
        skill.version = params.version;
        skill.enabled = params.enabled;
        skill.slugId = params.slugId;
        return skill;
    }

    public List<V1Skill> list(V1SkillListParams params) {
        Map<String, Object> body = Bodies.map();
        if (params != null) {
            Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
            Bodies.putIfNotEmpty(body, "Keyword", params.keyword);
            Bodies.putIfNotEmpty(body, "Source", params.source);
            Bodies.putIfNotEmpty(body, "Name", params.name);
            Bodies.putIfNotEmpty(body, "SlugID", params.slugId);
            if (params.page != null) body.put("Page", params.page);
        }
        Items r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListSkills", body),
                new TypeReference<Items>() {});
        if (r == null || r.items == null) return new ArrayList<>();
        return r.items;
    }

    public V1Skill get(V1SkillGetParams params) {
        if (params == null || (Bodies.isEmpty(params.id) && Bodies.isEmpty(params.skillId))) {
            throw new IllegalArgumentException("hibot: skill id or skill_id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        Bodies.putIfNotEmpty(body, "ID", params.id);
        Bodies.putIfNotEmpty(body, "SkillID", params.skillId);
        Bodies.putIfNotEmpty(body, "Version", params.version);
        V1Skill r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "GetSkill", body),
                new TypeReference<V1Skill>() {});
        if (r == null || Bodies.isEmpty(r.id)) {
            throw new IllegalStateException("hibot: get skill response missing ID");
        }
        return r;
    }

    public V1BatchGetSkillsResponse batchGet(V1BatchGetSkillsRequest params) {
        return requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "BatchGetSkills", params),
                new TypeReference<V1BatchGetSkillsResponse>() {});
    }

    public void update(V1SkillUpdateParams params) {
        if (params == null || (Bodies.isEmpty(params.id) && Bodies.isEmpty(params.skillId))) {
            throw new IllegalArgumentException("hibot: skill id or skill_id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        if (!Bodies.isEmpty(params.id)) body.put("ID", params.id);
        if (!Bodies.isEmpty(params.skillId)) body.put("SkillID", params.skillId);
        if (!Bodies.isEmpty(params.version)) body.put("Version", params.version);
        if (params.description != null) body.put("Description", params.description);
        if (params.source != null) body.put("Source", params.source);
        if (params.artifactId != null) body.put("ArtifactID", params.artifactId);
        if (params.enabled != null) body.put("Enabled", params.enabled);
        if (params.newVersion != null) body.put("NewVersion", params.newVersion);
        if (params.slugId != null) body.put("SlugID", params.slugId);
        if (params.credentialConfig != null) {
            body.put("CredentialConfig", credentialConfigToMap(params.credentialConfig));
        }
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "UpdateSkill", body),
                null);
    }

    public void delete(V1SkillDeleteParams params) {
        if (params == null || (Bodies.isEmpty(params.id) && Bodies.isEmpty(params.skillId))) {
            throw new IllegalArgumentException("hibot: skill id or skill_id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        Bodies.putIfNotEmpty(body, "ID", params.id);
        Bodies.putIfNotEmpty(body, "SkillID", params.skillId);
        Bodies.putIfNotEmpty(body, "Version", params.version);
        requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "DeleteSkill", body),
                null);
    }

    public List<V1SkillVersion> listVersions(V1SkillVersionListParams params) {
        if (params == null || Bodies.isEmpty(params.skillId)) {
            throw new IllegalArgumentException("hibot: skill_id is required");
        }
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("SkillID", params.skillId);
        Bodies.putIfNotEmpty(body, "SortBy", params.sortBy);
        Bodies.putIfNotEmpty(body, "SortOrder", params.sortOrder);
        if (params.page != null) body.put("Page", params.page);
        VersionItems r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListSkillVersions", body),
                new TypeReference<VersionItems>() {});
        if (r == null || r.items == null) return new ArrayList<>();
        return r.items;
    }

    public V1SkillVersion resolveVersion(V1SkillResolveVersionParams params) {
        if (params == null) {
            throw new IllegalArgumentException("hibot: skill name is required");
        }
        if (!Bodies.isEmpty(params.id)) {
            V1SkillVersion v = new V1SkillVersion();
            v.id = params.id;
            v.name = params.name;
            v.constraint = params.constraint;
            return v;
        }
        if (Bodies.isEmpty(params.name)) {
            throw new IllegalArgumentException("hibot: skill name is required");
        }
        String skillId = resolveSkillId(params);
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("SkillID", skillId);
        VersionItems r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListSkillVersions", body),
                new TypeReference<VersionItems>() {});
        V1SkillVersion v = (r != null && r.items != null && !r.items.isEmpty()) ? r.items.get(0) : null;
        if (v == null || Bodies.isEmpty(v.id)) {
            throw new IllegalStateException(
                    "hibot: no skill version matched name=\"" + params.name
                            + "\" constraint=\"" + (params.constraint == null ? "" : params.constraint) + "\"");
        }
        v.name = params.name;
        v.constraint = params.constraint;
        return v;
    }

    private String resolveSkillId(V1SkillResolveVersionParams params) {
        Map<String, Object> body = Bodies.map();
        Bodies.putIfNotEmpty(body, "WorkspaceID", params.workspaceId);
        body.put("Name", params.name);
        SkillIdItems r = requester.doAction(
                new RequestExecutor.Action(config.serverService(), Versions.SERVER, "ListSkills", body),
                new TypeReference<SkillIdItems>() {});
        if (r != null && r.items != null) {
            for (SkillIdItem it : r.items) {
                if (params.name.equals(it.name) && !Bodies.isEmpty(it.skillId)) {
                    return it.skillId;
                }
            }
            if (!r.items.isEmpty() && !Bodies.isEmpty(r.items.get(0).skillId)) {
                return r.items.get(0).skillId;
            }
        }
        throw new IllegalStateException("hibot: skill \"" + params.name + "\" not found");
    }

    /** 把 V1SkillCredentialInputParams 序列化为服务端 CredentialConfig 字段。 */
    static Map<String, Object> credentialConfigToMap(V1SkillCredentialInputParams cfg) {
        Map<String, Object> body = new LinkedHashMap<>();
        if (cfg.name != null && !cfg.name.isEmpty()) body.put("Name", cfg.name);
        if (cfg.description != null && !cfg.description.isEmpty()) body.put("Description", cfg.description);
        if (cfg.source != null && !cfg.source.isEmpty()) body.put("Source", cfg.source);
        if (cfg.providerType != null && !cfg.providerType.isEmpty()) body.put("ProviderType", cfg.providerType);
        if (cfg.config != null) body.put("Config", cfg.config);
        if (cfg.secrets != null && !cfg.secrets.isEmpty()) {
            List<Map<String, Object>> secrets = new ArrayList<>();
            for (V1CredentialSecretInputParams s : cfg.secrets) {
                Map<String, Object> entry = new LinkedHashMap<>();
                if (s.secretId != null && !s.secretId.isEmpty()) entry.put("SecretID", s.secretId);
                if (s.keyName != null && !s.keyName.isEmpty()) entry.put("KeyName", s.keyName);
                if (s.description != null && !s.description.isEmpty()) entry.put("Description", s.description);
                if (s.secretType != null && !s.secretType.isEmpty()) entry.put("SecretType", s.secretType);
                if (s.secretValue != null && !s.secretValue.isEmpty()) entry.put("SecretValue", s.secretValue);
                secrets.add(entry);
            }
            body.put("Secrets", secrets);
        }
        return body;
    }

    private static final class IdResult {
        @com.fasterxml.jackson.annotation.JsonProperty("ID") public String id;
    }
    private static final class Items {
        @com.fasterxml.jackson.annotation.JsonProperty("Items") public List<V1Skill> items;
    }
    private static final class VersionItems {
        @com.fasterxml.jackson.annotation.JsonProperty("Items") public List<V1SkillVersion> items;
    }
    private static final class SkillIdItems {
        @com.fasterxml.jackson.annotation.JsonProperty("Items") public List<SkillIdItem> items;
    }
    private static final class SkillIdItem {
        @com.fasterxml.jackson.annotation.JsonProperty("SkillID") public String skillId;
        @com.fasterxml.jackson.annotation.JsonProperty("Name") public String name;
    }
}
