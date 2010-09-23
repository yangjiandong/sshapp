package org.springside.examples.showcase.unit.common;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springside.examples.showcase.common.dao.UserJdbcDao;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.data.UserData;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.test.utils.DataUtils;
import org.springside.modules.test.utils.DbUnitUtils;

import com.google.common.collect.Lists;

/**
 * UserJdbcDao的集成测试用例, 演示Spring Jdbc Template的使用.
 * 
 * @author calvin
 */
@DirtiesContext
@TransactionConfiguration(transactionManager = "defaultTransactionManager")
@ContextConfiguration(locations = { "/applicationContext-test.xml", "/common/applicationContext-jdbc.xml" })
public class UserJdbcDaoTest extends SpringTxTestCase {
	private static DataSource dataSourceHolder = null;
	@Autowired
	private UserJdbcDao userJdbcDao;

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
	public void queryObject() {
		User user = userJdbcDao.queryObject("1");
		assertEquals("admin", user.getLoginName());
	}

	@Test
	public void queryObjectList() {
		List<User> resultlist = userJdbcDao.queryObjectList();
		assertEquals("admin", resultlist.get(0).getLoginName());
	}

	@Test
	public void queryMap() {
		Map<String, Object> resultMap = userJdbcDao.queryMap(1L);
		assertEquals("admin", resultMap.get("login_name"));
	}

	@Test
	public void queryMapList() {
		List<Map<String, Object>> resultList = userJdbcDao.queryMapList();
		assertEquals("admin", resultList.get(0).get("login_name"));
	}

	@Test
	public void queryByNamedParameter() {
		User user = userJdbcDao.queryByNamedParameter("admin");
		assertEquals("admin", user.getLoginName());
	}

	@Test
	public void queryByNamedParameterWithInClause() {
		List<User> users = userJdbcDao.queryByNamedParameterWithInClause(1L, 2L);
		assertEquals(2, users.size());
	}

	@Test
	public void createObject() {
		String id = UserData.getUserId();
		User user = new User();
		user.setId(id);
		user.setLoginName(DataUtils.randomName("user"));
		user.setName(DataUtils.randomName("User"));
		userJdbcDao.createObject(user);

		User newUser = userJdbcDao.queryObject(id);
		assertEquals(user.getLoginName(), newUser.getLoginName());
	}

	@Test
	public void batchCreateObject() {
		String id1 = UserData.getUserId();
		User user1 = new User();
		user1.setId(id1);
		user1.setLoginName(DataUtils.randomName("user"));
		user1.setName(DataUtils.randomName("User"));

		String id2 = UserData.getUserId();
		User user2 = new User();
		user2.setId(id2);
		user2.setLoginName(DataUtils.randomName("user"));
		user2.setName(DataUtils.randomName("User"));

		List<User> list = Lists.newArrayList(user1, user2);

		userJdbcDao.batchCreateObject(list);

		User newUser1 = userJdbcDao.queryObject(id1);
		assertEquals(user1.getLoginName(), newUser1.getLoginName());

		User newUser2 = userJdbcDao.queryObject(id2);
		assertEquals(user2.getLoginName(), newUser2.getLoginName());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void searchObject() {
		Map map = new HashMap();
		List<User> list = userJdbcDao.searchUserByFreemarkerSqlTemplate(map);
		assertTrue(list.size() > 1);

		map.clear();
		map.put("loginName", "admin");
		list = userJdbcDao.searchUserByFreemarkerSqlTemplate(map);
		assertEquals(1, list.size());

		map.clear();
		map.put("name", "Admin");
		list = userJdbcDao.searchUserByFreemarkerSqlTemplate(map);
		assertEquals(1, list.size());

		map.clear();
		map.put("loginName", "admin");
		map.put("name", "Admin");
		list = userJdbcDao.searchUserByFreemarkerSqlTemplate(map);
		assertEquals(1, list.size());
	}
}
