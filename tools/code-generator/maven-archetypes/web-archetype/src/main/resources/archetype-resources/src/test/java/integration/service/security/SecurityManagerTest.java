#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.service.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ${package}.service.ServiceException;
import ${package}.service.security.SecurityManager;
import org.springside.modules.test.spring.SpringTxTestCase;

/**
 * SecurityManager的集成测试用例,测试Service层的业务逻辑.
 * 
 * 调用实际的DAO类进行操作,亦可使用MockDAO对象将本用例改为单元测试.
 * 
 * @author calvin
 */
public class SecurityManagerTest extends SpringTxTestCase {

	@Autowired
	private SecurityManager securityManager;

	@Test
	public void deleteUser() {
		securityManager.deleteUser(2L);
	}

	@Test(expected = ServiceException.class)
	public void deleteAdmin() {
		securityManager.deleteUser(1L);
	}
}
