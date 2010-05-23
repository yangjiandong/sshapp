#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.unit.service.security;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import ${package}.data.SecurityData;
import ${package}.entity.security.Authority;
import ${package}.entity.security.Role;
import ${package}.entity.security.User;
import ${package}.service.security.SecurityManager;
import ${package}.service.security.UserDetailsServiceImpl;
import org.springside.modules.utils.ReflectionUtils;

/**
 * UserDetailsServiceImpl的单元测试用例, 测试Service层的业务逻辑. 
 * 
 * 使用EasyMock对UserManager进行模拟.
 * 
 * @author calvin
 */
public class UserDetailsServiceImplTest extends Assert {

	private UserDetailsServiceImpl userDetailService = new UserDetailsServiceImpl();
	private SecurityManager securityManager = null;

	@Before
	public void setUp() {
		securityManager = EasyMock.createNiceMock(SecurityManager.class);
		ReflectionUtils.setFieldValue(userDetailService, "securityManager", securityManager);
	}

	@After
	public void tearDown() {
		EasyMock.verify(securityManager);
	}

	@Test
	public void loadUserExist() {

		String authName = "A_foo";

		User user = SecurityData.getRandomUser();
		Role role = SecurityData.getRandomRole();
		user.getRoles().add(role);
		Authority auth = new Authority();
		auth.setName(authName);
		role.getAuthorities().add(auth);

		//录制脚本
		EasyMock.expect(securityManager.findUserByLoginName(user.getLoginName())).andReturn(user);
		EasyMock.replay(securityManager);

		//执行测试
		UserDetails userDetails = userDetailService.loadUserByUsername(user.getLoginName());

		//校验结果
		assertEquals(user.getLoginName(), userDetails.getUsername());
		assertEquals(user.getPassword(), userDetails.getPassword());
		assertEquals(1, userDetails.getAuthorities().length);
		assertEquals(new GrantedAuthorityImpl(authName), userDetails.getAuthorities()[0]);
	}

	@Test(expected = UsernameNotFoundException.class)
	public void loadUserNotExist() {
		//录制脚本
		EasyMock.expect(securityManager.findUserByLoginName("userNameNotExist")).andReturn(null);
		EasyMock.replay(securityManager);
		//执行测试
		userDetailService.loadUserByUsername("userNameNotExist");
	}
}