package org.springside.examples.showcase.functional;

import java.util.Properties;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.mortbay.jetty.Server;
import org.openqa.selenium.WebDriver;
import org.springside.examples.showcase.tools.Start;
import org.springside.modules.test.utils.DbUnitUtils;
import org.springside.modules.test.utils.JettyUtils;
import org.springside.modules.test.utils.SeleniumUtils;
import org.springside.modules.utils.PropertyUtils;
import org.springside.modules.utils.SpringContextHolder;

/**
 * 功能测试基类.
 * 
 * 在整个测试期间启动一次Jetty Server, 并在每个TestCase执行前中重新载入默认数据.
 * 
 * @author calvin
 */
@Ignore
public class BaseFunctionalTestCase extends Assert {

	protected static final String BASE_URL = Start.BASE_URL;

	protected static Server server;

	protected static DataSource dataSource;

	protected static WebDriver driver;

	@BeforeClass
	public static void init() throws Exception {
		if (server == null) {
			startJetty();
			fetchDataSource();
		}

		loadDefaultData();
	}

	/**
	 * 删除默认数据.
	 */
	@AfterClass
	public static void cleanDefaultData() throws Exception {
		DbUnitUtils.removeData(dataSource, "/data/default-data.xml");
	}

	/**
	 * 启动Jetty服务器
	 */
	protected static void startJetty() throws Exception {
		server = JettyUtils.buildTestServer(Start.PORT, Start.CONTEXT);
		server.start();
	}

	/**
	 * 取出Jetty Server内的DataSource.
	 */
	protected static void fetchDataSource() {
		dataSource = SpringContextHolder.getBean("dataSource");
	}

	/**
	 * 载入默认数据.
	 */
	protected static void loadDefaultData() throws Exception {
		DbUnitUtils.loadData(dataSource, "/data/default-data.xml");
	}

	/**
	 * 创建WebDriver.
	 */
	protected static void createWebDriver() throws Exception {
		Properties props = PropertyUtils.loadProperties("classpath:/application.test.properties",
				"classpath:/application.test-local.properties");

		driver = SeleniumUtils.buildDriver(props.getProperty("selenium.driver"));
	}
}
