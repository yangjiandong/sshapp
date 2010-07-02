package org.ssh.app.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 角色-菜单权限(仅控制到菜单项)
 */
@Entity
@Table(name = "t_role_resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleResource {

	private Long oid;
	private Long roleId;
	private Long resourceId;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Id_Generator")
	@TableGenerator(name = "Id_Generator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "SYS_ROLERESOURCES", initialValue = 1, allocationSize = 1)
	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	@Column(name = "ROLE_ID")
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Column(name = "RESOURCE_ID")
	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	@Transient
	public boolean isTransient() {
		return this.oid == null;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
