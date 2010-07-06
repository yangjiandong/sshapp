package org.ssh.app.common;

public enum ResourceTypeEnum {
	SUBSYSTEM("子系统"), SUBMENU("菜单项");
	String resourceType;

	ResourceTypeEnum(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getValue() {
		return resourceType;
	}
}
