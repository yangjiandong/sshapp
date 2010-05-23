@echo off
echo [INFO] 请保证已安装springside archetypes.

if exist generated-project (rmdir /s/q generated-project)
mkdir generated-project
cd generated-project

call mvn archetype:generate -DarchetypeCatalog=local

echo [INFO] 已在%cd%\generated-project下生成项目.

echo [INFO] 为新项目初始化依赖jar.
cd generated-project
for /D %%a in (*) do cd "%%a"
call mvn dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime -Dsilent=true
call mvn dependency:copy-dependencies -DoutputDirectory=webapp/WEB-INF/lib  -DincludeScope=runtime -Dsilent=true
pause