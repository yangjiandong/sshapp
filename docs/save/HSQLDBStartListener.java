package org.ssh.app.web;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hsqldb.Server;

//http://mht.javaeye.com/blog/165138
public class HSQLDBStartListener implements ServletContextListener {
    /**
     * 初始化
     */
    public void contextInitialized(ServletContextEvent sce) {
     String dbName = sce.getServletContext().getInitParameter("hsql.dbName");
     String path = sce.getServletContext().getInitParameter("hsql.dbPath");
     int port = -1;
     try {
      port = Integer.parseInt(sce.getServletContext().getInitParameter(
        "hsql.port"));
     } catch (Exception e) {
      port = 9999;
     }
     if (dbName == null || dbName.equals("")) {
      System.out
        .println("Cant' get hsqldb.dbName from web.xml Context Param");
      return;
     }
     File dbDir = new File(path);
     if (!dbDir.exists()) {
      if (!dbDir.mkdirs()) {
       System.out.println("Can not create DB Dir for Hsql:" + dbDir);
       return;
      }
     }
     if (!path.endsWith("/")) {
      path = path + "/";
     }
     File scriptFile = new File(path + dbName + ".script");
     File propertiesFile = new File(path + dbName + ".properties");
     if (propertiesFile.exists()) {
      this.startServer(path, dbName, port);

     } else {
      System.out
        .println("Connect failed:Connect Hsqldb error or database files not exits!");
     }


    }
    /**
     * 启动 Hsqldb 服务的方法。
     *
     * @param dbPath
     *            数据库路径
     * @param dbName
     *            数据库名称
     * @param port
     *            端口号
     */
    private void startServer(String dbPath, String dbName, int port) {
     Server server = new Server();// 它可是hsqldb.jar里面的类啊。
     server.setDatabaseName(0, dbName);
     server.setDatabasePath(0, dbPath + dbName);
     if (port != -1) {
      server.setPort(port);
     }
     server.setSilent(true);
     server.start();
     System.out.println("HSQLDB started...");

    }
   /**
     * Listener 销毁方法，在 Web 应用终止的时候执行"shutdown"命令关闭数据库.
     */
    public void contextDestroyed(ServletContextEvent arg0) {

     Connection conn = null;
     try {
      Class.forName("org.hsqldb.jdbcDriver");
      conn = DriverManager.getConnection(
        "jdbc:hsqldb:hsql://localhost:9999/mydb", "sa", "");
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("SHUTDOWN;");
     } catch (Exception e) {
      ;
     }
    }
   }