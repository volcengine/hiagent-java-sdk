package com.volcengine.hibot;

import com.volcengine.hibot.v1.V1ChatStream;
import com.volcengine.hibot.v1.V1Constants;
import com.volcengine.hibot.v1.types.V1Agent;
import com.volcengine.hibot.v1.types.V1AgentDeleteParams;
import com.volcengine.hibot.v1.types.V1AgentListParams;
import com.volcengine.hibot.v1.types.V1AgentNewParams;
import com.volcengine.hibot.v1.types.V1AgentNewParamsToolUnion;
import com.volcengine.hibot.v1.types.V1ManagedAgentModelConfigParams;
import com.volcengine.hibot.v1.types.V1ManagedAgentResourceRefParams;
import com.volcengine.hibot.v1.types.V1ManagedAgentSkillToolParams;
import com.volcengine.hibot.v1.types.V1Message;
import com.volcengine.hibot.v1.types.V1Model;
import com.volcengine.hibot.v1.types.V1ModelGetParams;
import com.volcengine.hibot.v1.types.V1ModelList;
import com.volcengine.hibot.v1.types.V1ModelListParams;
import com.volcengine.hibot.v1.types.V1Resource;
import com.volcengine.hibot.v1.types.V1ResourceDeleteParams;
import com.volcengine.hibot.v1.types.V1ResourceNewParams;
import com.volcengine.hibot.v1.types.V1Session;
import com.volcengine.hibot.v1.types.V1SessionChatEvent;
import com.volcengine.hibot.v1.types.V1SessionChatParams;
import com.volcengine.hibot.v1.types.V1SessionNewParams;
import com.volcengine.hibot.v1.types.V1SkillDeleteParams;
import com.volcengine.hibot.v1.types.V1SkillNewParams;
import com.volcengine.hibot.v1.types.V1SkillVersion;
import com.volcengine.hibot.v1.types.V1UploadBlob;
import com.volcengine.hibot.v1.types.V1UploadBlobParams;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * 真实环境端到端测试，对齐 Go 仓库的 examples/e2e/e2e_test.go 中的
 * runRealEnvJourney 与 TestRealEnvResourceSkillLoop。
 *
 * <p>仅当环境变量 HIBOT_E2E_TOP_HOST 非空时启用，避免污染本地 mock 链路。
 * 同时还需要 HIBOT_E2E_AK / HIBOT_E2E_SK / HIBOT_E2E_WORKSPACE。
 */
@EnabledIfEnvironmentVariable(named = "HIBOT_E2E_TOP_HOST", matches = ".+")
class RealEnvE2eTest {

    private static final String SKILL_PULSE_TOKEN = "PULSE_OK_E2E";
    private static final String RESOURCE_FIXTURE = "testdata/runbook.md";
    private static final String SKILL_FIXTURE_DIR = "testdata/skill";

    /**
     * 用例 A：对齐 runRealEnvJourney，使用工作空间内已有 Agent 跑最小闭环
     * (ListAgents → CreateSession → ChatStreaming → Chat)。
     */
    @Test
    void realEnvJourney_listAgentsCreateSessionStreamingAndBatch() throws Exception {
        String host = trimEnv("HIBOT_E2E_TOP_HOST");
        String ak = trimEnv("HIBOT_E2E_AK");
        String sk = trimEnv("HIBOT_E2E_SK");
        String workspace = trimEnv("HIBOT_E2E_WORKSPACE");
        if (ak.isEmpty() || sk.isEmpty() || workspace.isEmpty()) {
            fail("real-env journey requires HIBOT_E2E_AK / HIBOT_E2E_SK / HIBOT_E2E_WORKSPACE");
        }
        System.out.printf("real-env journey: host=%s workspace=%s%n", host, workspace);

        try (Hibot client = new Hibot(HibotConfig.builder()
                .endpoint(host)
                .accessKey(ak)
                .secretKey(sk)
                .workspaceId(workspace)
                .build())) {

            // Step 1: discover an existing agent — real cluster fixtures own creation.
            // 跳过 e2e- 前缀的临时 agent（resource/skill 闭环用例创建出来的，
            // cleanup 失败时可能残留并指向已删除的 skill，导致流式 chat 直接 fail）。
            List<V1Agent> agents = client.v1.agents.list(new V1AgentListParams());
            if (agents.isEmpty()) {
                fail("real-env workspace " + workspace + " has no agents; please pre-create one before running this test");
            }
            V1Agent agent = null;
            for (V1Agent candidate : agents) {
                if (candidate.name != null && candidate.name.startsWith("e2e-")) {
                    continue;
                }
                agent = candidate;
                break;
            }
            if (agent == null) {
                fail("real-env workspace " + workspace
                        + " only has e2e-* throwaway agents; please pre-create a stable agent before running this test");
            }
            System.out.printf("using agent: id=%s name=%s%n", agent.id, agent.name);

            // Step 2: create a session with no Peer — webchat default path.
            V1SessionNewParams sp = new V1SessionNewParams();
            sp.agentId = agent.id;
            V1Session session = client.v1.sessions.create(sp);
            assertNotNull(session.id, "real-env CreateSession returned empty ID");
            System.out.printf("created session: %s%n", session.id);

            // Step 3: streaming chat — must observe completed.
            StreamResult streaming = runStreamingChat(client, session.id, agent.id,
                    "流式真实环境冒烟：请用一句话介绍你自己。");
            assertNotNull(streaming.finalMessage.id, "real-env streaming final missing id");
            assertNotNull(streaming.finalMessage.content, "real-env streaming final missing content");
            assertFalse(streaming.finalMessage.content.isEmpty(),
                    "real-env streaming final content is empty");
            System.out.printf("streaming final: id=%s content=%s%n",
                    streaming.finalMessage.id, streaming.finalMessage.content);

            // Step 4: batch (non-streaming) chat reuses the same session.
            V1SessionChatParams batchParams = new V1SessionChatParams();
            batchParams.agentId = agent.id;
            batchParams.input = "批量真实环境冒烟：再回答一次同样的问题。";
            V1Message batchFinal = client.v1.sessions.chat(session.id, batchParams);
            assertNotNull(batchFinal.id, "real-env batch final missing id");
            assertNotNull(batchFinal.content, "real-env batch final missing content");
            assertFalse(batchFinal.content.isEmpty(), "real-env batch final content is empty");
            System.out.printf("batch final: id=%s content=%s%n",
                    batchFinal.id, batchFinal.content);
        }
    }

