springside3
------------

2011.08.08
----------

   1. 解决 jetty+maven+el 问题
   http://blog.flurdy.com/2010/07/jetty-tomcat-jsp.html
   save/jetty.maven.el.txt

   modules/parent/pom.xml

2011.07.30
----------

1、建立branch 3.3.4
   git branch 3.3.4
   git push origin 3.3.4

2011.04.28
----------

1. update core hibernateDao,page,propertyFilter

2011.04.05
----------

1. 单页面测试(opoa)

   view/index.jsp
<!--
<script type="text/javascript" src="<c:url value='/resources/js/main.js' />"></script>
 -->
<script type="text/javascript" src="<c:url value='/resources/js/opoa.main.js' />"></script>

   resources/js/opoa.main.js
   resources/js/modules
   resources/data

2. jRebel

   -Xms768m -Xmx768m -XX:PermSize=128m -XX:MaxPermSize=256m
   -Drebel.log=true -noverify -javaagent:c:\jrebel.jar

   -Drebel.log=true -noverify -javaagent:c:\jrebel.jar -Xmx512M -Xms512M -XX:MaxPermSize=1024m

   测试 jrebel 3.5 在本项目中应用,有效.

   mvn jetty
   配置环境变量
   set REBEL_HOME=d:\jrebel
   set MAVEN_OPTS=-noverify -javaagent:%REBEL_HOME%\jrebel.jar -Xmx1024m -XX:MaxPermSize=256m
   call mvn -o jetty:run -Dmaven.test.skip=true
   配置jetty不自动扫描代码变化
　 jetty-mavn-plugin的 plugin > configuration > scanIntervalSeconds 设置为0

2011.03.30
----------

1. 暂时关闭spring-core 测试 ThreadUtilsTest.normalShutdown

   assertEquals("InterruptedException", appender.getFirstLog().getMessage());

   在重新导出环境下测试通不过。

2. 自定义表测试

   BookController.showExamples2 --> BookService.getExamples2

2011.03.26
----------

1. 重新整理ehcache 资料

   关闭hibernate 缓存

   ssh.txt
   1、手工配置spring 方法缓存
   resources/cache/applicationContext-ehcache.xml
   --需手工指定bean及方法
   2、增加CacheUtil
   手工处理ehcache

   以下代码不全,暂时采用原有代码
   http://agun.javaeye.com/blog/741519

   --使用
   a. 直接使用CacheUtil.getCache
   b. 匹配方法
   c. InstantEhCacheManager,FixEhCacheManager,MethodEhCachedManger与 CacheUtil.getCache 类似

2011.03.23
----------

1. annotation
http://www.infoq.com/cn/articles/cf-java-annotation
org.ssh.app.util.annotation.RequiredRoles
BookController.showBooks33

2011.03.22
----------

1. textile wiki quick
save/Textile Quick Reference.htm
相关资料:
http://redcloth.org/hobix.com/
http://textile.thresholdstate.com/

2011.03.21
----------

1. com.thimbleware.jmemcache
java 版 memcache

http://code.google.com/p/jmemcache-daemon/
    <dependency>
        <groupId>com.thimbleware.jmemcached</groupId>
        <artifactId>jmemcached-core</artifactId>
        <version>1.0.0</version>
    </dependency>

主要修改extension模块中 JmemcachedServer

TODO
  启动参数还要看一下

2. sshapp 增加混淆 build.xml 任务,好像只支持jar混淆
save/yguard_ant_howto.html

3. 以前 wikitext2pdf 不成功
   少了 pdf 生成任务,
   现采用 wikitext-to-xslfo ,再用 apache fop
   save/fop.anttask.pdf
   中文字体: http://wangxc.javaeye.com/blog/598912
   save/fop.txt

   fo 文件中文字体暂时处理方法:
      <root font-family="simhei" xmlns="http://www.w3.org/1999/XSL/Format">
      or
      <root font-family="YaHeiConsolasHybrid" xmlns="http://www.w3.org/1999/XSL/Format">

http://peterfriese.wordpress.com/page/2/

2011.03.20
----------
Hibernate映射有用的策略--公用属性类
文章分类:Java编程

有时候我们有这种需求，即，很多类有相似的属性，因此需要一个继承方式来描述这些关系，而这些公用的属性我们只想把他用OO的方式表现出来方便组织管理，这个公用的类没有业务属性，更不是业务父类这个时候就需要这种方式了
@MappedSuperclass
public class BaseEntity {
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastUpdate() { ... }
    public String getLastUpdater() { ... }
    ...
}

