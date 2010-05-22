/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: GroupsUtils.java 516 2009-10-02 13:55:33Z calvinxiu $
 */
package org.springside.modules.test.groups;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 实现TestNG Groups分组执行用例功能的TestRunner.
 * 
 * 比如可以在白天持续执行速度较快的测试方法, 到了晚上再执行较慢的测试方法.
 * 
 * Runner会只执行测试类的@Groups定义, 与在-Dtest.groups=xxx 或 application.test.properties的test.groups=xxx相吻合的测试类及测试方法.
 * 如两处均无设置则执行全部.
 * 另提供独立判断的工具方法供其他的Runner调用.
 * 
 * 注意, 本类只适用于JUnit 4.4以上版本.
 *  
 * @author freeman
 * @author calvin
 */
public class GroupsTestRunner extends BlockJUnit4ClassRunner {

	/** 在Properties文件或JVM参数-D中定义执行分组的变量名称. */
	public static final String PROPERTY_NAME = "test.groups";
	/** 定义了分组变量的Properties文件名. */
	public static final String PROPERTY_FILE = "application.test.properties";

	private static Logger logger = LoggerFactory.getLogger(GroupsTestRunner.class);

	private static List<String> groups;

	public GroupsTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	//--重载Runner方法--//

	/**
	 * 重载加入Class级别控制.
	 */
	@Override
	public void run(RunNotifier notifier) {
		if (!shouldRun(getTestClass().getJavaClass())) {
			EachTestNotifier testNotifier = new EachTestNotifier(notifier, getDescription());
			testNotifier.fireTestIgnored();
			return;
		}

		super.run(notifier);
	}

	/**
	 * 重载加入方法级别控制.
	 */
	@Override
	protected void runChild(FrameworkMethod method, RunNotifier notifier) {
		if (!shouldRun(method.getMethod())) {
			Description description = describeChild(method);
			EachTestNotifier eachNotifier = new EachTestNotifier(notifier, description);
			eachNotifier.fireTestIgnored();
			return;
		}

		super.runChild(method, notifier);
	}

	//-- 判断测试用例/测试方法是否符合的工具方法 --//

	/**
	 * 判断测试类是否符合分组要求.
	 * 如果至少含有一个符合Groups定义的测试方法时返回true.
	 */
	public static boolean shouldRun(Class<?> testClass) {
		Method[] methods = testClass.getMethods();
		for (Method method : methods) {
			if (method.getAnnotation(Test.class) != null && method.getAnnotation(Ignore.class) == null
					&& shouldRun(method)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断测试方法是否符合Groups要求.
	 * 如果@Groups符合定义或Groups定义为ALL或方法上无@Groups定义返回true.
	 */
	public static boolean shouldRun(Method testMethod) {
		//初始化Groups定义
		if (groups == null) {
			initGroups(PROPERTY_FILE);
		}
		//如果groups定义为全部执行则返回true
		if (groups.contains(Groups.ALL)) {
			return true;
		}

		//取得方法上的Groups annotation, 如果无Groups注解或注解符合分组要求则返回true.
		Groups groupsAnnotation = testMethod.getAnnotation(Groups.class);
		if ((groupsAnnotation == null) || groups.contains(groupsAnnotation.value())) {
			return true;
		}

		return false;
	}

	/**
	 * 从系统变量或Properties文件初始化运行的groups.
	 * 如果均无定义则返回ALL.
	 */
	protected static void initGroups(String propertyFile) {

		String groupsDefine = getGroupsFromSystemProperty();

		//如果环境变量未定义test.groups,尝试从property文件读取.
		if (StringUtils.isBlank(groupsDefine)) {
			groupsDefine = getGroupsFromPropertyFile(propertyFile);
			//如果仍未定义,设为全部运行
			if (StringUtils.isBlank(groupsDefine)) {
				groupsDefine = Groups.ALL;
			}
		}

		groups = Arrays.asList(groupsDefine.split(","));
	}

	/**
	 * 从环境变量读取test.groups定义, 多个group用逗号分隔.
	 * eg. java -Dtest.groups=Mini,Majo
	 */
	protected static String getGroupsFromSystemProperty() {
		return System.getProperty(PROPERTY_NAME);
	}

	/**
	 * 从Classpath中的application.test.properties文件读取test.groups定义, 多个group用逗号分隔.
	 * eg. test.groups=Mini,Major
	 */
	protected static String getGroupsFromPropertyFile(String propertyFile) {
		try {
			Properties p = PropertiesLoaderUtils.loadAllProperties(propertyFile);
			return p.getProperty(PROPERTY_NAME);
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
		return null;
	}
}
