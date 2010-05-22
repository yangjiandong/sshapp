package org.springside.modules.unit.orm;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.orm.PropertyFilter.MatchType;

/**
 * PropertyFilter的测试类
 * 
 * @author calvin
 */
public class PropertyFilterTest extends Assert {

	@Test
	public void test() {
		//Boolean EQ filter
		PropertyFilter booleanEqFilter = new PropertyFilter("EQB_foo", "true");
		assertEquals(MatchType.EQ, booleanEqFilter.getMatchType());
		assertEquals(Boolean.class, booleanEqFilter.getPropertyType());
		assertEquals(true, booleanEqFilter.getPropertyValue());

		//Date LT filter
		PropertyFilter dateLtFilter = new PropertyFilter("LTD_foo", "2046-01-01");
		assertEquals(MatchType.LT, dateLtFilter.getMatchType());
		assertEquals(Date.class, dateLtFilter.getPropertyType());
		assertEquals("foo", dateLtFilter.getPropertyName());
		assertEquals(new DateTime(2046, 1, 1, 0, 0, 0, 0).toDate(), dateLtFilter.getPropertyValue());

		//Integer GT filter
		PropertyFilter intGtFilter = new PropertyFilter("GTI_foo", "123");
		assertEquals(MatchType.GT, intGtFilter.getMatchType());
		assertEquals(Integer.class, intGtFilter.getPropertyType());
		assertEquals("foo", intGtFilter.getPropertyName());
		assertEquals(123, intGtFilter.getPropertyValue());

		//Double LE filter
		PropertyFilter doubleLeFilter = new PropertyFilter("LEN_foo", "12.33");
		assertEquals(MatchType.LE, doubleLeFilter.getMatchType());
		assertEquals(Double.class, doubleLeFilter.getPropertyType());
		assertEquals("foo", doubleLeFilter.getPropertyName());
		assertEquals(12.33, doubleLeFilter.getPropertyValue());

		//Long GE filter
		PropertyFilter longGeFilter = new PropertyFilter("GEL_foo", "123456789");
		assertEquals(MatchType.GE, longGeFilter.getMatchType());
		assertEquals(Long.class, longGeFilter.getPropertyType());
		assertEquals("foo", longGeFilter.getPropertyName());
		assertEquals(123456789L, longGeFilter.getPropertyValue());

		//Like OR filter
		PropertyFilter likeOrFilter = new PropertyFilter("LIKES_foo_OR_bar", "hello");
		assertEquals(MatchType.LIKE, likeOrFilter.getMatchType());
		assertEquals(String.class, likeOrFilter.getPropertyType());
		assertArrayEquals(new String[] { "foo", "bar" }, likeOrFilter.getPropertyNames());
		assertEquals("hello", likeOrFilter.getPropertyValue());
	}

	@Test
	public void errorFilterName() throws Exception {
		int exceptionCount = 0;
		try {
			new PropertyFilter("ABS_foo", "hello");
		} catch (IllegalArgumentException e) {
			exceptionCount++;
		}

		try {
			new PropertyFilter("GEX_foo", "hello");
		} catch (IllegalArgumentException e) {
			exceptionCount++;
		}

		try {
			new PropertyFilter("EQS_", "hello");
		} catch (IllegalArgumentException e) {
			exceptionCount++;
		}

		assertEquals(3, exceptionCount);
	}
}
