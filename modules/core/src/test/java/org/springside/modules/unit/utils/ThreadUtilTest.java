/*
 * $HeadURL: https://springside.googlecode.com/svn/springside3/trunk/modules/core/src/test/java/org/springside/modules/unit/utils/ThreadUtilTest.java $
 * $Id: ThreadUtilTest.java 1146 2010-08-05 15:28:39Z calvinxiu $
 * Copyright (c) 2010 by Ericsson, all rights reserved.
 */

package org.springside.modules.unit.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.log.MockLog4jAppender;
import org.springside.modules.utils.ThreadUtils;
import org.springside.modules.utils.ThreadUtils.CustomizableThreadFactory;

public class ThreadUtilTest extends Assert {

	@Test
	public void customizableThreadFactory() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
			}
		};
		CustomizableThreadFactory factory = new CustomizableThreadFactory("foo");

		Thread thread = factory.newThread(runnable);
		assertEquals("foo-1", thread.getName());

		Thread thread2 = factory.newThread(runnable);
		assertEquals("foo-2", thread2.getName());
	}

	@Test
	public void gracefulShutdown() throws InterruptedException {

		Logger logger = Logger.getLogger("test");
		MockLog4jAppender appender = new MockLog4jAppender();
		appender.addToLogger(logger);

		//time enough to shutdown
		ExecutorService pool = Executors.newSingleThreadExecutor();
		Runnable task = new Task(logger, 100, 0);
		pool.execute(task);
		ThreadUtils.gracefulShutdown(pool, 200, TimeUnit.MILLISECONDS);
		assertTrue(pool.isTerminated());
		assertNull(appender.getFirstLog());

		//time not enough to shutdown,call shutdownNow
		appender.clearLogs();
		pool = Executors.newSingleThreadExecutor();
		task = new Task(logger, 200, 0);
		pool.execute(task);
		ThreadUtils.gracefulShutdown(pool, 100, TimeUnit.MILLISECONDS);
		assertTrue(pool.isTerminated());
		assertEquals("InterruptedException", appender.getFirstLog().getMessage());

		//self thread interrupt while calling gracefulShutdown
		appender.clearLogs();

		final ExecutorService pool2 = Executors.newSingleThreadExecutor();
		task = new Task(logger, 100000, 0);
		pool2.execute(task);

		final CountDownLatch lock = new CountDownLatch(1);
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				lock.countDown();
				ThreadUtils.gracefulShutdown(pool2, 200000, TimeUnit.MILLISECONDS);
			}
		});
		thread.start();
		lock.await();
		thread.interrupt();
		Thread.sleep(500);
		assertEquals("InterruptedException", appender.getFirstLog().getMessage());
	}

	@Test
	public void normalShutdown() throws InterruptedException {

		Logger logger = Logger.getLogger("test");
		MockLog4jAppender appender = new MockLog4jAppender();
		appender.addToLogger(logger);

		//time not enough to shutdown,call shutdownNow
		appender.clearLogs();
		ExecutorService pool = Executors.newSingleThreadExecutor();
		Runnable task = new Task(logger, 200, 0);
		pool.execute(task);
		ThreadUtils.normalShutdown(pool, 100, TimeUnit.MILLISECONDS);
		assertTrue(pool.isTerminated());
		assertEquals("InterruptedException", appender.getFirstLog().getMessage());

		//self thread interrupt while calling gracefulShutdown
		appender.clearLogs();
		//        pool = Executors.newSingleThreadExecutor();

		final ExecutorService pool2 = Executors.newSingleThreadExecutor();
		task = new Task(logger, 100000, 1000);
		pool2.execute(task);

		final CountDownLatch lock = new CountDownLatch(1);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				lock.countDown();
				ThreadUtils.normalShutdown(pool2, 200000, TimeUnit.MILLISECONDS);
			}
		});
		thread.start();
		lock.await();
		thread.interrupt();

		Thread.sleep(1500);

		assertEquals("InterruptedException", appender.getFirstLog().getMessage());
	}

	static class Task implements Runnable {
		private Logger logger;

		private int runTime = 0;

		private int sleepTime;

		Task(Logger logger, int sleepTime, int runTime) {
			this.logger = logger;
			this.sleepTime = sleepTime;
			this.runTime = runTime;
		}

		@Override
		public void run() {
			System.out.println("start task");
			if (runTime > 0) {
				long start = System.currentTimeMillis();
				while (System.currentTimeMillis() - start < runTime) {
					;
				}
			}

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				logger.warn("InterruptedException");
			}
		}
	}
}
