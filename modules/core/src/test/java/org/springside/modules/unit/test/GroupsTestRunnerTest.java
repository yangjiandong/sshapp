package org.springside.modules.unit.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.springside.modules.test.groups.Groups;
import org.springside.modules.test.groups.GroupsTestRunner;
import org.springside.modules.utils.ReflectionUtils;

@RunWith(GroupsTestRunner.class)
public class GroupsTestRunnerTest extends Assert {

	@Test
	@SuppressWarnings("unchecked")
	public void groupsInit() throws InitializationError {
		GroupsTestRunner groupsTestRunner = new GroupsTestRunner(GroupsTestRunnerTest.class);

		//从application-core-test.properties中取出test.groups值, 为DAILY
		ReflectionUtils.invokeMethod(groupsTestRunner, "initGroups", new Class[] { String.class },
				new Object[] { "application.core.test.properties" });
		assertEquals("DAILY", ((List<String>) ReflectionUtils.getFieldValue(groupsTestRunner, "groups")).get(0));

		//设置系统变量值, 覆盖application-test.properties中的值, 为DAILY,NIGHTLY 		
		System.setProperty(GroupsTestRunner.PROPERTY_NAME, "DAILY,NIGHTLY");
		ReflectionUtils.invokeMethod(groupsTestRunner, "initGroups", new Class[] { String.class },
				new Object[] { "application.core.test.properties" });
		assertEquals("DAILY", ((List<String>) ReflectionUtils.getFieldValue(groupsTestRunner, "groups")).get(0));
		assertEquals("NIGHTLY", ((List<String>) ReflectionUtils.getFieldValue(groupsTestRunner, "groups")).get(1));

		//清理设置
		ReflectionUtils.setFieldValue(groupsTestRunner, "groups", null);
	}

	@Test
	public void isTestClassShouldRun() throws InitializationError {
		GroupsTestRunner groupsTestRunner = new GroupsTestRunner(GroupsTestRunnerTest.class);
		ReflectionUtils.invokeMethod(groupsTestRunner, "initGroups", new Class[] { String.class },
				new Object[] { "application.core.test.properties" });
		assertEquals(true, GroupsTestRunner.shouldRun(TestClassBean1.class));
		assertEquals(true, GroupsTestRunner.shouldRun(TestClassBean2.class));
		assertEquals(false, GroupsTestRunner.shouldRun(TestClassBean3.class));

	}

	@Test
	public void isTestMethodShouldRun() throws InitializationError, SecurityException, NoSuchMethodException {
		GroupsTestRunner groupsTestRunner = new GroupsTestRunner(GroupsTestRunnerTest.class);

		//从application-core-test.properties中取出test.groups值, 为DAILY
		ReflectionUtils.invokeMethod(groupsTestRunner, "initGroups", new Class[] { String.class },
				new Object[] { "application.core.test.properties" });

		assertEquals(true, GroupsTestRunner.shouldRun(TestClassBean1.class.getMethod("shouldRun", new Class[] {})));
		assertEquals(true, GroupsTestRunner.shouldRun(TestClassBean2.class.getMethod("shouldRun", new Class[] {})));
		assertEquals(false, GroupsTestRunner
				.shouldRun(TestClassBean3.class.getMethod("shouldNeverRun", new Class[] {})));

	}

	@RunWith(GroupsTestRunner.class)
	public static class TestClassBean1 {
		@Test
		@Groups("DAILY")
		public void shouldRun() {
		}

		@Test
		@Groups("foo")
		public void shouldNeverRun() {
			fail("the method in foo group should never run");
		}
	}

	@RunWith(GroupsTestRunner.class)
	public static class TestClassBean2 {
		@Test
		public void shouldRun() {
		}
	}

	@RunWith(GroupsTestRunner.class)
	public static class TestClassBean3 {
		@BeforeClass
		public static void shuoldNeverRunBeforeClass() {
			fail("the method in foo group should never run");
		}

		@Before
		public void shuoldNeverRunBefore() {
			fail("the method in foo group should never run");
		}

		@Test
		@Groups("foo")
		public void shouldNeverRun() {
			fail("the method in foo group should never run");
		}
	}
}
