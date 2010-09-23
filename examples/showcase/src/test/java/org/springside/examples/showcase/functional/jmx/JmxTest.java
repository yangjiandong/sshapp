package org.springside.examples.showcase.functional.jmx;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.springside.examples.showcase.functional.BaseFunctionalTestCase;

/**
 * JMX演示页面内容简单测试.
 * 
 * @author calvin
 */
public class JmxTest extends BaseFunctionalTestCase {

	@BeforeClass
	public static void startWebDriver() throws Exception {
		createWebDriver();
	}

	@Test
	public void test() {
		driver.get(BASE_URL + "/jmx/jmx-client.action");
		assertEquals("default", driver.findElement(By.id("nodeName")).getValue());
	}
}
