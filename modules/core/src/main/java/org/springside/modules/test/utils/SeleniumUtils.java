/*
 * $HeadURL: https://springside.googlecode.com/svn/springside3/trunk/modules/core/src/main/java/org/springside/modules/test/utils/SeleniumUtils.java $
 * $Id: SeleniumUtils.java 1141 2010-07-31 17:54:22Z calvinxiu $
 * Copyright (c) 2010 by Ericsson, all rights reserved.
 */

package org.springside.modules.test.utils;

import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.RenderedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Selenium工具类.
 *
 * @author calvin
 */
public class SeleniumUtils {

	public static final String HTMLUNIT = "htmlunit";

	public static final String FIREFOX = "firefox";

	public static final String IE = "ie";

	public static final String REMOTE = "remote";

	private static Logger logger = LoggerFactory.getLogger(SeleniumUtils.class);

	public static WebDriver buildDriver(String driverName) throws Exception {
		WebDriver driver = null;

		if (HTMLUNIT.equals(driverName)) {
			driver = new HtmlUnitDriver();
			((HtmlUnitDriver) driver).setJavascriptEnabled(true);
		}

		if (FIREFOX.equals(driverName)) {
			driver = new FirefoxDriver();
		}

		if (IE.equals(driverName)) {
			driver = new InternetExplorerDriver();
		}

		if (driverName.startsWith(REMOTE)) {
			String[] params = driverName.split(":");
			Assert.isTrue(params.length == 3,
					"Remote driver is not right, accept format is \"remote:local:firefox\", but the input is\""
							+ driverName + "\"");

			String remoteAddress = params[1];
			String driverType = params[2];
			DesiredCapabilities cap = null;
			if (FIREFOX.equals(driverType)) {
				cap = DesiredCapabilities.firefox();
			}

			if (IE.equals(driverType)) {
				cap = DesiredCapabilities.internetExplorer();
			}

			driver = new RemoteWebDriver(new URL("http://" + remoteAddress + ":3000/wd"), cap);
		}

		Assert.notNull(driver, "No driver could be found by name:" + driverName);

		return driver;
	}

	/**
	 * 兼容Selnium1.0的常用函数.
	 */
	public static boolean isTextPresent(WebDriver driver, String text) {
		return StringUtils.contains(driver.findElement(By.tagName("body")).getText(), text);
	}

	/**
	 * 兼容Selnium1.0的常用函数.
	 */
	public static void type(WebElement element, String text) {
		element.clear();
		element.sendKeys(text);
	}

	/**
	 * 兼容Selnium1.0的常用函数.
	 */
	public static void uncheck(WebElement element) {
		if (element.isSelected()) {
			element.toggle();
		}
	}

	/**
	 * 兼容Selnium1.0的常用函数, 序列从0开始.
	 */
	public static String getTable(WebElement table, int rowIndex, int columnIndex) {
		return table.findElement(By.xpath("//tr[" + (rowIndex + 1) + "]//td[" + (columnIndex + 1) + "]")).getText();
	}

	/**
	 * 兼容Selnium1.0的常用函数, timeout单位为毫秒.
	 */
	public static void waitForDisplay(WebElement element, int timeout) {
		long timeoutTime = System.currentTimeMillis() + timeout;
		while (System.currentTimeMillis() < timeoutTime) {
			if (((RenderedWebElement) element).isDisplayed()) {
				return;
			}
		}
		logger.warn("waitForDisplay timeout");
	}
}
