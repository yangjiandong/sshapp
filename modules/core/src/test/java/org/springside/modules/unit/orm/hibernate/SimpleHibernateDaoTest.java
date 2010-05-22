package org.springside.modules.unit.orm.hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.orm.hibernate.SimpleHibernateDao;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.unit.orm.hibernate.data.User;

import com.google.common.collect.Maps;

@ContextConfiguration(locations = { "/applicationContext-core-test.xml" })
public class SimpleHibernateDaoTest extends SpringTxTestCase {

	private static final String DEFAULT_LOGIN_NAME = "admin";

	private SimpleHibernateDao<User, Long> dao;

	@Autowired
	private SessionFactory sessionFactory;

	@Before
	public void setUp() throws BeansException, SQLException, DatabaseUnitException, IOException {
		jdbcTemplate.update("drop all objects");
		jdbcTemplate.update("runscript from 'src/test/resources/schema.sql'");

		DatabaseDataSourceConnection connection = new DatabaseDataSourceConnection((DataSource) applicationContext
				.getBean("dataSource"));
		InputStream stream = applicationContext.getResource("classpath:/test-data.xml").getInputStream();
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(stream);
		DatabaseOperation.INSERT.execute(connection, dataSet);
		connection.close();

		dao = new SimpleHibernateDao<User, Long>(sessionFactory, User.class);
	}

	@Test
	public void crud() {
		User user = new User();
		user.setName("foo");
		user.setLoginName("foo");
		dao.save(user);
		user.setName("boo");
		dao.save(user);
		dao.delete(user);
	}

	@Test
	public void getAll() {
		List<User> users = dao.getAll("id", true);
		assertEquals(6, users.size());
		assertEquals(DEFAULT_LOGIN_NAME, users.get(0).getLoginName());
	}

	@Test
	public void findByProperty() {
		List<User> users = dao.findBy("loginName", DEFAULT_LOGIN_NAME);
		assertEquals(1, users.size());
		assertEquals(DEFAULT_LOGIN_NAME, users.get(0).getLoginName());

		User user = dao.findUniqueBy("loginName", DEFAULT_LOGIN_NAME);
		assertEquals(DEFAULT_LOGIN_NAME, user.getLoginName());
	}

	@Test
	public void findByHQL() {

		List<User> users = dao.find("from User u where loginName=?", DEFAULT_LOGIN_NAME);
		assertEquals(1, users.size());
		assertEquals(DEFAULT_LOGIN_NAME, users.get(0).getLoginName());

		User user = dao.findUnique("from User u where loginName=?", DEFAULT_LOGIN_NAME);
		assertEquals(DEFAULT_LOGIN_NAME, user.getLoginName());

		Map<String, Object> values = Maps.newHashMap();
		values.put("loginName", DEFAULT_LOGIN_NAME);
		users = dao.find("from User u where loginName=:loginName", values);
		assertEquals(1, users.size());
		assertEquals(DEFAULT_LOGIN_NAME, users.get(0).getLoginName());

		user = dao.findUnique("from User u where loginName=:loginName", values);
		assertEquals(DEFAULT_LOGIN_NAME, user.getLoginName());
	}

	@Test
	public void findByCriterion() {
		Criterion c = Restrictions.eq("loginName", DEFAULT_LOGIN_NAME);
		List<User> users = dao.find(c);
		assertEquals(1, users.size());
		assertEquals(DEFAULT_LOGIN_NAME, users.get(0).getLoginName());

		User user = dao.findUnique(c);
		assertEquals(DEFAULT_LOGIN_NAME, user.getLoginName());

		dao.findUnique(c);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void batchUpdate() {
		Map map = new HashMap();
		map.put("ids", new Long[] { 1L, 23L });

		dao.batchExecute("update User u set u.status='disabled' where id in(:ids)", map);
		User u1 = dao.get(1L);
		assertEquals("disabled", u1.getStatus());
		User u3 = dao.get(3L);
		assertEquals("enabled", u3.getStatus());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void eagerFetch() {
		String sql = "from User u left join fetch u.roleList order by u.id";

		Query query = dao.createQuery(sql);
		List<User> userList = dao.distinct(query).list();
		assertEquals(6, userList.size());
		assertTrue(Hibernate.isInitialized(userList.get(0).getRoleList()));

		Criteria criteria = dao.createCriteria().setFetchMode("roles", FetchMode.JOIN);
		userList = dao.distinct(criteria).list();
		assertEquals(6, userList.size());
		assertTrue(Hibernate.isInitialized(userList.get(0).getRoleList()));
	}

	@Test
	public void getIdName() {
		assertEquals("id", dao.getIdName());
	}

}
