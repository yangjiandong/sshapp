package org.springside.modules.unit.log;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.log.MockLog4jAppender;

public class MockLog4jAppenderTest extends Assert {

	@Test
	public void test() {
		String testString1 = "Hello";
		String testString2 = "World";
		MockLog4jAppender appender = new MockLog4jAppender();
		appender.addToLogger("org.springside");

		Logger logger = LoggerFactory.getLogger(MockLog4jAppenderTest.class);
		logger.warn(testString1);
		logger.warn(testString2);
		assertEquals(testString1, appender.getFirstLog().getMessage());

		assertEquals(testString2, appender.getLastLog().getMessage());
		assertEquals(2, appender.getAllLogs().size());
		assertEquals(testString2, appender.getAllLogs().get(1).getMessage());

		appender.clearLogs();
		assertNull(appender.getFirstLog());
	}

}
