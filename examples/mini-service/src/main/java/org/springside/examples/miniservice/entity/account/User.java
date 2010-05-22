package org.springside.examples.miniservice.entity.account;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springside.examples.miniservice.entity.IdEntity;

import com.google.common.collect.Lists;

/**
 * 用户.
 * 
 * 使用JPA annotation定义ORM关系.
 * 使用Hibernate annotation定义JPA 1.0未覆盖的部分.
 * 
 * @author calvin
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "acct_user")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends IdEntity {
	private String loginName;
	private String password;
	private String name;
	private String email;

	private List<Role> roleList = Lists.newArrayList();

	@Column(nullable = false, unique = true)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//多对多定义,无需定义Cascade.
	@ManyToMany
	//中间表定义,表名采用默认命名规则
	@JoinTable(name = "acct_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	//Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	//集合按id排序.
	@OrderBy("id")
	//集合中对象的id的缓存.
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}