@Entity class Order extends BaseEntity {
    @Id public Integer getId() { ... }
    ...
}

 @MappedSuperclass可以满足我们这种需求

注意1:没有用这注解注解的父类属性将不在持久化管理之内，换句话说就是只有用这个注解描述的类的属性才能持久化

2011.03.18
----------

1. update spring security to 3.0.5
>
cd modules
cd parent
mvn clean
mvn install
cd ..
cd core
mvn clean
mvn install

2011.03.17
----------

1. 参考spring mvc-showcase 使用jquery
   book/form.jsp,fileupload.jsp

2. JavaScript 实现的 AES 加、解密算法
   http://www.movable-type.co.uk/scripts/aes.html
   js/lib/AES.js

   example:
   var password = 'L0ck it up saf3';
   var plaintext = 'pssst ... don't tell anyone';
   var ciphertext = Aes.Ctr.encrypt(plaintext, password, 256);
   var origtext = Aes.Ctr.decrypt(ciphertext, password, 256);

3. jquery

   cool jquery grid
   http://www.trirand.com/blog/jqgrid/jqgrid.html
   https://github.com/tonytomov/jqGrid

   easyui
   http://jquery-easyui.wikidot.com/

2011.03.16
----------

1. json 方案
   Jackson > Gson > Json-lib

2. tomcat + eclipse + maven
   save/tomcat.maven.eclipse.txt

   注意,需取消 pom.xml,但用jetty时需要
    <dependency>
      <groupId>org.apache.tomcat</groupId>
      <artifactId>jasper-el</artifactId>
      <version>6.0.26</version>
    </dependency>

    对 tilesConfigurer 有影响,不知jetty下是否正常

    how to set mvn output dir

    <outputDirectory>src/main/webapp/WEB-INF/classes</outputDirectory>

      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-eclipse-plugin</artifactId>
        <version>2.8</version>
        <configuration>
          <sourceExcludes>
            <sourceExclude>**/.svn/</sourceExclude>
          </sourceExcludes>
          <downloadSources>true</downloadSources>
          <buildOutputDirectory>src/main/webapp/WEB-INF/classes</buildOutputDirectory>
        </configuration>
      </plugin>

   --在不新增方法、不导入新类的情况下，该调试方法比较好用


2011.03.12
----------

1. Access restriction: ... not accessible due to restriction on required library ..\jre\lib\rt.jar

Windows -> Preferences -> Java -> Compiler -> Errors/Warnings -> Deprecated and trstricted API -> Forbidden reference (access rules): -> change to warning

2. test groovy sql(tools/snippets/groovy.txt)
这里要重点介绍的是另外一种快捷增加记录的方法--DataSet。
DataSet将SQL语句隐藏，将数据集放入到一个Map中。可以对这个Map中内容进行查询、增加。请看如下代码：
def blogs=db.dataSet('blog') /*new DataSet(db, 'blog')或者db.dataSet(Blog)*/
blogs.each{ println it }
blogs.add(
    content:'dateset using',
    author_id:3,
    date_created:new Date())
blogs.findAll {it.id>1 }.sort{it.version desc}.each { println it }

从上述代码中可以看到，创建DateSet实例后，就能够获得一个Map，可以对这个Map执行findAll(whereClosure)以及sort(sortClosure)。
而这里增加数据使用的是add(Map)方法。

在对数据库进行增加、更新或者删除时，需要考虑事务性，以保证数据的完整性。
对于此，Groovy同样提供了一个非常方便的用法。对于需要在同一个事务中完成的代码，可以使用Sql或者DataSet的withTransaction(Closure)方法实现，参见如下代码：

db.withTransaction{
    for (int i=0;i<5;i++){
        blogs.add(
        content:"dateset using"+i,
        author_id:3,
        date_created:new Date())
    }
    db.execute ("delete from iblog where i>20")
}

2011.03.10
----------

1. update blueprint css

2011.03.06
----------

1. change to servlet2.5

   web.xml

2. add camel ?


2011.02.17
----------

1. save/groovy.txt

2. hibernate 保存表操作历史
http://jeffreyhsu.javaeye.com/blog/191696

hibernate事件:http://www.nautsch.net/2008/05/16/hibernate-eventlisteners-in-spring-konfigurieren/
save/hibernate-even.txt
org/ssh/app/orm/hibernate/AbstractEntity,Historizable,HistoryListener,OperationType,entity
example: example../Category

2011.02.16
----------

1. groovysh in cygwin
running groovysh with cygwin. If you have troubles, the following may help:

stty -icanon min 1 -echo
groovysh --terminal=unix
stty icanon echo

2011.02.15
----------

