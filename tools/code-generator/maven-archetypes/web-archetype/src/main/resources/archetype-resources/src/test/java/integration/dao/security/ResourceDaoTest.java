#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.dao.security;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ${package}.dao.security.ResourceDao;
import ${package}.data.SecurityData;
import ${package}.entity.security.Resource;
import org.springside.modules.test.spring.SpringTxTestCase;

/**
 * ResourceDao的集成测试用例,测试ORM映射及特殊的DAO操作.
 * 
 * @author calvin
 */
public class ResourceDaoTest extends SpringTxTestCase {
	@Autowired
	private ResourceDao entityDao;

	@Test
	public void crudEntity() {
		//new entity and save it. 
		Resource entity = SecurityData.getRandomResource();
		entityDao.save(entity);
		flush();

		//find entity.
		Resource entityFromDB = entityDao.findUniqueBy("id", entity.getId());
		assertReflectionEquals(entity, entityFromDB);

		//delete entity.
		entityDao.delete(entity.getId());
		flush();
		entity = entityDao.findUniqueBy("id", entity.getId());
		assertNull(entity);
	}

	@Test
	public void getUrlResourceWithAuthorities() {
		List<Resource> resourceList = entityDao.getUrlResourceWithAuthorities();

		//校验资源的总数、排序及其授权已初始化
		assertEquals(6, resourceList.size());
		Resource resource = resourceList.get(0);
		assertEquals(1.0, resource.getPosition());
		evict(resource);
		assertTrue(resource.getAuthorities().size() > 0);
	}
}