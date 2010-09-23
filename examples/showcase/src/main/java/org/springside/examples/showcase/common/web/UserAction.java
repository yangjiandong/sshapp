package org.springside.examples.showcase.common.web;

import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.StaleStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.AccountManager;

/**
 * 用户管理Action.
 * 
 * @author calvin
 */
@Namespace("/common")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "user.action", type = "redirect") })
public class UserAction extends CrudActionSupport<User> {

	private static final long serialVersionUID = 7240853226114035208L;

	private AccountManager accountManager;

	//-- 页面属性  --//
	private String id;
	private User entity;
	private List<User> allUserList;
	private Integer workingVersion;//对象版本号, 配合Hibernate的@Version防止并发修改
	private List<String> checkedUserIds;

	//-- ModelDriven 与 Preparable函数 --//
	public User getModel() {
		return entity;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = accountManager.getUser(id);
		} else {
			entity = new User();
		}
	}

	//-- CRUD Action 函数 --//
	@Override
	public String list() throws Exception {
		allUserList = accountManager.getAllUserWithRole();
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	/**
	 * 保存用户时,演示Hibernate的version字段使用.
	 */
	@Override
	public String save() throws Exception {
		if (workingVersion < entity.getVersion()) {
			throw new StaleStateException("对象已有新的版本");
		}

		accountManager.saveUser(entity);
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		throw new UnsupportedOperationException("delete操作暂时未支持");
	}

	//-- 其他Action函数 --//
	public String disableUsers() {
		accountManager.disableUsers(checkedUserIds);
		return RELOAD;
	}

	//-- 页面属性访问函数 --//
	public List<User> getAllUserList() {
		return allUserList;
	}

	public void setCheckedUserIds(List<String> checkedUserIds) {
		this.checkedUserIds = checkedUserIds;
	}

	public void setWorkingVersion(Integer workingVersion) {
		this.workingVersion = workingVersion;
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
}