1. groovy 脚本测试

  <lang:groovy id="pdfGenerator"
    script-source="${groovy.script.dir}GroovyPdfGenerator.groovy"
    refresh-check-delay="1000"
    customizer-ref="performanceLoggingCustomizer">

    <lang:property name="jdbcDriver" value="${import.jdbc.driver}" />
    <lang:property name="jdbcUrl" value="${import.jdbc.url}" />
    <lang:property name="jdbcUser" value="${import.jdbc.username}" />
    <lang:property name="jdbcPassword" value="${import.jdbc.password}" />

    <lang:property name="companyName" value="Really Groovy Bookstore"/>
  </lang:groovy>

   grails-spring slf4j1.5.8 与项目有冲突

   测试groovy简单执行sql
   json支持还需找例子

   // Create a Spring application context object
    def ctx = new ClassPathXmlApplicationContext("RentABike-context.xml")

    //Ask Spring for an instance of CommandLineView, with a
    //Bike store implementation set by Spring
    def clv = ctx.getBean("commandLineView")

   test:
   curl http://localhost:8089/sshapp/book/getBooks33

2011.02.14
----------

1. add groovy 1.7.5

2011.02.09
----------

1. spring 3.0.5

2. Database Change Management
http://www.liquibase.org/

wiki/liquibase.textile,save/SpringLiquibase.java.txt
ant,mvn,servlet

integration spring:

    <changeSet id="1" author="bob">
        <createTable tableName="department">
            <column name="id" type="int">
                <constraints primaryKey="true" nullable="false"/>
            </column>
            <column name="name" type="varchar(50)">
                <constraints nullable="false"/>
            </column>
            <column name="active" type="boolean" defaultValueBoolean="true"/>
        </createTable>
    </changeSet>

    pom.xml add liquibase

--建立mvn
cd save
mvn install:install-file -Dfile=liquibase-2.0.1.jar -DgroupId=com.database.liquibase -DartifactId=liquibase -Dversion=2.0.1 -Dpackaging=jar -DgeneratePom=true

处理 Waiting for changelog lock..
delete from DATABASECHANGELOGLOCK
insert DATABASECHANGELOGLOCK(id,locked) values(1,0)

--自己建bean
http://www.insaneprogramming.be/?p=56

other tool:
http://scriptella.javaforge.com/

3. springside3-extension 增加 org.springside.modules.charts

4. wiki

create new file, .textile

2010.08.01
----------

1、hjpetstore
http://hi.baidu.com/quest2run/blog/item/2526f64672654a2bcefca3a4.html

use cvs client command line
On client replace username with your java.net account (registration free):
cvs -d :pserver:username@cvs.dev.java.net:/cvs login
Then:
cvs -d :pserver:username@cvs.dev.java.net:/cvs checkout hjpetstore/hjpetstore2

The following technologies are being used in the current implementation:

    * JQuery fisheye
    * kaptcha
    * Spring MVC 3
    * Spring Security
    * Hibernate 3.5
    * JBoss Cache 3 (alternative infinispan)
    * JMS External Integration
    * GlassFish 3 cluster
    * Mysql fail-over and cluster
    * Zabbix / Zapcat

hjpetstore-mysql-populate.sql脚本需更改证
save/hjpetstore-mysql-populate.sql

--很奇怪，家里的环境mvn下载不了所需包，必须设置proxy
    <proxy>
      <id>optional</id>
      <active>true</active>
      <protocol>http</protocol>
      <username>proxyuser</username>
      <password>proxypass</password>
      <host>localhost</host>
      <port>2010</port>
      <nonProxyHosts>repository.jboss.org|repo1.maven.org|download.java.net</nonProxyHosts>
    </proxy>

--关注它的RestTemplate
SecurityServiceRestClientImpl

2、glassfish v3

   --启动默认domain1
   as-install/bin/asadmin start-domain

   as-install/bin/asadmin stop-domain

   --java db
   as-install/bin/asadmin start-database --dbhome directory-path

   For example, to start the Java DB server from its default location:
   as-install/bin/asadmin start-database --dbhome as-install-parent/javadb

   --相关配置
   -Dhttp.maxConnections=250

   1、在 将:-Xmx512 和 -client 这2个参数  修改
      为这几个参数 -server ,-XX:+AggressiveHeap, -Xmx2048m, -Xms2048m, -Xss128k, -XX:+DisableExplicitGC，
      不要写在一行中要分开写成几行，
      另外，如果在64位的JMV中你的内存使用范围需要设置到  -Xmx4096m -Xms4096m 才会起到效果，
      也就是64位的运行环境说需要设置超过4个G的大小才会得到效果，
      在windows 32位的环境下，一般 1.4G 到 1.6G是一个比较稳健的值，2G是一个极限。
      而在Solaris的SPARC CPU环境下尽量控制在1400M范围以内。

   2、如果在多个CPU上运行GlassFish V3，还需要添加这2个参数，增大并行运算的处理能力：
      -XX:ParallelGCThreads=N   (其中N为CPU的数量，如果N>8 ，N=CPU数x2)
      -XX:+UseParallelOldGC
      -XX:LargePageSizeInBytes=256m

   3、如果你使用的是JDK版本6u16或者更高的64位的JVM环境则需要加入：
      -XX:+UseCompressedOops 参数 .
      另外,需要注意设置Java堆的最大值跟你的操作系统系统有一定的关系，
      linux/solairs/windows 、32位或者64位的环境需要设置的参数都不一样，
      所以应该根据具体的操作系统环境设置不同的参数


