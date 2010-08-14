/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: MockLog4jAppender.java 1095 2010-05-22 12:44:14Z calvinxiu $
 */
package org.springside.modules.log;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 在List中保存日志的Appender, 用于测试日志输出.
 * 
 * 在测试开始前,使用addToLogger方法将本logger添加到需要侦听的logger.
 * 
 * @author calvin
 */
public class MockLog4jAppender extends AppenderSkeleton {

	private List<LoggingEvent> logs = new ArrayList<LoggingEvent>();

	/**
	 * 返回之前append的第一个log事件.
	 */
	public LoggingEvent getFirstLog() {
		if (logs.isEmpty()) {
			return null;
		}
		return logs.get(0);
	}

	/**
	 * 返回之前append的最后一个log事件.
	 */
	public LoggingEvent getLastLog() {
		if (logs.isEmpty()) {
			return null;
		}
		return logs.get(logs.size() - 1);
	}

	/**
	 * 返回之前append的log事件列表.
	 */
	public List<LoggingEvent> getAllLogs() {
		return logs;
	}

	/**
	 * 清除之前append的log事件列表.
	 */
	public void clearLogs() {
		logs.clear();
	}

	/**
	 * 将本Appender添加到logger中.
	 */
	public void addToLogger(Logger logger) {
		logger.addAppender(this);
	}

	/**
	 * 将本Appender添加到logger中.
	 */
	public void addToLogger(String loggerName) {
		Logger logger = Logger.getLogger(loggerName);
		logger.addAppender(this);
	}

	/**
	 * 将本Appender添加到logger中.
	 */
	public void addToLogger(Class<?> loggerClass) {
		Logger logger = Logger.getLogger(loggerClass);
		logger.addAppender(this);
	}

	/**
	 * 实现AppenderSkeleton的append函数, 将log事件加入到内部的List<Event>.
	 */
	@Override
	protected void append(LoggingEvent event) {
		logs.add(event);
	}

	/**
	 * @see AppenderSkeleton#close()
	 */
	public void close() {
	}

	/**
	 * @see AppenderSkeleton#requiresLayout()
	 */
	public boolean requiresLayout() {
		return false;
	}
}