    /**
     * 用例 B：对齐 TestRealEnvResourceSkillLoop —— 上传 runbook + skill zip，
     * 创建临时 Agent 绑定二者，跑 pulse check 闭环并断言：
     *   1. 最终 message.content 包含 PULSE_OK_E2E；
     *   2. SSE 流上观测到 tool_start / tool_complete 事件。
     */
    @Test
    void realEnvResourceSkillLoop_invokesPulseSkill() throws Exception {
        String host = trimEnv("HIBOT_E2E_TOP_HOST");
        String ak = trimEnv("HIBOT_E2E_AK");
        String sk = trimEnv("HIBOT_E2E_SK");
        String workspace = trimEnv("HIBOT_E2E_WORKSPACE");
        if (ak.isEmpty() || sk.isEmpty() || workspace.isEmpty()) {
            fail("real-env loop requires HIBOT_E2E_AK / HIBOT_E2E_SK / HIBOT_E2E_WORKSPACE");
        }
        System.out.printf("real-env loop: host=%s workspace=%s%n", host, workspace);

        boolean keepAgent = !trimEnv("HIBOT_E2E_KEEP_AGENT").isEmpty();

        try (Hibot client = new Hibot(HibotConfig.builder()
                .endpoint(host)
                .accessKey(ak)
                .secretKey(sk)
                .workspaceId(workspace)
                .build())) {

            // Step 1: pick a usable model.
            String modelId;
            String presetId = trimEnv("HIBOT_E2E_MODEL_ID");
            if (!presetId.isEmpty()) {
                modelId = presetId;
                System.out.printf("using preset model id=%s (HIBOT_E2E_MODEL_ID)%n", presetId);
            } else {
                V1Model model = null;
                try {
                    V1ModelGetParams gp = new V1ModelGetParams();
                    gp.modelName = V1Constants.V1_MANAGED_AGENT_MODEL_DOUBAO_SEED_PRO;
                    model = client.v1.models.get(gp);
                    System.out.printf("matched model by ModelName: id=%s name=%s modelName=%s%n",
                            model.id, model.name, model.modelName);
                } catch (RuntimeException e) {
                    System.out.printf("get default model by ModelName failed: %s; falling back to ListModels%n",
                            e.getMessage());
                    V1ModelList list = client.v1.models.list(new V1ModelListParams());
                    if (list == null || list.items == null || list.items.isEmpty()) {
                        fail("real-env workspace " + workspace + " has no models; please pre-create one");
                    }
                    model = list.items.get(0);
                    System.out.printf("fallback model picked: id=%s name=%s modelName=%s type=%s%n",
                            model.id, model.name, model.modelName, model.type);
                }
                modelId = model.id;
            }

            // Step 2: upload Resource fixture.
            Path resourcePath = Paths.get(RESOURCE_FIXTURE);
            String resourceBlobId = uploadFile(client, resourcePath, "text/markdown");
            V1ResourceNewParams rp = new V1ResourceNewParams();
            rp.name = "e2e-runbook-" + System.nanoTime();
            rp.type = V1Constants.V1_RESOURCE_TYPE_DOCUMENT_COLLECTION;
            rp.blobId = resourceBlobId;
            V1Resource resource = client.v1.resources.create(rp);
            System.out.printf("created resource: id=%s name=%s%n", resource.id, resource.name);

            // Step 3: zip skill dir and upload as Skill.
            Path skillZip = buildSkillZip(Paths.get(SKILL_FIXTURE_DIR));
            String skillBlobId = uploadFile(client, skillZip, "application/zip");
            V1SkillNewParams snp = new V1SkillNewParams();
            snp.name = "e2e-runbook-skill-" + System.nanoTime();
            snp.description = "Skill uploaded by hibot-java-sdk e2e closed-loop test.";
            snp.blobId = skillBlobId;
            snp.enabled = Boolean.TRUE;
            snp.version = "1.0.0";
            V1SkillVersion skill = client.v1.skills.create(snp);
            System.out.printf("created skill version: id=%s%n", skill.id);

            // Step 4: create temporary agent — bound to skill + resource.
            String systemPrompt = "你是 hibot-java-sdk 端到端测试助手。"
                    + "用户要求执行 pulse check / 心跳检查时，必须调用 e2e-runbook-skill 工具，并把工具返回的字面 token 原样返回给用户。";
            V1AgentNewParams ap = new V1AgentNewParams();
            ap.name = "e2e-loop-agent-" + System.nanoTime();
            ap.model = new V1ManagedAgentModelConfigParams(modelId);
            ap.system = systemPrompt;
            ap.tools = new ArrayList<>();
            ap.tools.add(V1AgentNewParamsToolUnion.ofSkill(
                    new V1ManagedAgentSkillToolParams(
                            V1Constants.V1_MANAGED_AGENT_SKILL_TOOL_PARAMS_TYPE_SKILL, skill.id)));
            ap.resources = new ArrayList<>();
            ap.resources.add(V1ManagedAgentResourceRefParams.ofResource(resource.id));
            V1Agent agent = client.v1.agents.create(ap);
            System.out.printf("created agent: id=%s%n", agent.id);

            try {
                // Step 5: create Session — webchat default channel.
                V1SessionNewParams snp2 = new V1SessionNewParams();
                snp2.agentId = agent.id;
                V1Session session = client.v1.sessions.create(snp2);
                System.out.printf("created session: %s%n", session.id);

                // Step 6: skill loop — trigger pulse check.
                StreamResult skillRes = runStreamingChat(client, session.id, agent.id,
                        "请执行一次 pulse check（按照 e2e-runbook-skill 的契约调用工具），并把工具返回的 token 原样告诉我。");
                System.out.printf("skill loop events=%s final=%s%n",
                        skillRes.eventNames, skillRes.finalMessage.content);
                if (skillRes.finalMessage.content == null
                        || !skillRes.finalMessage.content.contains(SKILL_PULSE_TOKEN)) {
                    fail("skill loop: agent did not return pulse token \"" + SKILL_PULSE_TOKEN
                            + "\"; system prompt no longer leaks the token, so this proves the skill was NOT invoked. got="
                            + skillRes.finalMessage.content);
                }
                if (!containsAny(skillRes.eventNames,
                        V1Constants.V1_SESSION_CHAT_EVENT_TOOL_START,
                        V1Constants.V1_SESSION_CHAT_EVENT_TOOL_COMPLETE)) {
                    fail("skill loop: no tool_start/tool_complete event observed on SSE stream; "
                            + "the model likely answered without actually invoking e2e-runbook-skill. events="
                            + skillRes.eventNames);
                }
                System.out.printf("skill loop ok: pulse token \"%s\" returned via real skill invocation (events=%s)%n",
                        SKILL_PULSE_TOKEN, skillRes.eventNames);
            } finally {
                if (!keepAgent) {
                    cleanup(() -> {
                        V1AgentDeleteParams dp = new V1AgentDeleteParams(agent.id);
                        client.v1.agents.delete(dp);
                    }, "agent " + agent.id);
                    cleanup(() -> {
                        V1SkillDeleteParams dp = new V1SkillDeleteParams();
                        dp.id = skill.id;
                        client.v1.skills.delete(dp);
                    }, "skill " + skill.id);
                    cleanup(() -> {
                        V1ResourceDeleteParams dp = new V1ResourceDeleteParams();
                        dp.resourceId = resource.id;
                        client.v1.resources.delete(dp);
                    }, "resource " + resource.id);
                }
            }
        }
    }

