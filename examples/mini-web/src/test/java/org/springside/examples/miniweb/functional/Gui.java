package org.springside.examples.miniweb.functional;

public class Gui {

	public static final String BUTTON_LOGIN = "//input[@value='登录']";
	public static final String BUTTON_SUBMIT = "//input[@value='提交']";
	public static final String BUTTON_SEARCH = "//input[@value='搜索']";

	public static final String MENU_USER = "帐号列表";
	public static final String MENU_ROLE = "角色列表";

	public enum UserColumn {
		LOGIN_NAME, NAME, EMAIL, ROLES, OPERATIONS
	}

	public enum RoleColumn {
		NAME, AUTHORITIES, OPERATIONS
	}
}
