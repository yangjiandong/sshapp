package org.springside.examples.showcase.unit.common;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springside.examples.showcase.common.dao.UIDGenerator;

public class UIDGeneratorTest {

	@Test
	public void generateId() {
		UIDGenerator generator = new UIDGenerator();
		String id = (String) generator.generate(null, null);
		assertTrue(id.length() == 16);
		System.out.println(id);
	}
}
