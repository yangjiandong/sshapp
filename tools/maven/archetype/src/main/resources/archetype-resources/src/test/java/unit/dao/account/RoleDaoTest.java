#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.unit.dao.account;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import ${package}.dao.account.RoleDao;
import ${package}.dao.account.UserDao;
import ${package}.entity.account.Role;
import ${package}.entity.account.User;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.test.utils.DataUtils;
import org.springside.modules.test.utils.DbUnitUtils;

/**
 * RoleDao的测试用例, 测试ORM映射及特殊的DAO操作.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class RoleDaoTest extends SpringTxTestCase {

	private static DataSource dataSourceHolder = null;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserDao userDao;

	@Before
	public void loadDefaultData() throws Exception {
		if (dataSourceHolder == null) {
			DbUnitUtils.loadData(dataSource, "/data/default-data.xml");
			dataSourceHolder = dataSource;
		}
	}

	@AfterClass
	public static void cleanDefaultData() throws Exception {
		DbUnitUtils.removeData(dataSourceHolder, "/data/default-data.xml");
	}

	/**
	 * 测试删除角色时删除用户-角色的中间表.
	 */
	@Test
	public void deleteRole() {
		//新增测试角色并与admin用户绑定.
		Role role = new Role();
		role.setName(DataUtils.randomName("Role"));
		roleDao.save(role);

		User user = userDao.get(1L);
		user.getRoleList().add(role);
		userDao.save(user);
		userDao.flush();

		int oldJoinTableCount = countRowsInTable("ACCT_USER_ROLE");
		int oldUserTableCount = countRowsInTable("ACCT_USER");

		//删除用户角色, 中间表将减少1条记录,而用户表应该不受影响.
		roleDao.delete(role.getId());
		roleDao.flush();

		int newJoinTableCount = countRowsInTable("ACCT_USER_ROLE");
		int newUserTableCount = countRowsInTable("ACCT_USER");
		assertEquals(1, oldJoinTableCount - newJoinTableCount);
		assertEquals(0, oldUserTableCount - newUserTableCount);
	}
}
