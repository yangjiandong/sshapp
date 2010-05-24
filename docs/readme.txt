springside3
------------

2010.05.24
----------

1、hsql 嵌入式启动
   save/

2、mvn + jetty + debug + eclipse

   application/sshapp/bin/jetty-debug.bat
   sshapp-jetty-debug.launch

2010.05.23
-----------

1、手工启动h2
   tools/h2/start-db.bat

2、改造shwocase pom.xml，直级关联springside3

3、mvn 不访问 网络
   set OFF_LINE=-o
   mv %OFF_LINE%

4、怎样建立标准的springside项目，

   cd tools
   new-project.bat
   --Confirm properties configuration:
   --groupId: org.sshapp
   --artifactId: sshapp
   --version: 1.0-SNAPSHOT
   --package: org.sshapp

   生成后，拷贝到application下
   修改pom.xml

2010.05.22
----------

1、增加git仓库 http://github.com/yangjiandong/sshapp
   git remote add origin git@github.com:yangjiandong/sshapp.git

   git push origin master:refs/heads/master

   提交时，需将ssh-key 加到 github
   github user:yangjiandong,123456789,young.jiandong@gmail.com

2、建立branch 3.3.2
   git branch 3.3.2
   git push origin 3.3.2

3、springside 安装运行步骤
   
   a.设置ant,mvn系统变量，采用springside提供的，设置mvn本地仓库，拷贝springside提供的仓库文件
   
   b.直接运行quick-start.bat

     能正常发布到本地仓库，运行好像有问题

   c.如果正常，一般能生成eclipse项目

4、手工建立eclipse项目
   
   a、建立m2_home变量
     mvn -Declipse.workspace=<path-to-eclipse-workspace> eclipse:add-maven-repo
   b、生成eclipse项目
     mvn eclipse:eclipse
     bin/eclipse.bat

   --END