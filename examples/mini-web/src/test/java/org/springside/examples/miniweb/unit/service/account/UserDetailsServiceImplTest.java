package org.springside.examples.miniweb.unit.service.account;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springside.examples.miniweb.data.AccountData;
import org.springside.examples.miniweb.entity.account.Authority;
import org.springside.examples.miniweb.entity.account.Role;
import org.springside.examples.miniweb.entity.account.User;
import org.springside.examples.miniweb.service.account.AccountManager;
import org.springside.examples.miniweb.service.account.UserDetailsServiceImpl;

/**
 * UserDetailsServiceImpl的测试用例, 测试Service层的业务逻辑. 
 * 
 * 使用EasyMock对AccountManager进行模拟.
 * 
 * @author calvin
 */
public class UserDetailsServiceImplTest {

	private IMocksControl control = EasyMock.createControl();

	private UserDetailsServiceImpl userDetailService;
	private AccountManager mockAccountManager;

	@Before
	public void setUp() {
		userDetailService = new UserDetailsServiceImpl();
		mockAccountManager = control.createMock(AccountManager.class);
		userDetailService.setAccountManager(mockAccountManager);
	}

	@After
	public void tearDown() {
		control.verify();
	}

	@Test
	public void loadUserExist() {

		//准备数据
		String authName = "foo";
		User user = AccountData.getRandomUser();
		Role role = AccountData.getRandomRole();
		user.getRoleList().add(role);
		Authority auth = new Authority();
		auth.setName(authName);
		role.getAuthorityList().add(auth);

		//录制脚本
		EasyMock.expect(mockAccountManager.findUserByLoginName(user.getLoginName())).andReturn(user);
		control.replay();

		//执行测试
		UserDetails userDetails = userDetailService.loadUserByUsername(user.getLoginName());

		//校验结果
		assertEquals(user.getLoginName(), userDetails.getUsername());
		assertEquals(user.getPassword(), userDetails.getPassword());
		assertEquals(1, userDetails.getAuthorities().size());
		assertEquals(new GrantedAuthorityImpl(auth.getPrefixedName()), userDetails.getAuthorities().iterator().next());
	}

	@Test(expected = UsernameNotFoundException.class)
	public void loadUserNotExist() {
		//录制脚本
		EasyMock.expect(mockAccountManager.findUserByLoginName("userNameNotExist")).andReturn(null);
		control.replay();
		//执行测试
		userDetailService.loadUserByUsername("userNameNotExist");
	}
}