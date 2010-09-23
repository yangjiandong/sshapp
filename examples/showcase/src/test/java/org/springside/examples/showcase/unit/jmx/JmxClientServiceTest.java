package org.springside.examples.showcase.unit.jmx;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.jmx.client.JmxClientService;
import org.springside.modules.test.spring.SpringContextTestCase;

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext-test.xml", "/jmx/applicationContext-jmx-server.xml",
		"/jmx/applicationContext-jmx-client.xml" })
public class JmxClientServiceTest extends SpringContextTestCase {

	@Autowired
	private JmxClientService jmxClientService;

	@Test
	public void serverConfig() {
		//prepare
		String oldNodeName = jmxClientService.getNodeName();

		jmxClientService.setNodeName("node1");
		assertEquals("node1", jmxClientService.getNodeName());

		jmxClientService.setNotificationMailEnabled(false);
		assertEquals(false, jmxClientService.isNotificationMailEnabled());

		//clean
		jmxClientService.setNodeName(oldNodeName);
	}

	@Test
	public void trace() {
		jmxClientService.startTrace();
		assertEquals(true, jmxClientService.getTraceStatus());
		jmxClientService.stopTrace();
		assertEquals(false, jmxClientService.getTraceStatus());
	}
}
