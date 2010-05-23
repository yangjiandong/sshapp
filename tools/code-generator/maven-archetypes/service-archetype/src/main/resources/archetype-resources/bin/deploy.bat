@echo off
echo [INFO] 使用maven编译项目，并部署到tomcat中.
echo [INFO] 请确保Tomcat 6已启动并已在conf/tomcat-users.xml中设置admin用户.

cd ..
call mvn package cargo:redeploy
cd bin
pause