@echo off
echo [INFO] 确保设置path系统变量含ANT1.7以上版本的bin目录.
echo [INFO] 确保本地WebService应用已启动.

set local_driver=%cd:~0,2%
set local_path=%cd%

set exec_path=%0
set exec_path=%exec_path:~0,-14%"
set exec_driver=%exec_path:~1,2%

%exec_driver%
cd %exec_path%

call ant -f save-wsdl-build.xml

%local_driver%
cd %local_path%

echo [INFO] WSDL已保存到webapp/wsdl/mini-service.wsdl.
pause