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
package com.volcengine.hiagent.eva.examples;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volcengine.ApiException;
import com.volcengine.hiagent.api.model.GetEvaTaskReportResponse;
import com.volcengine.hiagent.api.model.base.*;
import com.volcengine.hiagent.eva.EvaService;
import com.volcengine.hiagent.eva.InferenceFunction;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.volcengine.hiagent.api.model.base.EvaConversationStatus.EvaConversationStatusSucceed;

public class TestInferenceAndEvaluateEvaTaskService {
    private static final Logger logger = Logger.getLogger(TestInferenceAndEvaluateEvaTaskService.class.getName());

    public static void main(String[] args) {
        try {
            // 从环境变量获取配置信息
            String ak = System.getenv("VOLC_ACCESSKEY");
            String sk = System.getenv("VOLC_SECRETKEY");
            String endpoint = System.getenv("HIAGENT_TOP_ENDPOINT");
            String workspaceID = System.getenv("WORKSPACE_ID");
            String appID = System.getenv("CUSTOM_APP_ID");
            String datasetID = System.getenv("DATASET_ID");
            String datasetVersionID = System.getenv("DATASET_VERSION_ID");
            String rulesetID = System.getenv("RULESET_ID");
            String taskName = System.getenv("TASK_NAME");
            String ruleParamFiles = System.getenv("RULE_PARAM_FILE");
            int maxConversations = Integer.parseInt(System.getenv("MAX_CONVERSATIONS"));
            // 读取规则参数文件（如果存在），反序列化为 List<EvaTaskRuleParams>，否则传入 null
            List<EvaTaskRuleParams> ruleParams = loadRuleParamsFromEnv(ruleParamFiles);

            // 验证必要的环境变量
            validateEnvVars(ak, sk, workspaceID, appID, datasetID, rulesetID, ruleParams);

            // 创建EvaService实例
            EvaService evaService = new EvaService(endpoint, ak, sk, workspaceID, appID);

            // 创建模型代理配置
            ModelAgentConfig modelConfig = new ModelAgentConfig();
            modelConfig.setTemperature(0.7);
            modelConfig.setTopP(0.9);
            modelConfig.setMaxTokens(2048);
            modelConfig.setRoundsReserved(5);
            modelConfig.setRagEnabled(false);
            EvaTargetCustomAPPConfig customAPPConfig = new EvaTargetCustomAPPConfig(appID, modelConfig);


            // 实现InferenceFunction接口
            InferenceFunction inferenceFunction = new ExampleInferenceFunction();

            // 运行评估任务
            logger.info("开始运行评估任务...");
            GetEvaTaskReportResponse report = evaService.inferenceAndEvaluate(
                    datasetID,
                    datasetVersionID,
                    taskName,
                    rulesetID,
                    maxConversations,
                    customAPPConfig,
                    ruleParams,
                    inferenceFunction
            );

            // 打印评估报告结果
            printReport(report);

        } catch (ApiException e) {
            logger.log(Level.SEVERE, "API调用异常: " + e.getMessage(), e);
            System.err.println("错误码: " + e.getCode());
            System.err.println("错误信息: " + e.getMessage());
            System.err.println("响应体: " + e.getResponseBody());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "测试运行异常: " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 验证必要的环境变量
     */
    private static void validateEnvVars(String ak, String sk, String workspaceID, String appID, String datasetID, String rulesetID, List<EvaTaskRuleParams> ruleParams) {
        List<String> missingVars = new ArrayList<>();
        if (ak == null || ak.isEmpty()) missingVars.add("VOLC_ACCESSKEY");
        if (sk == null || sk.isEmpty()) missingVars.add("VOLC_SECRETKEY");
        if (workspaceID == null || workspaceID.isEmpty()) missingVars.add("WORKSPACE_ID");
        if (appID == null || appID.isEmpty()) missingVars.add("CUSTOM_APP_ID");
        if (datasetID == null || datasetID.isEmpty()) missingVars.add("DATASET_ID");
        if ((rulesetID == null || rulesetID.isEmpty()) && (ruleParams == null || ruleParams.isEmpty())) {
            missingVars.add("RULESET_ID");
            missingVars.add("RULE_PARAM_FILE");
        }

        if (!missingVars.isEmpty()) {
            throw new IllegalStateException("缺少必要的环境变量: " + String.join(", ", missingVars));
        }
    }

    /**
     * 从环境变量指定的文件路径加载规则参数
     * RULE_PARAM_FILE 为一个 JSON 文件路径；如果为空或文件不存在，则返回 null
     */
    private static List<EvaTaskRuleParams> loadRuleParamsFromEnv(String ruleParamFilePath) {
        try {
            if (ruleParamFilePath == null || ruleParamFilePath.isEmpty()) {
                Logger.getLogger(TestInferenceAndEvaluateEvaTaskService.class.getName())
                        .info("环境变量 RULE_PARAM_FILE 未设置或为空，ruleParams 将传入 null");
                return null;
            }
            Path path = Paths.get(ruleParamFilePath);
            if (!Files.exists(path)) {
                Logger.getLogger(TestInferenceAndEvaluateEvaTaskService.class.getName())
                        .warning("规则参数文件不存在: " + ruleParamFilePath + "，ruleParams 将传入 null");
                return null;
            }
            String json = Files.readString(path);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, new TypeReference<List<EvaTaskRuleParams>>() {
            });
        } catch (Exception e) {
            Logger.getLogger(TestInferenceAndEvaluateEvaTaskService.class.getName())
                    .log(Level.WARNING, "读取/解析规则参数文件失败: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 打印评估报告结果
     */
    private static void printReport(GetEvaTaskReportResponse report) {
        if (report == null) {
            logger.warning("评估报告为空");
            return;
        }

        logger.info("评估报告摘要:");
        logger.info("创建时间: " + report.getCreatedAt());
        logger.info("更新时间: " + report.getUpdatedAt());
        logger.info("创建人: " + report.getCreatedBy());
        logger.info("更新人: " + report.getUpdatedBy());

        // 打印规则信息
        if (report.getRules() != null && !report.getRules().isEmpty()) {
            logger.info("规则数量: " + report.getRules().size());
            // 实际使用中可以根据需要打印更多规则详情
        }

        // 打印目标信息
        if (report.getTargets() != null && !report.getTargets().isEmpty()) {
            logger.info("目标数量: " + report.getTargets().size());
            report.getTargets().forEach(target -> {
                logger.info("  目标ID: " + target.getTargetID());
                logger.info("  总耗时: " + target.getDuration() + "ms");
                logger.info("  总消耗Token: " + target.getCostTokens());
                logger.info("  平均消耗Token: " + target.getAvgCostTokens());
                logger.info("  平均耗时: " + target.getAvgDuration() + "ms");
                logger.info("  平均首字符响应时间: " + target.getAvgTTFT() + "ms");
            });
        }
    }

    /**
     * InferenceFunction接口的示例实现
     * 这个实现处理输入的案例数据并返回模拟的评估结果
     */
    private static class ExampleInferenceFunction implements InferenceFunction {
        private static final Logger logger = Logger.getLogger(ExampleInferenceFunction.class.getName());
        private static final Random random = new Random();

        @Override
        public List<EvaTaskResultTargetContentPair> execute(List<Map<String, Cell>> caseData) throws Error {
            logger.info("处理案例数据，数据项数量: " + caseData.size());

            // 创建结果列表
            List<EvaTaskResultTargetContentPair> results = new ArrayList<>();

            // 模拟处理每一轮对话
            int round = 1;

            // 从案例数据中提取输入信息
            String inputText = extractInputText(caseData);
            logger.info("提取的输入文本: " + inputText);

            // 创建第一个回合的响应
            EvaTaskResultTargetContentPair response = new EvaTaskResultTargetContentPair();
            response.setRound(round);
            response.setStatus(EvaConversationStatusSucceed);
            response.setContent(generateMockResponse(inputText));
            response.setContentThought("这是一个模拟的思考过程，基于输入生成了回答。");
            response.setCostTokens(random.nextInt(200) + 100); // 模拟消耗的token数
            response.setInferenceDuration(random.nextInt(500) + 500); // 模拟推理耗时
            response.setRuleDuration(random.nextInt(100) + 50); // 模拟规则处理耗时
            response.setTTFT(random.nextInt(200) + 100); // 模拟首字符响应时间

            results.add(response);

            logger.info("生成了模拟的评估结果，回合数: " + results.size());

            return results;
        }

        /**
         * 从案例数据中提取输入文本
         */
        private String extractInputText(List<Map<String, Cell>> caseData) {
            if (caseData == null || caseData.isEmpty()) {
                return "测试输入文本";
            }

            // 尝试从案例数据中提取第一个有效的文本输入
            for (Map<String, Cell> rowData : caseData) {
                for (Map.Entry<String, Cell> entry : rowData.entrySet()) {
                    Cell cell = entry.getValue();
                    if (cell != null && cell.getText() != null && !cell.getText().isEmpty()) {
                        return cell.getText();
                    }
                    // 也可以尝试从其他字段中提取内容
                    if (cell != null && cell.getJson() != null && !cell.getJson().isEmpty()) {
                        return "JSON数据: " + cell.getJson().substring(0, Math.min(100, cell.getJson().length())) + "...";
                    }
                }
            }

            return "测试输入文本";
        }

        /**
         * 生成模拟的响应内容
         */
        private String generateMockResponse(String input) {
            // 这里可以根据实际需求实现更复杂的响应生成逻辑
            // 当前简单实现：返回基于输入的模拟回答
            return "这是对输入的模拟回答。您的输入是: \"" +
                    (input.length() > 50 ? input.substring(0, 50) + "..." : input) + "\"";
        }
    }
}