2010.06.07
----------

1、json-lib 对数据datatime 转换有问题，取消表date字段属性，采用string
net.sf.json.JSONException: java.lang.reflect.InvocationTargetException

2、Hibernate中缓存的统计策略 -generate_statistics

  http://blog.163.com/qqabc20082006@126/blog/static/2292852520091120112719561/

  // 创建Statistics对象，并通过SessionFactory对象获得统计信息
  Statistics st =  sf.getStatistics();
  // 打印全部统计信息
  System.out.println(st);
  // 打印二级缓存信息
  System.out.println(st.getSecondLevelCacheHitCount());

2010.06.05
----------

1、ubuntu10.4下建立开发环境

   a.jetty.sh 中设置 MAVEN_OPS 没成功，只能在命令行中用
   mvn -o jetty:run -Djetty.port=8089 -Dmaven.test.skip=true -Dmaven.findbugs.jvmargs=-Xmx512m

   b.将mvn仓库设到d盘，
   先查看具体位置
   gedit /etc/fstab
   设置mvn/conf/setting.xml
   <localRepository>/media/54485D16CE623524/HOME/local/repo</localRepository>

   c.eclipse
gedit ~/.gtkrc-2.0
style "gtkcompact" {
GtkButton::default_border={0,0,0,0}
GtkButton::default_outside_border={0,0,0,0}
GtkButtonBox::child_min_width=0
GtkButtonBox::child_min_heigth=0
GtkButtonBox::child_internal_pad_x=0
GtkButtonBox::child_internal_pad_y=0
GtkMenu::vertical-padding=1
GtkMenuBar::internal_padding=0
GtkMenuItem::horizontal_padding=4
GtkToolbar::internal-padding=0
GtkToolbar::space-size=0
GtkOptionMenu::indicator_size=0
GtkOptionMenu::indicator_spacing=0
GtkPaned::handle_size=4
GtkRange::trough_border=0
GtkRange::stepper_spacing=0
GtkScale::value_spacing=0
GtkScrolledWindow::scrollbar_spacing=0
GtkTreeView::vertical-separator=0
GtkTreeView::horizontal-separator=0
GtkTreeView::fixed-height-mode=TRUE
GtkWidget::focus_padding=0
}
class "GtkWidget" style "gtkcompact"

2、ubuntu下 图标 网络连接  消失 解决办法

cd /etc
cd NetworkManager
sudo gedit nm-system-settings.conf
把 里面的 false 改成 true.

停止 NetworkManager
sudo /etc/init.d/network-manager stop
重新啟動 NetworkManager
sudo /etc/init.d/network-manager start

3、没体会到spring3 rest 风格，简单的连接测试成功，

  采用json返回的测试没成功，只能采用以前的方式，
  如 http://loianegroner.com/tag/json-lib-ext-spring/，方式与公司采用的拼字符输出类似
  --JsonController.java
  --test: curl -i -X GET http://localhost:8089/sshapp/jsons/getBooks

  需要解决的是dwr如何用restful方式替代

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

   $ ssh-keygen
    (ssh-keygen -C "你的email地址" -t rsa)
    Generating public/private rsa key pair.
    Enter file in which to save the key (/Users/schacon/.ssh/id_rsa):
    Enter passphrase (empty for no passphrase):
    Enter same passphrase again:
    Your identification has been saved in /Users/schacon/.ssh/id_rsa.
    Your public key has been saved in /Users/schacon/.ssh/id_rsa.pub.
    The key fingerprint is:
    43:c5:5b:5f:b1:f1:50:43:ad:20:a6:92:6a:1f:9a:3a schacon@agadorlaptop.local

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

5、clone

   git clone git://github.com/yangjiandong/sshapp.git sshapp


   --END
