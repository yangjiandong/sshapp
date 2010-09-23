package org.springside.examples.showcase.unit.log;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.utils.ThreadUtils;

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext-test.xml", "/log/applicationContext-log.xml" })
@TransactionConfiguration(transactionManager = "defaultTransactionManager")
public class AsyncJdbcAppenderTest extends SpringTxTestCase {

	private static final String LOG_TABLE_NAME = "SS_LOG";

	@Test
	public void logToDb() {
		Logger dbLogger = Logger.getLogger("DBLogExample");

		int oldLogsCount = this.countRowsInTable(LOG_TABLE_NAME);

		int i = 1;

		//插入5条记录,未真正插入数据库.
		for (; i <= 5; i++) {
			dbLogger.info("helloworld" + i);
		}
		ThreadUtils.sleep(1000);
		assertEquals(oldLogsCount, this.countRowsInTable(LOG_TABLE_NAME));

		//再插入5条记录,达到batchSize(10),插入数据库.
		for (; i <= 10; i++) {
			dbLogger.info("helloworld" + i);
		}
		ThreadUtils.sleep(1000);
		assertEquals(oldLogsCount + 10, this.countRowsInTable(LOG_TABLE_NAME));
	}

	@Test
	@Ignore("Only for performance Test")
	public void performanceTest() throws InterruptedException {
		Logger dbLogger = Logger.getLogger("DBLogExample");
		int totalCount = 20000;
		int tps = 4000;
		Date batchBegin = new Date();
		for (int i = 1; i <= totalCount; i++) {
			dbLogger.info("helloworld " + i);
			if (i % tps == 0) {
				Date batchEnd = new Date();
				long sleepMillSecs = 1000 - (batchEnd.getTime() - batchBegin.getTime());
				if (sleepMillSecs > 0) {
					Thread.sleep(sleepMillSecs);
				}
				batchBegin = new Date();
			}
		}
	}
}
