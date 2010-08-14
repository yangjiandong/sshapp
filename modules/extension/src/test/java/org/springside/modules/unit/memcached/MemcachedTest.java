package org.springside.modules.unit.memcached;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.memcached.JmemcachedServer;
import org.springside.modules.memcached.SpyMemcachedClient;
import org.springside.modules.memcached.SpyMemcachedClientFactory;
import org.springside.modules.test.spring.SpringContextTestCase;

@ContextConfiguration(locations = { "/applicationContext-extension-test-memcached.xml" })
public class MemcachedTest extends SpringContextTestCase {

	private static JmemcachedServer server = new JmemcachedServer();

	@Autowired
	private SpyMemcachedClientFactory spyClientFactory;

	@BeforeClass
	public static void beforeClass() throws Exception {
		server.start();
	}

	@AfterClass
	public static void afterClass() throws Exception {
		server.stop();
	}

	@Test
	public void normal() {
		SpyMemcachedClient spyClient = spyClientFactory.getClient();

		String key = "consumer:1";
		String value = "admin";

		spyClientFactory.getClient().set(key, 60 * 60 * 1, value);
		String result = spyClient.get(key);
		assertEquals(value, result);

		spyClient.delete(key);
		result = spyClient.get(key);
		assertNull(result);
	}

	@Test
	public void bulk() {
		SpyMemcachedClient spyClient = spyClientFactory.getClient();

		String key1 = "consumer:1";
		String value1 = "admin";

		String key2 = "consumer:2";
		String value2 = "calvin";

		String key3 = "invalidKey";

		spyClient.set(key1, 60 * 60 * 1, value1);
		spyClient.set(key2, 60 * 60 * 1, value2);

		Map<String, String> result = spyClient.getBulk(key1, key2);
		assertEquals(value1, result.get(key1));
		assertEquals(value2, result.get(key2));
		assertNull(result.get(key3));
	}

	@Test
	public void incr() {
		SpyMemcachedClient spyClient = spyClientFactory.getClient();

		String key = "incr_key";

		spyClient.incr(key, 2, 1);
		assertEquals("1", spyClient.get(key));

		spyClient.incr(key, 2, 1);
		assertEquals("3", spyClient.get(key));
	}

	public static class Consumer {

		private int id;
		private String name;
		private String email = "admin@springside.org.cn";

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((email == null) ? 0 : email.hashCode());
			result = prime * result + id;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Consumer other = (Consumer) obj;
			if (email == null) {
				if (other.email != null)
					return false;
			} else if (!email.equals(other.email))
				return false;
			if (id != other.id)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

	}
}
