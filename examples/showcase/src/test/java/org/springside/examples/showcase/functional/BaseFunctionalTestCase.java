package org.springside.examples.showcase.functional;

import java.util.Properties;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mortbay.jetty.Server;
import org.openqa.selenium.WebDriver;
import org.springside.examples.showcase.tools.Start;
import org.springside.modules.test.groups.GroupsTestRunner;
import org.springside.modules.test.utils.DbUnitUtils;
import org.springside.modules.test.utils.JettyUtils;
import org.springside.modules.test.utils.SeleniumUtils;
import org.springside.modules.utils.PropertiesUtils;
import org.springside.modules.utils.spring.SpringContextHolder;

/**
 * 功能测试基类.
 * 
 * 在整个测试期间启动一次Jetty Server, 并在每个TestCase执行前中重新载入默认数据.
 * 
 * @author calvin
 */
@Ignore
@RunWith(GroupsTestRunner.class)
public class BaseFunctionalTestCase {

	protected static final String BASE_URL = Start.BASE_URL;

	protected static Server jettyServer;

	protected static DataSource dataSourceHolder;

	protected static WebDriver driver;

	@BeforeClass
	public static void startAll() throws Exception {
		startJetty();

		fetchDataSource();
		loadDefaultData();
	}

	@AfterClass
	public static void stopAll() throws Exception {
		cleanDefaultData();
		stopJetty();
	}

	/**
	 * 启动Jetty服务器.
	 */
	protected static void startJetty() throws Exception {
		jettyServer = JettyUtils.buildTestServer(Start.PORT, Start.CONTEXT);
		jettyServer.start();
	}

	/**
	 * 关闭Jetty服务器.
	 */
	protected static void stopJetty() throws Exception {
		jettyServer.stop();
	}

	/**
	 * 取出Jetty Server内的DataSource.
	 */
	protected static void fetchDataSource() {
		dataSourceHolder = SpringContextHolder.getBean("dataSource");
	}

	/**
	 * 载入默认数据.
	 */
	protected static void loadDefaultData() throws Exception {
		DbUnitUtils.loadData(dataSourceHolder, "/data/default-data.xml");
	}

	/**
	 * 删除默认数据.
	 */
	public static void cleanDefaultData() throws Exception {
		DbUnitUtils.removeData(dataSourceHolder, "/data/default-data.xml");
	}

	/**
	 * 创建WebDriver.
	 */
	protected static void createWebDriver() throws Exception {
		Properties props = PropertiesUtils.loadProperties("classpath:/application.test.properties",
				"classpath:/application.test-local.properties");

		driver = SeleniumUtils.buildDriver(props.getProperty("selenium.driver"));
	}
}
