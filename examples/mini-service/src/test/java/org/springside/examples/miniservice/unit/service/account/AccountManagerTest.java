package org.springside.examples.miniservice.unit.service.account;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springside.examples.miniservice.dao.account.UserDao;
import org.springside.examples.miniservice.service.account.AccountManager;

/**
 * AccountManager的单元测试用例, 测试Service层的业务逻辑.
 * 
 * 使用EasyMock对UserDao进行模拟.
 * 
 * @author calvin
 */
public class AccountManagerTest extends Assert {

	private AccountManager accountManager;
	private UserDao mockUserDao;

	@Before
	public void setUp() {
		accountManager = new AccountManager();
		mockUserDao = EasyMock.createMock(UserDao.class);
		accountManager.setUserDao(mockUserDao);
	}

	@After
	public void tearDown() {
		EasyMock.verify(mockUserDao);
	}

	/**
	 * 用户认证测试.
	 * 
	 * 分别测试正确的用户与正确,空,错误的密码三种情况.
	 */
	@Test
	public void authUser() {
		EasyMock.expect(mockUserDao.countUserByLoginNamePassword("admin", "admin")).andReturn(1L);
		EasyMock.expect(mockUserDao.countUserByLoginNamePassword("admin", "errorPasswd")).andReturn(0L);
		EasyMock.replay(mockUserDao);

		assertEquals(true, accountManager.authenticate("admin", "admin"));
		assertEquals(false, accountManager.authenticate("admin", ""));
		assertEquals(false, accountManager.authenticate("admin", "errorPasswd"));
	}
}
