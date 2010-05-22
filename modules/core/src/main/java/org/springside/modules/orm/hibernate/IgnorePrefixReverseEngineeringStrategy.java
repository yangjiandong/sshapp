package org.springside.modules.orm.hibernate;

import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;

/**
 * Hibernate Tools 从数据库逆向生成Entity POJO时, Class名要去除表名中的前缀的策略.
 * 
 * 继承子类修改前缀长度.
 * 
 * @author calvin
 */
public abstract class IgnorePrefixReverseEngineeringStrategy extends DelegatingReverseEngineeringStrategy {

	public IgnorePrefixReverseEngineeringStrategy(ReverseEngineeringStrategy delegate) {
		super(delegate);
	}

	@Override
	public String tableToClassName(TableIdentifier tableIdentifier) {
		String delegateResult = super.tableToClassName(tableIdentifier);
		int index = delegateResult.lastIndexOf('.');

		String packageName = delegateResult.substring(0, index + 1);
		String className = delegateResult.substring(index + getPrefixLength() + 1);
		String fullClassName = packageName + className;

		return fullClassName;
	}

	/**
	 * 在子类重载的忽略的长度.
	 */
	abstract protected int getPrefixLength();
}
