springside3
------------

2010.06.03
----------

1、spring3 + mvc out xml,json
http://stsmedia.net/spring-finance-part-7-adding-support-for-json-and-xml-views/

2010.06.02
----------

1、spring 3 mvc + rest 

配置 web.xml
<servlet>
    <servlet-name>app</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <load-on-startup>2</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>app</servlet-name>
    <url-pattern>*.do</url-pattern>
  </servlet-mapping>
--指出以.do为mvc

配置app-servlet.xml

  <!--define Spring MVC's view resource(*.jsp or other file) -->
  <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <!-- to support JSTL -->
    <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
    <!-- path of view resource files -->
    <property name="prefix" value="/views/" />
    <!-- suffix of view resource files -->
    <property name="suffix" value=".jsp" />
  </bean>

所有页面文件以.jsp为后缀，文件存放在webapp/views下

controller:
参看 UserController
@RequestMapping("/user")
    @RequestMapping(value = "/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response, User userinfo) {
        logger.info("user login..");
        logger.info(userinfo.toString());

        if (userinfo.getLoginName().equals("admin") && userinfo.getPlainPassword().equals("123")) {
            request.setAttribute("user", userinfo);
            return "users/list";//不能用/users/list,否则页面文件指到webapp/user/views/users/list.jsp
        } else {
            return "users/loginerr";
        }

    }

jsp:
  <FORM METHOD=POST ACTION="user/login.do">
    <INPUT TYPE="text" NAME="loginName" value="loginName"><br><br>
    <INPUT TYPE="text" NAME="plainPassword" value="plainPassword"><br><br>
    <INPUT TYPE="submit">
 <br>
 <A HREF="topic/add.do" target="_blank" >add</A>
      <br>
    <A HREF="topic/1234567.do" target="_blank">id:1234567</A>
 </FORM>

 --所有的连接必须以.do为后缀

2、取消do后缀，感觉do不是很美观

web.xml
  <filter-mapping>
    <filter-name>UrlRewriteFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

  <servlet-mapping>
    <servlet-name>app</servlet-name>
    <url-pattern>/app/*</url-pattern>
  </servlet-mapping>

urlrewrite.xml
所有连接都转为/app
  <rule>
    <from>/**</from>
    <to>/app/$1</to>
  </rule>
当然也要定义一些例外，如静态文件，还有remote 访问(以后有可能用到)

或：
http://www.javabloger.com/article/no-mapping-found-for-http-request-with-uri.html

<servlet-mapping>  
     <servlet-name>springmvc</servlet-name>  
     <url-pattern>/</url-pattern>  
 </servlet-mapping>   

 将默认的过滤器选项激活，世界又恢复平静，css、js、gif、jpg 等一切显示正常。
     <servlet-mapping>
         <servlet-name>default</servlet-name>
         <url-pattern>*.css</url-pattern>
     </servlet-mapping>
 
      <servlet-mapping>
         <servlet-name>default</servlet-name>
         <url-pattern>*.gif</url-pattern>
     </servlet-mapping>
    
    <servlet-mapping>
         <servlet-name>default</servlet-name>
         <url-pattern>*.jpg</url-pattern>
    </servlet-mapping>
    
    <servlet-mapping>
         <servlet-name>default</servlet-name>
         <url-pattern>*.js</url-pattern>
    </servlet-mapping>

3、mvn jetty:run
html,js文件不能编辑
Files are locked on Windows and can't be replaced
http://docs.codehaus.org/display/JETTY/Files+locked+on+Windows

   <servlet>
    <servlet-name>default</servlet-name>
    <servlet-class>org.mortbay.jetty.servlet.DefaultServlet</servlet-class>
    <init-param>
      <param-name>useFileMappedBuffer</param-name>
      <param-value>false</param-value>
    </init-param>
    <load-on-startup>0</load-on-startup>
  </servlet>

2010.05.27
----------

1、spring3.0 rest
http://stsmedia.net/spring-finance-part-2-spring-mvc-spring-30-rest-integration/

2010.05.25
----------

1、save/h2-Tutorial.pdf

2、改用c3po，取消dbcp
   pom.xml
    <dependency>
      <groupId>c3p0</groupId>
      <artifactId>c3p0</artifactId>
      <version>0.9.1.2</version>
    </dependency>

    增加hibernate-ehcache
    <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-ehcache</artifactId>
    </dependency>

3、增加logback
   pom.xml
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>0.9.21</version>
    </dependency>
    logback.xml

    save/How to setup SLF4J and LOGB..pdf

2010.05.24
----------

1、hsql 嵌入式启动
   save/HSQLDBStartListerner
   --暂时不用，h2已能自启动

2、mvn + jetty + debug + eclipse

   application/sshapp/bin/jetty-debug.bat
   sshapp-jetty-debug.launch

3、按现在状况，lift要掌握有难度，先花时间搞定ssh

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