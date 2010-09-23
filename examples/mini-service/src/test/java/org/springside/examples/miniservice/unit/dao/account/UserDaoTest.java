package org.springside.examples.miniservice.unit.dao.account;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.miniservice.dao.account.UserDao;
import org.springside.examples.miniservice.data.AccountData;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.test.utils.DbUnitUtils;

/**
 * UserDao的集成测试用例,测试ORM映射及特殊的DAO操作.
 * 
 * 默认在每个测试函数后进行回滚.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class UserDaoTest extends SpringTxTestCase {

	private static DataSource dataSourceHolder = null;

	@Autowired
	private UserDao entityDao;

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

	@Test
	//如果你需要真正插入数据库,将Rollback设为false
	//@Rollback(false) 
	public void crudEntityWithRole() throws Exception {
		DbUnitUtils.loadData(dataSource, "/data/default-data.xml");

		//新建并保存带角色的用户
		User user = AccountData.getRandomUserWithAdminRole();
		entityDao.save(user);
		//强制执行保存sql
		entityDao.flush();

		//查找用户
		user = entityDao.findUniqueBy("id", user.getId());
		assertEquals(1, user.getRoleList().size());

		//删除用户的角色
		user.getRoleList().remove(0);
		entityDao.flush();
		user = entityDao.findUniqueBy("id", user.getId());
		assertEquals(0, user.getRoleList().size());

		//删除用户
		entityDao.delete(user.getId());
		entityDao.flush();
		user = entityDao.findUniqueBy("id", user.getId());
		assertNull(user);
	}

	@Test(expected = org.hibernate.exception.ConstraintViolationException.class)
	public void saveNotUniqueUser() throws Exception {
		User user = AccountData.getRandomUser();
		user.setLoginName("admin");

		entityDao.save(user);
		entityDao.flush();
	}
}
