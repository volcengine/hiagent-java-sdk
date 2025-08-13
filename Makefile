# Makefile for hiagent-java-sdk (Maven multi-module)

# Maven options
MVN = mvn
MVN_OPTS = -B

# Modules
MODULES = hiagent-api hiagent-components hiagent-eva hiagent-observe

.PHONY: all build clean test install package deploy run help

## 默认任务：构建全部模块
all: build

## 构建所有模块
build:
	$(MVN) $(MVN_OPTS) clean install

## 清理构建产物
clean:
	$(MVN) $(MVN_OPTS) clean

## 运行全部测试
test:
	$(MVN) $(MVN_OPTS) test

## 只构建某个模块
# 用法: make module MODULE=api
module:
ifneq ($(MODULE),)
	$(MVN) $(MVN_OPTS) -pl $(MODULE) -am clean install
else
	@echo "用法: make module MODULE=<模块名>"
	@echo "可选模块: $(MODULES)"
	@exit 1
endif

## 打包全部模块
package:
	$(MVN) $(MVN_OPTS) package

## 安装到本地仓库
install:
	$(MVN) $(MVN_OPTS) install

## 发布到远程仓库（需要配置 settings.xml）
deploy:
	$(MVN) $(MVN_OPTS) deploy

## 运行示例
# 用法: make run CLASS=com.volcengine.hiagent.examples.ExampleMain
run:
ifneq ($(CLASS),)
	$(MVN) $(MVN_OPTS) -pl examples exec:java -Dexec.mainClass=$(CLASS)
else
	@echo "用法: make run CLASS=com.volcengine.hiagent.examples.ExampleMain"
	@exit 1
endif

## 显示帮助
help:
	@echo "HiAgent Java SDK Makefile 可用命令:"
	@echo "  make build       - 构建所有模块"
	@echo "  make clean       - 清理构建产物"
	@echo "  make test        - 运行全部测试"
	@echo "  make module MODULE=<模块名>  - 构建单个模块 ($(MODULES))"
	@echo "  make package     - 打包所有模块"
	@echo "  make install     - 安装到本地 Maven 仓库"
	@echo "  make deploy      - 发布到远程 Maven 仓库"
	@echo "  make run CLASS=<MainClass>  - 运行指定类"
