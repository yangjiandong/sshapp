@echo off
echo [INFO] 请保证已安装springside archetypes.

if exist generated-project (rmdir /s/q generated-project)
mkdir generated-project
cd generated-project

call mvn -o archetype:generate -DarchetypeCatalog=local -DarchetypeArtifactId=maven-archetype-webapp

rem echo [INFO] 已在%cd%\generated-project下生成项目.

rem echo [INFO] 为新项目初始化依赖jar.
rem cd generated-project
rem for /D %%a in (*) do cd "%%a"
rem call mvn dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime -Dsilent=true
rem call mvn dependency:copy-dependencies -DoutputDirectory=webapp/WEB-INF/lib  -DincludeScope=runtime -Dsilent=true
pause