package org.springside.modules.orm.jdbc;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * 使用Velocity生成sql的工具类.
 * 
 * @author calvin
 */
public class SqlBuilder {

	static {
		try {
			Velocity.init();
		} catch (Exception e) {
			throw new RuntimeException("Exception occurs while initialzie the velociy: " + e.getMessage(), e);
		}
	}

	public static String getSql(String sqlTemplate, Map<String, ?> model) {
		try {
			VelocityContext velocityContext = new VelocityContext(model);
			StringWriter result = new StringWriter();
			Velocity.evaluate(velocityContext, result, "", sqlTemplate);
			return result.toString();
		} catch (IOException e) {
			throw new RuntimeException("Parse sql failed", e);
		}
	}
}
