package org.springside.examples.showcase.unit.common;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springside.examples.showcase.common.dao.UserDao;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.AccountManager;
import org.springside.examples.showcase.common.service.ServiceException;

public class AccountManagerTest {

	private IMocksControl control = EasyMock.createControl();

	private AccountManager accountManager;
	private UserDao mockUserDao;

	@Before
	public void setUp() {
		accountManager = new AccountManager();
		mockUserDao = control.createMock(UserDao.class);
		accountManager.setUserDao(mockUserDao);
	}

	@After
	public void tearDown() {
		control.verify();
	}

	@Test
	public void saveUser() {
		User admin = new User();
		admin.setId("1");
		User user = new User();
		user.setId("2");

		mockUserDao.save(user);
		control.replay();

		//正常保存用户.
		accountManager.saveUser(user);

		//保存超级管理用户抛出异常.
		try {
			accountManager.saveUser(admin);
			fail("expected ServicExcepton not be thrown");
		} catch (ServiceException e) {
			//expected exception
		}
	}
}
