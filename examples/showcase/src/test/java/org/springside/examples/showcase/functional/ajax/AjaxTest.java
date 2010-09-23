package org.springside.examples.showcase.functional.ajax;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.springside.examples.showcase.functional.BaseFunctionalTestCase;
import org.springside.modules.test.utils.SeleniumUtils;

/**
 * 测试Ajax Mashup.
 * 
 * @calvin
 */
public class AjaxTest extends BaseFunctionalTestCase {

	@BeforeClass
	public static void startWebDriver() throws Exception {
		createWebDriver();
	}

	@AfterClass
	public static void stopWebDriver() {
		driver.close();
	}

	@Test
	public void mashup() {
		driver.get(BASE_URL);
		driver.findElement(By.linkText("Ajax演示")).click();
		driver.findElement(By.linkText("跨域Mashup演示")).click();

		driver.findElement(By.xpath("//input[@value='获取内容']")).click();
		SeleniumUtils.waitForDisplay(driver.findElement(By.id("mashupContent")), 5000);
		assertTrue(SeleniumUtils.isTextPresent(driver, "Hello World!"));
	}
}
