package org.springside.examples.showcase.log;

import org.apache.struts2.convention.annotation.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 演示向数据库写入日志的Action.
 * 
 * @author calvin
 */
@Namespace("/log")
@SuppressWarnings("serial")
public class LogAction extends ActionSupport {

	/**
	 * 在log4j.properties中,本logger已被指定使用asyncAppender.
	 */
	public static final String DB_LOGGER_NAME = "DBLogExample";

	@Override
	public String execute() {
		Logger logger = LoggerFactory.getLogger(DB_LOGGER_NAME);
		logger.info("helloworld!!");
		return SUCCESS;
	}
}
