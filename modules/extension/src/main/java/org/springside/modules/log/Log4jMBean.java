/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: Log4jMBean.java 966 2010-02-24 16:56:28Z calvinxiu $
 */
package org.springside.modules.log;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Appender;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * 基于JMX动态配置Logger日志等级, 获取Logger Appender的MBean.
 * 
 * @author calvin
 */
@ManagedResource(objectName = Log4jMBean.LOG4J_MBEAN_NAME, description = "Log4j Management Bean")
public class Log4jMBean {

	/**
	 * Log4jMbean注册的名称.
	 */
	public static final String LOG4J_MBEAN_NAME = "SpringSide:type=Log4jManagement,name=log4jManagement";

	private static org.slf4j.Logger mbeanLogger = LoggerFactory.getLogger(Log4jMBean.class);

	/**
	 * 获取Root Logger的日志级别.
	 */
	@ManagedAttribute(description = "Logging level of the root logger")
	public String getRootLoggerLevel() {
		Logger root = Logger.getRootLogger();
		return root.getLevel().toString();
	}

	/**
	 * 设置Root Logger的日志级别.
	 */
	@ManagedAttribute(description = "Logging level of the root logger")
	public void setRootLoggerLevel(String newLevel) {
		Logger root = Logger.getRootLogger();
		Level level = Level.toLevel(newLevel);
		root.setLevel(level);
		mbeanLogger.info("设置Root Logger级别到{}", newLevel);
	}

	/**
	 * 获取Logger的日志级别.
	 */
	@ManagedOperation(description = "Get logging level of the logger")
	@ManagedOperationParameters( { @ManagedOperationParameter(name = "loggerName", description = "Logger name") })
	public String getLoggerLevel(String loggerName) {
		Logger logger = Logger.getLogger(loggerName);
		return logger.getEffectiveLevel().toString();
	}

	/**
	 * 设置Logger的日志级别.
	 */
	@ManagedOperation(description = "Set new logging level to the logger")
	@ManagedOperationParameters( { @ManagedOperationParameter(name = "loggerName", description = "Logger name"),
			@ManagedOperationParameter(name = "newlevel", description = "New level") })
	public void setLoggerLevel(String loggerName, String newLevel) {
		Logger logger = Logger.getLogger(loggerName);
		Level level = Level.toLevel(newLevel);
		logger.setLevel(level);
		mbeanLogger.info("设置{}级别到{}", loggerName, newLevel);
	}

	/**
	 * 获取Root Logger的所有Appender的名称.
	 */
	@ManagedAttribute(description = "Get all appenders of the root logger")
	public List<String> getRootLoggerAppenders() {
		Logger root = Logger.getRootLogger();
		return getLoggerAppenders(root);
	}

	/**
	 * 获取Logger的所有Appender的名称.
	 * 继承而来的Appender名称后将有(parent)标识.
	 */
	@ManagedOperation(description = "Get all appenders of the logger")
	@ManagedOperationParameters( { @ManagedOperationParameter(name = "loggerName", description = "Logger name") })
	public List<String> getLoggerAppenders(String loggerName) {
		Logger logger = Logger.getLogger(loggerName);
		return getLoggerAppenders(logger);

	}

	/**
	 * 获取Logger的所有Appender的名称.
	 */
	@SuppressWarnings("unchecked")
	private List<String> getLoggerAppenders(Logger logger) {
		List<String> appenderNameList = new ArrayList<String>();
		//循环加载logger及其parent的appenders
		for (Category c = logger; c != null; c = c.getParent()) {
			Enumeration e = c.getAllAppenders();
			while (e.hasMoreElements()) {
				Appender appender = (Appender) e.nextElement();
				String appenderName = appender.getName();
				if (c != logger) {//NOSONAR
					appenderName += "(parent)";
				}
				appenderNameList.add(appenderName);
			}

			//如果logger不继承parent的appender,则停止向上循环.
			if (!c.getAdditivity()) {
				break;
			}
		}

		return appenderNameList;
	}
}
