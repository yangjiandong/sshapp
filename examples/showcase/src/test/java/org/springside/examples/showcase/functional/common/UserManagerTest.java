package org.springside.examples.showcase.functional.common;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.springside.examples.showcase.functional.BaseFunctionalTestCase;
import org.springside.modules.test.utils.SeleniumUtils;

/**
 * 用户管理的功能测试.
 * 
 * @author calvin
 */
public class UserManagerTest extends BaseFunctionalTestCase {
	/** 通用验证码,见web.xml中的设置.*/
	private static final String AUTOPASS_CAPTCHA = "RPWT54321";

	@BeforeClass
	public static void startWebDriver() throws Exception {
		createWebDriver();
	}

	@AfterClass
	public static void stopWebDriver() {
		driver.close();
	}

	@Test
	public void editUser() {
		driver.get(BASE_URL);
		driver.findElement(By.linkText("综合演示")).click();
		driver.findElement(By.id("editLink-2")).click();

		//修改用户需要登录管理员权限
		SeleniumUtils.type(driver.findElement(By.name("j_username")), "admin");
		SeleniumUtils.type(driver.findElement(By.name("j_password")), "admin");
		SeleniumUtils.type(driver.findElement(By.name("j_captcha")), AUTOPASS_CAPTCHA);
		driver.findElement(By.xpath("//input[@value='登录']")).click();
		//点击提交按钮
		SeleniumUtils.type(driver.findElement(By.name("name")), "user_foo");
		driver.findElement(By.xpath("//input[@value='提交']")).click();
		//重新进入用户修改页面, 检查最后修改者
		driver.findElement(By.id("editLink-2")).click();
		System.out.println(driver.findElement(By.tagName("body")).getText());
		assertTrue(SeleniumUtils.isTextPresent(driver, "最后修改:admin"));
	}
}
