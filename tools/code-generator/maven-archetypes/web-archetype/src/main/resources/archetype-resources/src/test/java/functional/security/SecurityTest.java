#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.functional.security;

import org.junit.Test;
import ${package}.functional.BaseSeleniumTestCase;

/**
 * 系统安全控制的功能测试, 测试主要用户故事.
 * 
 * @author calvin
 */
public class SecurityTest extends BaseSeleniumTestCase {

	/**
	 * 测试匿名用户访问系统时的行为.
	 */
	@Test
	public void checkAnonymous() {
		//访问退出登录页面,退出之前的登录
		selenium.open("/${artifactId}/j_spring_security_logout");
		assertTrue(selenium.isElementPresent("//input[@value='登录']"));

		//访问任意页面会跳转到登录界面
		selenium.open("/${artifactId}/security/user.action");
		assertTrue(selenium.isElementPresent("//input[@value='登录']"));
	}

	/**
	 * 访问只有用户角色的用户访问系统时的受限行为.
	 */
	@Test
	public void checkUserRole() {
		//访问退出登录页面,退出之前的登录
		selenium.open("/${artifactId}/j_spring_security_logout");
		assertTrue(selenium.isElementPresent("//input[@value='登录']"));

		//登录普通用户
		selenium.type("j_username", "user");
		selenium.type("j_password", "user");
		selenium.click("//input[@value='登录']");
		waitPageLoad();

		//校验用户角色的操作单元格为空
		clickMenu("帐号列表");
		waitPageLoad();
		assertEquals("查看", selenium.getTable("listTable.1.4"));

		clickMenu("角色列表");
		waitPageLoad();
		assertEquals("查看", selenium.getTable("listTable.1.2"));
	}
}
