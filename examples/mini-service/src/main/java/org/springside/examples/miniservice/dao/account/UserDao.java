package org.springside.examples.miniservice.dao.account;

import org.springframework.stereotype.Component;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 用户对象的泛型DAO.
 * 
 * @author calvin
 */
@Component
public class UserDao extends HibernateDao<User, Long> {
	//-- 统一定义所有以用户为主体的HQL --//
	private static final String COUNT_BY_LNAME_PASSWD = "select count(u) from User u where u.loginName=? and u.password=?";

	public Long countUserByLoginNamePassword(String loginName, String password) {
		return (Long) findUnique(COUNT_BY_LNAME_PASSWD, loginName, password);
	}

	/**
	 * 初始化User的延迟加载关联roleList.
	 */
	public void initUser(User user) {
		initProxyObject(user.getRoleList());
	}
}
