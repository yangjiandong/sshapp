/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: SpringTxTestCase.java 997 2010-03-23 13:34:47Z calvinxiu $
 */
package org.springside.modules.test.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring的支持数据库访问和依赖注入的JUnit4 集成测试基类.
 * 
 *  1.Spring Context IOC support
 *  2.Spring Transaction support 
 *  3.Spring JdbcTemplate and util functions
 *  4.JUnit Assert functions 
 *  
 * 子类需要定义applicationContext文件的位置, 如:
 * @ContextConfiguration(locations = { "/applicationContext-test.xml" })
 *  
 * @see AbstractTransactionalJUnit4SpringContextTests
 * @see SpringContextTestCase
 * 
 * @author calvin
 */
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class SpringTxTestCase extends SpringContextTestCase {

	protected DataSource dataSource;

	protected SimpleJdbcTemplate jdbcTemplate;

	protected String sqlScriptEncoding;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}

	public void setSqlScriptEncoding(String sqlScriptEncoding) {
		this.sqlScriptEncoding = sqlScriptEncoding;
	}

	protected int countRowsInTable(String tableName) {
		return SimpleJdbcTestUtils.countRowsInTable(this.jdbcTemplate, tableName);
	}

	protected int deleteFromTables(String... names) {
		return SimpleJdbcTestUtils.deleteFromTables(this.jdbcTemplate, names);
	}

	protected void runSql(String sqlResourcePath, boolean continueOnError) throws DataAccessException {
		Resource resource = this.applicationContext.getResource(sqlResourcePath);
		SimpleJdbcTestUtils.executeSqlScript(this.jdbcTemplate, new EncodedResource(resource, this.sqlScriptEncoding),
				continueOnError);
	}
}
