package org.springside.examples.miniservice.entity.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springside.examples.miniservice.entity.IdEntity;

/**
 * 角色.
 * 
 * 注释见{@link User}.
 * 
 * @author calvin
 */
@Entity
@Table(name = "acct_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends IdEntity {

	private String name;

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
