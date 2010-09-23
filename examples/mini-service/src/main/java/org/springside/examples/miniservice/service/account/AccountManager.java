package org.springside.examples.miniservice.service.account;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.miniservice.dao.account.UserDao;
import org.springside.examples.miniservice.entity.account.User;

/**
 * 用户管理类.
 * 
 * 实现领域对象用户及其相关实体的所有业务管理函数.
 * 使用Spring annotation定义事务管理.
 * 
 * @author calvin
 */
//Spring Service Bean的标识.
@Component
//默认将类中的所有函数纳入事务管理.
@Transactional
public class AccountManager {

	private UserDao userDao = null;

	/**
	 * 获取全部用户, 并对用户的延迟加载关联进行初始化.
	 */
	@Transactional(readOnly = true)
	public List<User> getAllInitedUser() {
		List<User> userList = userDao.getAll("id", true);
		for (User user : userList) {
			userDao.initUser(user);
		}
		return userList;
	}

	/**
	 * 获取用户, 并对用户的延迟加载关联进行初始化.
	 */
	@Transactional(readOnly = true)
	public User getInitedUser(Long id) {
		User user = userDao.get(id);
		userDao.initUser(user);
		return user;
	}

	public void saveUser(User user) {
		userDao.save(user);
	}

	/**
	 * 验证用户名密码. 
	 * 
	 * @return 验证通过时返回true.用户名或密码错误时返回false.
	 */
	@Transactional(readOnly = true)
	public boolean authenticate(String loginName, String password) {
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
			return false;
		}

		return (userDao.countUserByLoginNamePassword(loginName, password) == 1);
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
