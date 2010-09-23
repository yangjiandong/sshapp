package org.springside.examples.miniweb.functional.account;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springside.examples.miniweb.data.AccountData;
import org.springside.examples.miniweb.entity.account.Authority;
import org.springside.examples.miniweb.entity.account.Role;
import org.springside.examples.miniweb.functional.BaseFunctionalTestCase;
import org.springside.examples.miniweb.functional.Gui;
import org.springside.examples.miniweb.functional.Gui.RoleColumn;
import org.springside.modules.test.utils.DataUtils;
import org.springside.modules.test.utils.SeleniumUtils;

/**
 * 角色管理的功能测试,测试页面JavaScript及主要用户故事流程.
 * 
 * @author calvin
 */
public class RoleManagerTest extends BaseFunctionalTestCase {

	private static Role testRole = null;

	/**
	 * 检验OverViewPage.
	 */
	@Test
	public void overviewPage() {
		driver.findElement(By.linkText(Gui.MENU_ROLE)).click();
		WebElement table = driver.findElement(By.xpath("//table[@id='contentTable']"));
		assertEquals("管理员", SeleniumUtils.getTable(table, 1, RoleColumn.NAME.ordinal()));
		assertEquals("浏览用户, 修改用户, 浏览角色, 修改角色", SeleniumUtils.getTable(table, 1, RoleColumn.AUTHORITIES.ordinal()));
	}

	/**
	 * 创建公共测试角色.
	 */
	@Test
	public void createRole() {
		driver.findElement(By.linkText(Gui.MENU_ROLE)).click();
		driver.findElement(By.linkText("增加新角色")).click();

		//生成测试数据
		Role role = AccountData.getRandomRoleWithAuthority();

		//输入数据
		SeleniumUtils.type(driver.findElement(By.id("name")), role.getName());
		for (Authority authority : role.getAuthorityList()) {
			driver.findElement(By.id("checkedAuthIds-" + authority.getId())).setSelected();
		}
		driver.findElement(By.xpath(Gui.BUTTON_SUBMIT)).click();

		//校验结果
		assertTrue(SeleniumUtils.isTextPresent(driver, "保存角色成功"));
		verifyRole(role);

		//设置公共测试角色
		testRole = role;
	}

	/**
	 * 修改公共测试角色.
	 */
	@Test
	public void editRole() {
		ensureTestRoleExist();
		driver.findElement(By.linkText(Gui.MENU_ROLE)).click();
		driver.findElement(By.id("editLink-" + testRole.getName())).click();

		testRole.setName(DataUtils.randomName("Role"));
		SeleniumUtils.type(driver.findElement(By.id("name")), testRole.getName());

		for (Authority authority : testRole.getAuthorityList()) {
			SeleniumUtils.uncheck(driver.findElement(By.id("checkedAuthIds-" + authority.getId())));
		}
		testRole.getAuthorityList().clear();

		List<Authority> authorityList = AccountData.getRandomDefaultAuthorityList();
		for (Authority authority : authorityList) {
			driver.findElement(By.id("checkedAuthIds-" + authority.getId())).setSelected();
		}
		testRole.getAuthorityList().addAll(authorityList);

		driver.findElement(By.xpath(Gui.BUTTON_SUBMIT)).click();

		assertTrue(SeleniumUtils.isTextPresent(driver, "保存角色成功"));
		verifyRole(testRole);
	}

	/**
	 * 删除测试角色.
	 */
	@Test
	public void deleteRole() {
		ensureTestRoleExist();
		driver.findElement(By.linkText(Gui.MENU_ROLE)).click();

		driver.findElement(By.id("deleteLink-" + testRole.getName())).click();

		assertTrue(SeleniumUtils.isTextPresent(driver, "删除角色成功"));
		assertFalse(SeleniumUtils.isTextPresent(driver, testRole.getName()));

		testRole = null;
	}

	@SuppressWarnings("unchecked")
	private void verifyRole(Role role) {
		driver.findElement(By.id("editLink-" + role.getName())).click();

		assertEquals(role.getName(), driver.findElement(By.id("name")).getValue());

		for (Authority authority : role.getAuthorityList()) {
			assertTrue(driver.findElement(By.id("checkedAuthIds-" + authority.getId())).isSelected());
		}

		List<Authority> uncheckAuthList = ListUtils.subtract(AccountData.getDefaultAuthorityList(), role
				.getAuthorityList());
		for (Authority authority : uncheckAuthList) {
			assertFalse(driver.findElement(By.id("checkedAuthIds-" + authority.getId())).isSelected());
		}
	}

	/**
	 * 确保公共测试角色已初始化的工具函数.
	 */
	private void ensureTestRoleExist() {
		if (testRole == null) {
			createRole();
		}
	}
}
