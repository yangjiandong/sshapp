package org.springside.modules.unit.orm.hibernate;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.orm.hibernate.HibernateUtils;

import com.google.common.collect.Lists;

public class HibernateUtilsTest extends Assert {

	@Test
	public void mergeByCheckedIds() {
		List<TestBean> srcList = Lists.newArrayList(new TestBean("A"), new TestBean("B"));
		List<String> idList = Lists.newArrayList("A", "C");

		HibernateUtils.mergeByCheckedIds(srcList, idList, TestBean.class);

		assertEquals(2, srcList.size());
		assertEquals("A", srcList.get(0).getId());
		assertEquals("C", srcList.get(1).getId());

	}

	@Test
	public void buildPropertyFilters() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("filter_EQS_loginName", "abcd");
		request.setParameter("filter_LIKES_name_OR_email", "efg");

		List<PropertyFilter> filters = HibernateUtils.buildPropertyFilters(request);

		assertEquals(2, filters.size());

		PropertyFilter filter1 = filters.get(0);
		assertEquals(PropertyFilter.MatchType.EQ, filter1.getMatchType());
		assertEquals("loginName", filter1.getPropertyName());
		assertEquals(String.class, filter1.getPropertyType());
		assertEquals("abcd", filter1.getPropertyValue());

		PropertyFilter filter2 = filters.get(1);
		assertEquals(PropertyFilter.MatchType.LIKE, filter2.getMatchType());
		assertEquals(String.class, filter2.getPropertyType());
		assertEquals(true, filter2.isMultiProperty());
		assertEquals("efg", filter2.getPropertyValue());
	}

	public static class TestBean {
		private String id;

		public TestBean() {
		}

		public TestBean(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}

}
