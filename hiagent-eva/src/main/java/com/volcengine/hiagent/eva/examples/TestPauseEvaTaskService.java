package com.volcengine.hiagent.eva.examples;

import com.volcengine.ApiException;
import com.volcengine.hiagent.api.model.GetEvaTaskReportResponse;
import com.volcengine.hiagent.api.model.base.EvaTargetCustomAPPConfig;
import com.volcengine.hiagent.api.model.base.ModelAgentConfig;
import com.volcengine.hiagent.eva.EvaService;
import com.volcengine.hiagent.eva.InferenceFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestPauseEvaTaskService {
    private static final Logger logger = Logger.getLogger(TestPauseEvaTaskService.class.getName());

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
            logger.info("开始停止评估任务...");
            evaService.pause(
                    taskName
            );

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
