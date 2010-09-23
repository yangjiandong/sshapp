package org.springside.examples.miniservice.functional.rs;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springside.examples.miniservice.data.AccountData;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.examples.miniservice.functional.BaseFunctionalTestCase;
import org.springside.examples.miniservice.rs.client.UserResourceClient;
import org.springside.examples.miniservice.rs.dto.UserDTO;

import com.sun.jersey.api.client.UniformInterfaceException;

public class UserResourceServiceTest extends BaseFunctionalTestCase {

	private static UserResourceClient client;

	@BeforeClass
	public static void setUpClient() throws Exception {
		client = new UserResourceClient();
		client.setBaseUrl(BASE_URL + "/rs");
	}

	@Test
	public void getAllUser() {
		List<UserDTO> userList = client.getAllUser();
		assertTrue(userList.size() >= 6);
		assertEquals("admin", userList.get(0).getLoginName());
	}

	@Test
	public void getUser() {
		UserDTO admin = client.getUser(1L);
		assertEquals("admin", admin.getLoginName());
		assertEquals(2, admin.getRoleList().size());
		assertEquals("管理员", admin.getRoleList().get(0).getName());
	}

	@Test
	public void getUserWithInvalidId() {
		try {
			client.getUser(999L);
			fail("Should thrown exception while invalid id");
		} catch (UniformInterfaceException e) {
			assertEquals(404, e.getResponse().getStatus());
		}
	}

	@Test
	public void createUser() {
		User user = AccountData.getRandomUser();
		UserDTO dto = new DozerBeanMapper().map(user, UserDTO.class);

		URI uri = client.createUser(dto);
		assertNotNull(uri);
		System.out.println("Created user uri:" + uri);
	}
}
