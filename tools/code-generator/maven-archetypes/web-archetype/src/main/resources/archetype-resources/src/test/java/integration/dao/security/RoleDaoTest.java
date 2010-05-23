#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.dao.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ${package}.dao.security.RoleDao;
import ${package}.data.SecurityData;
import ${package}.entity.security.Role;
import org.springside.modules.test.spring.SpringTxTestCase;

/**
 * UserDao的集成测试用例,测试ORM映射及特殊的DAO操作.
 * 
 * @author calvin
 */
public class RoleDaoTest extends SpringTxTestCase {
	@Autowired
	private RoleDao entityDao;

	@Test
	public void crudEntity() {
		//new entity and save it. 
		Role entity = SecurityData.getRandomRole();
		entityDao.save(entity);
		flush();

		//find entity.
		Role entityFromDB = entityDao.findUniqueBy("id", entity.getId());
		assertReflectionEquals(entity, entityFromDB);

		//delete entity.
		entityDao.delete(entity.getId());
		flush();
		entity = entityDao.findUniqueBy("id", entity.getId());
		assertNull(entity);
	}

	/**
	 * 测试删除角色时删除用户-角色的中间表.
	 */
	@Test
	public void deleteRole() {
		int oldJoinTableCount = countRowsInTable("SS_USER_ROLE");
		int oldUserTableCount = countRowsInTable("SS_USER");

		//删除用户角色, 中间表将减少6条记录.
		entityDao.delete(2L);
		flush();

		int newJoinTableCount = countRowsInTable("SS_USER_ROLE");
		int newUserTableCount = countRowsInTable("SS_USER");
		assertEquals(6, oldJoinTableCount - newJoinTableCount);
		assertEquals(0, oldUserTableCount - newUserTableCount);
	}
}
