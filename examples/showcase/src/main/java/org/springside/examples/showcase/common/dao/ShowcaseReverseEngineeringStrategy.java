package org.springside.examples.showcase.common.dao;

import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.springside.modules.orm.hibernate.IgnorePrefixReverseEngineeringStrategy;

public class ShowcaseReverseEngineeringStrategy extends IgnorePrefixReverseEngineeringStrategy {

	public ShowcaseReverseEngineeringStrategy(ReverseEngineeringStrategy delegate) {
		super(delegate);
	}

	@Override
	protected int getPrefixLength() {
		return 2;
	}
}
