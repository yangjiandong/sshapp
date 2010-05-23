#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.service.user;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ${package}.entity.user.User;
import ${package}.service.user.UserManager;
import org.springside.modules.test.spring.SpringTxTestCase;

/**
 * UserManager的集成测试用例,测试Service层的业务逻辑.
 * 
 * 调用实际的DAO类进行操作,亦可使用MockDAO对象将本用例改为单元测试.
 * 
 * @author calvin
 */
public class UserManagerTest extends SpringTxTestCase {

	@Autowired
	private UserManager userManager;

	/**
	 * 用户获取测试.
	 * 
	 * 将用户从session中断开,特别测试用户及其关联角色的初始化情况.
	 */
	@Test
	public void getAllUser() {
		List<User> userList = userManager.getAllUser();
		assertEquals(6, userList.size());

		User user = userList.get(0);
		evict(user);
		assertTrue(user.getRoles().size() > 0);
	}

	/**
	 * 用户认证测试.
	 * 
	 * 分别测试正确的用户与正确,空,错误的密码三种情况.
	 */
	@Test
	public void authUser() {
		assertEquals(true, userManager.authenticate("admin", "admin"));
		assertEquals(false, userManager.authenticate("admin", ""));
		assertEquals(false, userManager.authenticate("admin", "errorPasswd"));
	}
}
