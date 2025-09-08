#!/bin/bash

# 读取 .env 文件（如果存在）
if [ -f .env ]; then
    echo "检测到 .env 文件，正在加载..."
    set -a
    source .env
    set +a
fi

# 检查参数
if [ -z "$1" ]; then
    echo "❌ 缺少测试用例类名参数"
    echo "用法: $0 <TestClassName>"
    echo "例如: $0 com.volcengine.hiagent.api.examples.TestCreateApiToken"
    exit 1
fi

TEST_CLASS=$1

# 检查环境变量
echo "检查环境变量..."
MISSING_VARS=""

if [ -z "$VOLC_ACCESSKEY" ]; then
    MISSING_VARS="$MISSING_VARS VOLC_ACCESSKEY"
fi

if [ -z "$VOLC_SECRETKEY" ]; then
    MISSING_VARS="$MISSING_VARS VOLC_SECRETKEY"
fi

if [ -n "$MISSING_VARS" ]; then
    echo "❌ 缺少以下环境变量: $MISSING_VARS"
    echo
    echo "请设置环境变量："
    echo "export VOLC_ACCESSKEY=\"your_access_key\""
    echo "export VOLC_SECRETKEY=\"your_secret_key\""
    echo "export HIAGENT_TOP_REGION=\"cn-beijing\"  # 可选，默认值"
    echo "export HIAGENT_TOP_ENDPOINT=\"observe.volcengineapi.com\"  # 可选，默认值"
    echo "export WORKSPACE_ID=\"your_workspace_id\""
    echo "export CUSTOM_APP_ID=\"your_custom_app_id\""
    echo
    echo "或者创建 .env 文件（如果支持的话）"
    exit 1
fi

echo "✓ 环境变量检查通过"
echo

# 设置默认值
if [ -z "$HIAGENT_TOP_REGION" ]; then
    export HIAGENT_TOP_REGION="cn-beijing"
    echo "使用默认 HIAGENT_TOP_REGION: $HIAGENT_TOP_REGION"
fi

if [ -z "$HIAGENT_TOP_ENDPOINT" ]; then
    export HIAGENT_TOP_ENDPOINT="observe.volcengineapi.com"
    echo "使用默认 HIAGENT_TOP_ENDPOINT: $HIAGENT_TOP_ENDPOINT"
fi

echo
echo "当前配置："
echo "  HIAGENT_TOP_REGION: $HIAGENT_TOP_REGION"
echo "  HIAGENT_TOP_ENDPOINT: $HIAGENT_TOP_ENDPOINT"
echo "  WORKSPACE_ID: $WORKSPACE_ID"
echo "  CUSTOM_APP_ID: $CUSTOM_APP_ID"
echo

# 进入正确的目录
echo "进入 hiagent-api 目录..."
cd hiagent-api || {
    echo "❌ 无法进入 hiagent-api 目录"
    echo "请确保在项目根目录运行此脚本"
    exit 1
}

echo "正在编译项目..."
mvn clean compile -q

if [ $? -ne 0 ]; then
    echo "❌ 编译失败"
    exit 1
fi

echo "✓ 编译成功"
echo

echo "正在运行 $TEST_CLASS..."
echo "=================================="

# 运行测试
mvn exec:java -Dexec.mainClass="$TEST_CLASS" -q

echo "=================================="
echo "测试完成"