    // -- helpers ---------------------------------------------------------

    private static String uploadFile(Hibot client, Path path, String contentType) throws IOException {
        if (!Files.exists(path)) {
            fail("fixture missing: " + path.toAbsolutePath());
        }
        byte[] data = Files.readAllBytes(path);
        V1UploadBlobParams params = new V1UploadBlobParams(path.getFileName().toString(), contentType);
        V1UploadBlob resp = client.v1.uploads.uploadBlob(params, data);
        if (resp == null || resp.blobId == null || resp.blobId.isEmpty()) {
            fail("upload " + path + ": empty BlobID");
        }
        return resp.blobId;
    }

    /**
     * Walks the fixture skill directory and produces a temp zip whose entries
     * preserve relative paths (SKILL.md must appear at the zip root).
     */
    private static Path buildSkillZip(Path dir) throws IOException {
        if (!Files.isDirectory(dir)) {
            fail("skill fixture dir missing: " + dir.toAbsolutePath());
        }
        if (!Files.exists(dir.resolve("SKILL.md"))) {
            fail("skill fixture missing SKILL.md: " + dir.toAbsolutePath());
        }
        Path out = Files.createTempFile("hibot-e2e-skill-", ".zip");
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(out))) {
            Files.walk(dir).filter(Files::isRegularFile).forEach(p -> {
                String rel = dir.relativize(p).toString().replace('\\', '/');
                try {
                    zos.putNextEntry(new ZipEntry(rel));
                    zos.write(Files.readAllBytes(p));
                    zos.closeEntry();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        if (Files.size(out) == 0) {
            fail("skill zip is empty: " + out);
        }
        return out;
    }

    /**
     * Consume one full SSE stream, returning the final message and observed
     * event names. Mirrors the Go runStreamingChat helper.
     */
    private static StreamResult runStreamingChat(Hibot client, String sessionId, String agentId, String input) {
        V1SessionChatParams p = new V1SessionChatParams();
        p.agentId = agentId;
        p.input = input;
        List<String> events = new ArrayList<>();
        boolean sawDelta = false;
        boolean sawCompleted = false;
        V1Message finalMsg;
        try (V1ChatStream stream = client.v1.sessions.chatStreaming(sessionId, p)) {
            while (stream.next()) {
                V1SessionChatEvent ev = stream.current();
                events.add(ev.type);
                if (V1Constants.V1_SESSION_CHAT_EVENT_DELTA.equals(ev.type)) {
                    sawDelta = true;
                } else if (V1Constants.V1_SESSION_CHAT_EVENT_COMPLETED.equals(ev.type)) {
                    sawCompleted = true;
                } else if (V1Constants.V1_SESSION_CHAT_EVENT_FAILED.equals(ev.type)) {
                    fail("streaming chat failed: " + (ev.error == null ? "" : ev.error.message));
                }
            }
            if (stream.err() != null) {
                fail("streaming chat err: " + stream.err() + " (events=" + events + ")");
            }
            assertTrue(sawCompleted,
                    "streaming chat: no completed event observed (events=" + events + ")");
            if (!sawDelta) {
                System.out.printf("streaming chat: no delta event (short reply); events=%s%n", events);
            }
            finalMsg = stream.finalMessage();
        }
        StreamResult r = new StreamResult();
        r.finalMessage = finalMsg;
        r.eventNames = events;
        return r;
    }

    private static boolean containsAny(List<String> events, String... targets) {
        for (String e : events) {
            for (String t : targets) {
                if (t.equals(e)) return true;
            }
        }
        return false;
    }

    /**
     * Reads an environment variable and strips wrapping whitespace, backticks
     * and quotes — matches the Go trimEnv helper. Returns "" when absent.
     */
    private static String trimEnv(String key) {
        String v = System.getenv(key);
        if (v == null) return "";
        v = v.trim();
        while (!v.isEmpty()) {
            char c = v.charAt(0);
            if (c == '`' || c == '\'' || c == '"' || c == ' ') {
                v = v.substring(1);
                continue;
            }
            break;
        }
        while (!v.isEmpty()) {
            char c = v.charAt(v.length() - 1);
            if (c == '`' || c == '\'' || c == '"' || c == ' ') {
                v = v.substring(0, v.length() - 1);
                continue;
            }
            break;
        }
        return v;
    }

    private static void cleanup(CleanupAction action, String label) {
        try {
            action.run();
        } catch (Exception e) {
            System.out.printf("cleanup %s: %s%n", label, e.getMessage());
        }
    }

    @FunctionalInterface
    private interface CleanupAction {
        void run() throws Exception;
    }

    private static final class StreamResult {
        V1Message finalMessage;
        List<String> eventNames;
    }
}
