package org.springside.examples.miniservice.data;

import org.springside.examples.miniservice.entity.account.Role;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.modules.test.utils.DataUtils;

/**
 * 用户测试数据生成.
 * 
 * @author calvin
 */
public class AccountData {

	public static User getRandomUser() {
		String userName = DataUtils.randomName("User");

		User user = new User();
		user.setLoginName(userName);
		user.setName(userName);
		user.setPassword("123456");
		user.setEmail(userName + "@springside.org.cn");

		return user;
	}

	public static User getRandomUserWithAdminRole() {
		User user = AccountData.getRandomUser();
		Role adminRole = AccountData.getAdminRole();
		user.getRoleList().add(adminRole);
		return user;
	}

	public static Role getAdminRole() {
		Role role = new Role();
		role.setId(1L);
		role.setName("Admin");

		return role;
	}
}
