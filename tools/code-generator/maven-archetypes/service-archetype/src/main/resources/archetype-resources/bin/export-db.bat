@echo off
echo [INFO] [INFO] 使用maven dbunit plugin 导出数据库.

cd ..
call mvn dbunit:export
pause