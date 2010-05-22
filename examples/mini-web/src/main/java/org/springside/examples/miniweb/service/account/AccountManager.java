package org.springside.examples.miniweb.service.account;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.miniweb.dao.account.AuthorityDao;
import org.springside.examples.miniweb.dao.account.RoleDao;
import org.springside.examples.miniweb.dao.account.UserDao;
import org.springside.examples.miniweb.entity.account.Authority;
import org.springside.examples.miniweb.entity.account.Role;
import org.springside.examples.miniweb.entity.account.User;
import org.springside.examples.miniweb.service.ServiceException;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 * 
 * @author calvin
 */
//Spring Bean的标识.
@Component
//默认将类中的所有函数纳入事务管理.
@Transactional
public class AccountManager {

	private static Logger logger = LoggerFactory.getLogger(AccountManager.class);

	private UserDao userDao;
	private RoleDao roleDao;
	private AuthorityDao authorityDao;

	//-- User Manager --//
	@Transactional(readOnly = true)
	public User getUser(Long id) {
		return userDao.get(id);
	}

	public void saveUser(User entity) {
		userDao.save(entity);
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	/**
	 * 使用属性过滤条件查询用户.
	 */
	@Transactional(readOnly = true)
	public Page<User> searchUser(final Page<User> page, final List<PropertyFilter> filters) {
		return userDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public User findUserByLoginName(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}

	/**
	 * 检查用户名是否唯一.
	 *
	 * @return loginName在数据库中唯一或等于oldLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String newLoginName, String oldLoginName) {
		return userDao.isPropertyUnique("loginName", newLoginName, oldLoginName);
	}

	//-- Role Manager --//
	@Transactional(readOnly = true)
	public Role getRole(Long id) {
		return roleDao.get(id);
	}

	@Transactional(readOnly = true)
	public List<Role> getAllRole() {
		return roleDao.getAll("id", true);
	}

	public void saveRole(Role entity) {
		roleDao.save(entity);
	}

	public void deleteRole(Long id) {
		roleDao.delete(id);
	}

	//-- Authority Manager --//
	@Transactional(readOnly = true)
	public List<Authority> getAllAuthority() {
		return authorityDao.getAll();
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Autowired
	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}
}
