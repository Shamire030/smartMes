@echo off
chcp 65001 >nul
title Smart MES 一键启动脚本

echo.
echo ==============================================
echo          Smart MES 系统一键启动脚本
echo ==============================================
echo.

:: 检查 Java 是否安装
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ 错误: 未找到 Java，请先安装 JDK 1.8+
    pause
    exit /b 1
)

echo ✅ Java 环境检测通过
echo.

:: 检查 Maven 是否安装
set MAVEN_PATH="D:\apache-maven-3.9.16\bin\mvn.cmd"
if not exist %MAVEN_PATH% (
    echo ⚠️  Maven 未配置，将使用已打包的 JAR 文件启动
    goto :startBackend
)

echo ✅ Maven 环境检测通过
echo.

:: 进入后端目录
cd smartMesProj

:startBackend
echo 🚀 启动后端服务...
echo.

:: 启动后端
java -jar target/smartmes-proj-1.0.0.jar

echo.
echo ==============================================
echo           后端服务启动完成！
echo ==============================================
echo.
echo 服务地址: http://localhost:8081/smartmes
echo Swagger文档: http://localhost:8081/smartmes/swagger-ui.html
echo.

pause