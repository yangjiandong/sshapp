#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.unit.service.account;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ${package}.dao.account.UserDao;
import ${package}.service.ServiceException;
import ${package}.service.account.AccountManager;

/**
 * SecurityEntityManager的测试用例, 测试Service层的业务逻辑.
 * 
 * 调用实际的DAO类进行操作,亦可使用MockDAO对象将本用例改为单元测试.
 * 
 * @author calvin
 */
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
	public void deleteUser() {
		mockUserDao.delete(2L);
		control.replay();

		//正常删除用户.
		accountManager.deleteUser(2L);

		//删除超级管理用户抛出异常.
		try {
			accountManager.deleteUser(1L);
			fail("expected ServicExcepton not be thrown");
		} catch (ServiceException e) {
			//expected exception
		}
	}
}
