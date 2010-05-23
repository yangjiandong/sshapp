#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.functional.security;

import org.junit.Test;
import ${package}.data.SecurityData;
import ${package}.entity.security.Role;
import ${package}.functional.BaseSeleniumTestCase;

/**
 * 角色管理的功能测试,测试页面JavaScript及主要用户故事流程.
 * 
 * @author calvin
 */
public class RoleManagerTest extends BaseSeleniumTestCase {

	/**
	 * 用户增删改操作查测试.
	 */
	@Test
	public void crudRole() {
		createRole();
		String newRoleName = editRole();
		deleteRole(newRoleName);
	}

	/**
	 * 创建用户,并返回创建的用户名.
	 */
	private void createRole() {
		clickMenu("角色列表");
		selenium.click("link=增加新角色");
		waitPageLoad();

		Role role = SecurityData.getRandomRole();

		selenium.type("name", role.getName());
		selenium.click("checkedAuthIds-1");
		selenium.click("checkedAuthIds-3");
		selenium.click("//input[@value='提交']");
		waitPageLoad();

		assertTrue(selenium.isTextPresent("保存角色成功"));
		assertEquals(role.getName(), selenium.getTable("listTable.3.0"));
		assertEquals("浏览用户, 浏览角色", selenium.getTable("listTable.3.1"));
	}

	/**
	 * 根据用户名修改对象.
	 */
	private String editRole() {
		String newRoleName = "newRoleName";

		clickMenu("角色列表");
		selenium.click("//table[@id='listTable']/tbody/tr[4]/td[3]/a[1]");
		waitPageLoad();

		selenium.type("name", newRoleName);
		selenium.click("checkedAuthIds-2");
		selenium.click("checkedAuthIds-3");
		selenium.click("//input[@value='提交']");
		waitPageLoad();

		assertTrue(selenium.isTextPresent("保存角色成功"));
		assertEquals(newRoleName, selenium.getTable("listTable.3.0"));
		assertEquals("浏览用户, 修改用户", selenium.getTable("listTable.3.1"));

		return newRoleName;
	}

	/**
	 * 根据用户名删除对象.
	 */
	private void deleteRole(String roleName) {
		clickMenu("角色列表");
		selenium.click("//table[@id='listTable']/tbody/tr[4]/td[3]/a[2]");
		waitPageLoad();

		assertTrue(selenium.isTextPresent("删除角色成功"));
		assertFalse(selenium.isTextPresent(roleName));
	}
}
