package org.springside.modules.unit.utils;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.springside.modules.utils.VelocityUtils;

import com.google.common.collect.Maps;

public class VelocityUtilsTest {
	private String TEMPLATE = "hello $name";
	private String ERROR_TEMPLATE = "hello $";

	@Test
	public void render() {
		Map<String, String> model = Maps.newHashMap();
		model.put("name", "calvin");
		String result = VelocityUtils.render(TEMPLATE, model);
		assertEquals("hello calvin", result);
	}

	@Test(expected = Exception.class)
	public void renderErrorTemplate() {
		Map<String, String> model = Maps.newHashMap();
		model.put("name", "calvin");
		VelocityUtils.render(ERROR_TEMPLATE, model);
	}
}
