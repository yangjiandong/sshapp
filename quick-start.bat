@echo off
echo [INFO] 确保默认JDK版本为JDK5.0及以上版本.

echo [INFO] 如不能连接Maven官方网站, 修改本文件去掉下面一行的注释.
rem set OFF_LINE=-o

set MVN=mvn
set ANT=ant

if exist "tools\maven\apache-maven-2.2.1\" set MVN="%cd%\tools\maven\apache-maven-2.2.1\bin\mvn.bat"
if exist "tools\ant\apache-ant-1.7.1\" set ANT="%cd%\tools\ant\apache-ant-1.7.1\bin\ant.bat"
echo Maven命令为%MVN%
echo Ant命令为%ANT%

echo [Step 1] 复制tools/maven/central-repository 到 %userprofile%\.m2\repository
rem xcopy /s/e/i/h/d/y "tools\maven\central-repository" "%USERPROFILE%\.m2\repository"

echo [Step 2] 启动H2数据库.
cd tools/h2
call %MVN% %OFF_LINE% initialize -Pstartdb
cd ..\..\

echo [Step 3] 安装SpringSide3 所有modules,examples项目到本地Maven仓库,生成Eclipse项目文件.
call %MVN% %OFF_LINE% eclipse:clean eclipse:eclipse
call %MVN% %OFF_LINE% clean install -Dmaven.test.skip=true

echo [Step 4] 为Mini-Service 初始化数据库, 启动Jetty.
cd examples\mini-service
call %ANT% -f bin/build.xml init-db 
start "Mini-Service" %MVN% %OFF_LINE% -Djetty.port=8084 jetty:run
cd ..\..\

echo [Step 5] 为Mini-Web 初始化数据库, 启动Jetty.
cd examples\mini-web
call %ANT% -f bin/build.xml init-db 
start "Mini-Web" %MVN% %OFF_LINE% -Djetty.port=8085 jetty:run
cd ..\..\

echo [Step 6] 为Showcase 生成Eclipse项目文件, 编译, 打包, 初始化数据库, 启动Jetty.
cd examples\showcase
call %ANT% -f bin/build.xml init-db
start "Showcase" %MVN% %OFF_LINE% -Djetty.port=8086 jetty:run
cd ..\..\

echo [INFO] SpringSide3.0 快速启动完毕.
echo [INFO] 可访问以下演示网址:
echo [INFO] http://localhost:8084/mini-service
echo [INFO] http://localhost:8085/mini-web
echo [INFO] http://localhost:8086/showcase

:end
pause