package org.springside.examples.miniweb.unit.dao.account;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.miniweb.dao.account.UserDao;
import org.springside.examples.miniweb.data.AccountData;
import org.springside.examples.miniweb.entity.account.User;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.test.utils.DbUnitUtils;

/**
 * UserDao的测试用例, 测试ORM映射及特殊的DAO操作.
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
	public void crudEntityWithRole() {
		//新建并保存带角色的用户
		User user = AccountData.getRandomUserWithRole();
		entityDao.save(user);
		//强制执行sql语句
		entityDao.flush();

		//获取用户
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

	//期望抛出ConstraintViolationException的异常.
	@Test(expected = org.hibernate.exception.ConstraintViolationException.class)
	public void savenUserNotUnique() {
		User user = AccountData.getRandomUser();
		user.setLoginName("admin");
		entityDao.save(user);
		entityDao.flush();
	}
}