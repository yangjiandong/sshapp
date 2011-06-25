package org.ssh.pm.common.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import org.springframework.stereotype.Component;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import org.ssh.pm.common.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

@Component
public class CommonJdbcDao {
	private static Logger logger = LoggerFactory.getLogger(CommonJdbcDao.class);

	private SimpleJdbcTemplate simpleJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate njdbcTemplate;
	private TransactionTemplate transactionTemplate;
	private String searchUserSql;
	private UserMapper userMapper = new UserMapper();

	// @Resource
	@Autowired
	public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
		simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
		njdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	// @Resource
	@Autowired
	public void setDefaultTransactionManager(
			//defaultTransactionManager
			@Qualifier("jdbcTransactionManager") PlatformTransactionManager defaultTransactionManager) {
		transactionTemplate = new TransactionTemplate(defaultTransactionManager);
	}

	public void setSearchUserSql(String searchUserSql) {
		this.searchUserSql = searchUserSql;
	}

	// spring jdbc
	public void createUser(User u) {
		String sql = "INSERT INTO t_users(loginName, userName) values(?,?)";

		Object[] paramValues = { u.getLoginName(), u.getUserName() };
		this.jdbcTemplate.update(sql, paramValues);
	}

	public void delUser(User u) {
		String sql = "delete from  t_users where loginName=?";

		Object[] paramValues = { u.getLoginName() };
		this.jdbcTemplate.update(sql, paramValues);
	}

	public List<User> finaAll() {
		String sql = "SELECT * FROM T_users";

		return this.jdbcTemplate.query(sql, new UserMapper());
	}

	public User findByLoginName(String loginName) {
		String sql = "SELECT * FROM T_UESRS WHERE loginName = ?";
		Object[] paramValues = { loginName };

		return (User) this.jdbcTemplate.queryForObject(sql, paramValues,
				new UserMapper());
	}

	// NamedParameterJdbcTemplate
	public void create(User acc) {
		String sql = "INSERT INTO account(loginname,password,email,"
				+ "cellphone,registed_time) "
				+ "VALUES(:loginname,:password,:email,:cellphone, NOW())";

		// 使用一个Bean对象的属性值作为命名参数的值
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(
				acc);

		this.njdbcTemplate.update(sql, namedParameters);
	}

	public void delete(User acc) {
		String sql = "DELETE FROM account WHERE id=:id";

		// 使用指定的值来代替命名参数
		SqlParameterSource namedParameters = new MapSqlParameterSource("id",
				acc.getId());

		this.njdbcTemplate.update(sql, namedParameters);
	}

	public void update(User acc) {
		String sql = "UPDATE account SET loginname=:loginname,"
				+ "password=:password,email=:email,"
				+ "cellphone=:cellphone WHERE id=:id";

		// 使用Map对象中的键/值对来代替多个命名参数的实际值
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("loginname", acc.getLoginName());
		namedParameters.put("password", acc.getPassword());
		namedParameters.put("id", acc.getId());

		this.njdbcTemplate.update(sql, namedParameters);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		String sql = "SELECT * FROM account";

		// 通过getJdbcOperations()来访问只有在JdbcTemplate中拥有的功能
		return this.njdbcTemplate.getJdbcOperations().query(sql,
				new UserMapper());
	}

	public User findById(Long id) {
		String sql = "SELECT * FROM account WHERE id=?";

		// 使用指定的值来代替命名参数
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);

		return (User) njdbcTemplate.query(sql, namedParameters,
				new UserMapper());
	}

	// spring jdbc example
	public void updateUser(User u) {
		// jdbcTemplate.execute("CREATE TABLE USER (user_id integer, name varchar(100))");
		simpleJdbcTemplate.update(
				"UPDATE t_users SET name = ? WHERE user_id = ?", new Object[] {
						u.getName(), u.getId() });

		// simpleJdbcTemplate.update("INSERT INTO t_users VALUES(?, ?, ?, ?)",
		// new Object[] {user.getId(), user.getName(), user.getSex(),
		// user.getAge()});
		// int count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM USER");
		// String name = (String)
		// jdbcTemplate.queryForObject("SELECT name FROM USER WHERE user_id = ?",
		// new Object[] {id}, java.lang.String.class);
		// List rows = jdbcTemplate.queryForList("SELECT * FROM USER");
	}

	public List<User> getUsers(Long userId) throws SQLException {
		return jdbcTemplate.query(
				"SELECT id, name,loginName FROM t_users WHERE id = ? ",
				userMapper, userId);
	}

	// http://www.codefutures.com/tutorials/spring-pagination/
	// page
	public JdbcPage<User> getUsersByJdbcpage(final int pageNo,
			final int pageSize, Long userId) throws SQLException {
		JdbcPaginationHelper<User> ph = new JdbcPaginationHelper<User>();

		return ph.fetchPage(jdbcTemplate, "SELECT count(*) FROM t_users ",
				"SELECT id, name,loginName FROM t_users", // new
															// Object[]{userId},
				null, pageNo, pageSize, userMapper);
	}

	private class UserMapper implements RowMapper<User> {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getLong("id"));
			user.setName(rs.getString("name"));
			user.setLoginName(rs.getString("login_name"));

			return user;
		}
	}

	// 服务层加 @Transactional
	public void insertDemo() {
		String sql = "INSERT INTO demo_test(no, name) values(?,?)";

		Object[] paramValues = { "01", "中午" };
		this.jdbcTemplate.update(sql, paramValues);
	}

	public void insertDemoInTransaction() {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus status) {
				String sql = "INSERT INTO demo_test(no, name) values(?,?)";
				Object[] paramValues = { "01", "中午-自己加事务" };
				simpleJdbcTemplate.update(sql, paramValues);
			}
		});
	}
}
