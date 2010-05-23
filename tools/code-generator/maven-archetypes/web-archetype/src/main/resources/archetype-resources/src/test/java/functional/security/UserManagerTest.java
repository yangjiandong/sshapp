#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.functional.security;

import org.junit.Test;
import ${package}.data.SecurityData;
import ${package}.entity.security.User;
import ${package}.functional.BaseSeleniumTestCase;
import org.springside.modules.test.groups.Groups;

/**
 * 用户管理的功能测试, 测试页面JavaScript及主要用户故事流程.
 * 
 * @author calvin
 */
public class UserManagerTest extends BaseSeleniumTestCase {

	/**
	 * 用户增删改操作查测试.
	 */
	@Test
	public void crudUser() {
		String loginName = createUser();
		editUser(loginName);
		deleteUser(loginName);
	}

	/**
	 * 创建用户时的输入校验测试. 
	 */
	@Test
	@Groups("extension")
	public void validateUser() {
		clickMenu("帐号列表");
		selenium.click("link=增加新用户");
		waitPageLoad();

		selenium.type("loginName", "admin");
		selenium.type("name", "");
		selenium.type("password", "a");
		selenium.type("passwordConfirm", "abc");
		selenium.type("email", "abc.com");
		selenium.click("//input[@value='提交']");

		selenium.waitForCondition("selenium.isTextPresent('用户登录名已存在')", "5000");
		assertTrue(selenium.isTextPresent("用户登录名已存在"));
		assertEquals("必选字段", selenium.getTable("//form[@id='inputForm']/table.1.1"));
		assertEquals("请输入一个长度最少是 3 的字符串", selenium.getTable("//form[@id='inputForm']/table.2.1"));
		assertEquals("输入与上面相同的密码", selenium.getTable("//form[@id='inputForm']/table.3.1"));
		assertEquals("请输入正确格式的电子邮件", selenium.getTable("//form[@id='inputForm']/table.4.1"));

	}

	/**
	 * 创建用户,并返回创建的用户名.
	 */
	private String createUser() {
		clickMenu("帐号列表");
		selenium.click("link=增加新用户");
		waitPageLoad();

		User user = SecurityData.getRandomUser();
		String loginName = user.getLoginName();

		selenium.type("loginName", user.getLoginName());
		selenium.type("name", user.getName());
		selenium.type("password", user.getPassword());
		selenium.type("passwordConfirm", user.getPassword());
		selenium.click("checkedRoleIds-2");
		selenium.click("//input[@value='提交']");
		waitPageLoad();

		assertTrue(selenium.isTextPresent("保存用户成功"));

		findUser(loginName);
		assertEquals(loginName, selenium.getTable("listTable.1.1"));
		assertEquals("用户", selenium.getTable("listTable.1.3"));
		return loginName;
	}

	/**
	 * 根据用户名修改对象.
	 */
	private void editUser(String loginName) {
		String newUserName = "newUserName";

		clickMenu("帐号列表");
		findUser(loginName);

		selenium.click("link=修改");
		waitPageLoad();

		selenium.type("name", newUserName);
		//取消用户角色,增加管理员角色
		selenium.click("checkedRoleIds-1");
		selenium.click("checkedRoleIds-2");
		selenium.click("//input[@value='提交']");
		waitPageLoad();

		assertTrue(selenium.isTextPresent("保存用户成功"));
		findUser(loginName);
		assertEquals(newUserName, selenium.getTable("listTable.1.1"));
		assertEquals("管理员", selenium.getTable("listTable.1.3"));
	}

	/**
	 * 根据用户名删除对象.
	 */
	private void deleteUser(String loginName) {
		clickMenu("帐号列表");
		findUser(loginName);

		selenium.click("link=删除");
		waitPageLoad();

		assertTrue(selenium.isTextPresent("删除用户成功"));
		findUser(loginName);
		assertTrue(selenium.isTextPresent("共0页"));
	}

	/**
	 * 根据用户名查找用户的Utils函数. 
	 */
	private void findUser(String loginName) {
		selenium.type("filter_EQS_loginName", loginName);
		selenium.click("//input[@value='搜索']");
		waitPageLoad();
	}
}
