package org.springside.examples.miniweb.unit.dao;

import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.EntityPersister;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.test.utils.DbUnitUtils;

/**
 * 简单测试所有Entity类的O/R Mapping.
 *  
 * @author calvin
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class HibernateMappingTest extends SpringTxTestCase {
	private static Logger logger = LoggerFactory.getLogger(HibernateMappingTest.class);
	private static DataSource dataSourceHolder = null;

	@Autowired
	private SessionFactory sessionFactory;

	@Before
	public void loadDefaultData() throws Exception {
		if (dataSourceHolder == null) {
			DbUnitUtils.loadData(dataSource, "/data/default-data.xml");
			dataSourceHolder = dataSource;
		}
	}

	@AfterClass
	public static void cleanDefaultData() throws Exception {
		DbUnitUtils.removeData(dataSourceHolder, "/data/default-data.xml");
	}

	@Test
	@SuppressWarnings("unchecked")
	public void allClassMapping() throws Exception {
		Session session = sessionFactory.openSession();

		try {
			Map metadata = sessionFactory.getAllClassMetadata();
			for (Object o : metadata.values()) {
				EntityPersister persister = (EntityPersister) o;
				String className = persister.getEntityName();
				Query q = session.createQuery("from " + className + " c");
				q.iterate();
				logger.debug("ok: " + className);
			}
		} finally {
			session.close();
		}
	}
}
