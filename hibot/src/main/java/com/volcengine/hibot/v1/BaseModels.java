package com.volcengine.hibot.v1;

import java.util.Collections;
import java.util.List;

/**
 * Built-in model registry exported from the aigw {@code ListModelProvider} API.
 *
 * <p>Mirrors go/hibot/v1/base_models.go (verbatim 100 entries).
 */
public final class BaseModels {
    private BaseModels() {}

    // BaseModelType — capability types registered by aigw.
    public static final String BASE_MODEL_TYPE_TEXT_GENERATION = "text-generation";
    public static final String BASE_MODEL_TYPE_EMBEDDINGS = "embeddings";
    public static final String BASE_MODEL_TYPE_VISION = "vision";
    public static final String BASE_MODEL_TYPE_AUDIO = "audio";
    public static final String BASE_MODEL_TYPE_RERANKING = "reranking";

    // BaseModelProvider — providers registered by aigw.
    public static final String BASE_MODEL_PROVIDER_VOLCENGINE = "volcengine";
    public static final String BASE_MODEL_PROVIDER_BYTEPLUS = "byteplus";
    public static final String BASE_MODEL_PROVIDER_VOLCENGINE_AICC = "volcengine_aicc";
    public static final String BASE_MODEL_PROVIDER_ZHIPU = "zhipu";
    public static final String BASE_MODEL_PROVIDER_KIMI = "kimi";
    public static final String BASE_MODEL_PROVIDER_MINIMAX = "minimax";
    public static final String BASE_MODEL_PROVIDER_DEEPSEEK = "deepseek";
    public static final String BASE_MODEL_PROVIDER_AWS = "aws";
    public static final String BASE_MODEL_PROVIDER_AZURE_OPENAI = "azure_openai";
    public static final String BASE_MODEL_PROVIDER_OPENAI = "openai";
    public static final String BASE_MODEL_PROVIDER_TONGYI = "tongyi";
    public static final String BASE_MODEL_PROVIDER_WENXIN = "wenxin";
    public static final String BASE_MODEL_PROVIDER_GOOGLE = "google";
    public static final String BASE_MODEL_PROVIDER_ANTHROPIC = "anthropic";
    public static final String BASE_MODEL_PROVIDER_LOCALAI = "localai";

    /** A single aigw built-in model entry. */
    public static final class BaseModel {
        public final String provider;
        public final String type;
        public final String modelName;

        public BaseModel(String provider, String type, String modelName) {
            this.provider = provider;
            this.type = type;
            this.modelName = modelName;
        }
    }

