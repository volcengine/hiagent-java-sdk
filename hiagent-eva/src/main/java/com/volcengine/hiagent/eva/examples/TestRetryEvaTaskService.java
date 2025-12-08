package com.volcengine.hiagent.eva.examples;

import com.volcengine.ApiException;
import com.volcengine.hiagent.api.model.GetEvaTaskReportResponse;
import com.volcengine.hiagent.eva.EvaService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestRetryEvaTaskService {
    private static final Logger logger = Logger.getLogger(TestRetryEvaTaskService.class.getName());

    public static void main(String[] args) {
        try {
            // 从环境变量获取配置信息
            String ak = System.getenv("VOLC_ACCESSKEY");
            String sk = System.getenv("VOLC_SECRETKEY");
            String endpoint = System.getenv("HIAGENT_TOP_ENDPOINT");
            String workspaceID = System.getenv("WORKSPACE_ID");
            String taskName = System.getenv("TASK_NAME");
            String appID = System.getenv("CUSTOM_APP_ID");


            // 验证必要的环境变量
            validateEnvVars(ak, sk, workspaceID, appID);

            // 创建EvaService实例
            EvaService evaService = new EvaService(endpoint, ak, sk, workspaceID, appID);


            // 运行评估任务
            logger.info("开始重试评估任务...");
            GetEvaTaskReportResponse report = evaService.retry(
                    taskName
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
     * 验证必要的环境变量
     */
    private static void validateEnvVars(String ak, String sk, String workspaceID, String appID) {
        List<String> missingVars = new ArrayList<>();
        if (ak == null || ak.isEmpty()) missingVars.add("VOLC_ACCESSKEY");
        if (sk == null || sk.isEmpty()) missingVars.add("VOLC_SECRETKEY");
        if (workspaceID == null || workspaceID.isEmpty()) missingVars.add("WORKSPACE_ID");
        if (appID == null || appID.isEmpty()) missingVars.add("CUSTOM_APP_ID");

        if (!missingVars.isEmpty()) {
            throw new IllegalStateException("缺少必要的环境变量: " + String.join(", ", missingVars));
        }
    }
}
