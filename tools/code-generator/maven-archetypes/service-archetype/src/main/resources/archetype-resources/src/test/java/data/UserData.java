#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.data;

import org.apache.commons.lang.RandomStringUtils;
import ${package}.entity.user.Role;
import ${package}.entity.user.User;

/**
 * 用户测试数据生成.
 * 
 * @author calvin
 */
public class UserData {

	public static User getRandomUser() {
		String userName = "User" + random();

		User user = new User();
		user.setLoginName(userName);
		user.setName(userName);
		user.setPassword("passwd");
		user.setEmail(userName + "@springside.org.cn");

		return user;
	}

	public static Role getRandomRole() {
		Role role = new Role();
		role.setName("Role" + random());

		return role;
	}

	public static Role getAdminRole() {
		Role role = new Role();
		role.setId(1L);

		return role;
	}

	public static String random() {
		return RandomStringUtils.randomAlphanumeric(5);
	}
}