    /** Built-in aigw model list, lexicographically sorted by Provider, Type, ModelName. */
    public static final List<BaseModel> ALL = Collections.unmodifiableList(java.util.Arrays.asList(
            new BaseModel("azure_openai", "embeddings", "text-embedding-3-large"),
            new BaseModel("azure_openai", "embeddings", "text-embedding-3-small"),
            new BaseModel("azure_openai", "embeddings", "text-embedding-ada-002"),
            new BaseModel("azure_openai", "text-generation", "gpt-4"),
            new BaseModel("azure_openai", "text-generation", "gpt-4o-mini"),
            new BaseModel("azure_openai", "text-generation", "o1"),
            new BaseModel("azure_openai", "text-generation", "o1-preview"),
            new BaseModel("byteplus", "audio", "seed-tts-1.0"),
            new BaseModel("byteplus", "audio", "seed-tts-2.0"),
            new BaseModel("byteplus", "audio", "volc.bigasr.sauc.duration"),
            new BaseModel("byteplus", "audio", "volc.seedasr.sauc.duration"),
            new BaseModel("byteplus", "text-generation", "deepseek-v3"),
            new BaseModel("byteplus", "text-generation", "deepseek-v3-2-251201"),
            new BaseModel("byteplus", "text-generation", "glm-4-7-251222"),
            new BaseModel("byteplus", "text-generation", "kimi-k2-thinking-251104"),
            new BaseModel("byteplus", "text-generation", "seed-1-6-250615"),
            new BaseModel("byteplus", "text-generation", "seed-1-6-flash-250615"),
            new BaseModel("byteplus", "text-generation", "seed-1-8-251228"),
            new BaseModel("byteplus", "text-generation", "seed-2-0-lite-260228"),
            new BaseModel("byteplus", "text-generation", "seed-2-0-mini-260215"),
            new BaseModel("byteplus", "text-generation", "seed-2-0-pro-260328"),
            new BaseModel("byteplus", "vision", "dreamina-seedance-2-0-260128"),
            new BaseModel("byteplus", "vision", "dreamina-seedance-2-0-fast-260128"),
            new BaseModel("byteplus", "vision", "seedream-5.0-lite-260128"),
            new BaseModel("kimi", "text-generation", "kimi-k2.5"),
            new BaseModel("minimax", "text-generation", "minimax-m2.5"),
            new BaseModel("minimax", "text-generation", "minimax-m2.7"),
            new BaseModel("openai", "embeddings", "text-embedding-3-large"),
            new BaseModel("openai", "embeddings", "text-embedding-3-small"),
            new BaseModel("openai", "embeddings", "text-embedding-ada-002"),
            new BaseModel("openai", "text-generation", "gpt-3.5-turbo"),
            new BaseModel("openai", "text-generation", "gpt-4"),
            new BaseModel("openai", "text-generation", "gpt-4o"),
            new BaseModel("openai", "text-generation", "gpt-4o-mini"),
            new BaseModel("openai", "text-generation", "gpt-5"),
            new BaseModel("openai", "text-generation", "gpt-5-chat-latest"),
            new BaseModel("openai", "text-generation", "gpt-5-mini"),
            new BaseModel("openai", "text-generation", "gpt-5-nano"),
            new BaseModel("openai", "text-generation", "o1"),
            new BaseModel("openai", "text-generation", "o1-mini"),
            new BaseModel("openai", "text-generation", "o1-preview"),
            new BaseModel("tongyi", "embeddings", "qwen3-vl-embedding"),
            new BaseModel("tongyi", "embeddings", "text-embedding-v1"),
            new BaseModel("tongyi", "embeddings", "text-embedding-v2"),
            new BaseModel("tongyi", "reranking", "qwen3-rerank"),
            new BaseModel("tongyi", "text-generation", "qwen-plus-latest"),
            new BaseModel("tongyi", "text-generation", "qwen-turbo-latest"),
            new BaseModel("tongyi", "text-generation", "qwen3-0.6b"),
            new BaseModel("tongyi", "text-generation", "qwen3-1.7b"),
            new BaseModel("tongyi", "text-generation", "qwen3-14b"),
            new BaseModel("tongyi", "text-generation", "qwen3-235b-a22b"),
            new BaseModel("tongyi", "text-generation", "qwen3-30b-a3b"),
            new BaseModel("tongyi", "text-generation", "qwen3-32b"),
            new BaseModel("tongyi", "text-generation", "qwen3-4b"),
            new BaseModel("tongyi", "text-generation", "qwen3-8b"),
            new BaseModel("volcengine", "audio", "seed-tts-2.0"),
            new BaseModel("volcengine", "audio", "volc.bigasr.auc_turbo"),
            new BaseModel("volcengine", "audio", "volc.seedasr.sauc.duration"),
            new BaseModel("volcengine", "text-generation", "deepseek-v3-2-251201"),
            new BaseModel("volcengine", "text-generation", "doubao-1-5-lite"),
            new BaseModel("volcengine", "text-generation", "doubao-seed-2-0-code-preview-260215"),
            new BaseModel("volcengine", "text-generation", "doubao-seed-2-0-lite-260215"),
            new BaseModel("volcengine", "text-generation", "doubao-seed-2-0-mini-260215"),
            new BaseModel("volcengine", "text-generation", "doubao-seed-2-0-pro-260215"),
            new BaseModel("volcengine", "text-generation", "glm-4-7-251222"),
            new BaseModel("volcengine", "vision", "doubao-seedance-2-0-260128"),
            new BaseModel("volcengine", "vision", "doubao-seedance-2-0-fast-260128"),
            new BaseModel("volcengine_aicc", "text-generation", "deepseek-v3-2-251201"),
            new BaseModel("volcengine_aicc", "text-generation", "doubao-seed-1-6-250615"),
            new BaseModel("volcengine_aicc", "text-generation", "doubao-seed-2-0-lite-260215"),
            new BaseModel("volcengine_aicc", "text-generation", "doubao-seed-2-0-pro-260215"),
            new BaseModel("volcengine_aicc", "text-generation", "glm-4-7-251222"),
            new BaseModel("wenxin", "embeddings", "bge-large-zh"),
            new BaseModel("wenxin", "text-generation", "AquilaChat-7B"),
            new BaseModel("wenxin", "text-generation", "BLOOMZ-7B"),
            new BaseModel("wenxin", "text-generation", "ChatGLM2-6B-32K"),
            new BaseModel("wenxin", "text-generation", "ERNIE 3.5"),
            new BaseModel("wenxin", "text-generation", "ERNIE Speed"),
            new BaseModel("wenxin", "text-generation", "ERNIE-3.5-8K-0205"),
            new BaseModel("wenxin", "text-generation", "ERNIE-3.5-8K-1222"),
            new BaseModel("wenxin", "text-generation", "ERNIE-4.0-8K"),
            new BaseModel("wenxin", "text-generation", "ERNIE-Bot"),
            new BaseModel("wenxin", "text-generation", "ERNIE-Bot-4"),
            new BaseModel("wenxin", "text-generation", "ERNIE-Bot-8k"),
            new BaseModel("wenxin", "text-generation", "ERNIE-Lite-8K-0308"),
            new BaseModel("wenxin", "text-generation", "ERNIE-Speed"),
            new BaseModel("wenxin", "text-generation", "ERNIE-Speed-128k"),
            new BaseModel("wenxin", "text-generation", "Llama-2-13b-chat"),
            new BaseModel("wenxin", "text-generation", "Llama-2-7b-chat"),
            new BaseModel("wenxin", "text-generation", "Mixtral-8x7B-Instruct"),
            new BaseModel("wenxin", "text-generation", "Qianfan-BLOOMZ-7B-compressed"),
            new BaseModel("wenxin", "text-generation", "Qianfan-Chinese-Llama-2-13B"),
            new BaseModel("wenxin", "text-generation", "Qianfan-Chinese-Llama-2-7B"),
            new BaseModel("wenxin", "text-generation", "XuanYuan-70B-Chat-4bit"),
            new BaseModel("wenxin", "text-generation", "Yi-34B-Chat"),
            new BaseModel("zhipu", "text-generation", "glm-3-turbo"),
            new BaseModel("zhipu", "text-generation", "glm-4"),
            new BaseModel("zhipu", "text-generation", "glm-4v"),
            new BaseModel("zhipu", "text-generation", "glm-5"),
            new BaseModel("zhipu", "text-generation", "glm-5.1")
    ));
}
