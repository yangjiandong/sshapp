#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.entity.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import ${package}.entity.IdEntity;

/**
 * 权限.
 * 
 * 注释见{@link User}.
 * 
 * @author calvin
 */
@Entity
@Table(name = "ACCT_AUTHORITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority extends IdEntity {

	/**
	 * SpringSecurity中默认的角色/授权名前缀.
	 */
	public static final String AUTHORITY_PREFIX = "ROLE_";

	private String name;

	public Authority() {
	}

	public Authority(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public String getPrefixedName() {
		return AUTHORITY_PREFIX + name;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
