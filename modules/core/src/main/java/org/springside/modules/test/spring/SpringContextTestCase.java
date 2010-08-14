/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: SpringContextTestCase.java 1141 2010-07-31 17:54:22Z calvinxiu $
 */
package org.springside.modules.test.spring;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

/**
 * Spring的支持依赖注入的JUnit4 集成测试基类, 支持:
 * 
 * 1.支持Spring 依赖注入功能.
 * 2.支持JUnit Assert功能.
 * 
 * 子类需要定义applicationContext文件的位置,如:
 * @ContextConfiguration(locations = { "/applicationContext-test.xml" })
 * 
 * @see AbstractJUnit4SpringContextTests
 * 
 * @author calvin
 */
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
public abstract class SpringContextTestCase extends Assert implements ApplicationContextAware {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected ApplicationContext applicationContext;

	public final void setApplicationContext(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
