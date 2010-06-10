package com.ekingsoft.core.reflect;

public class MethodReflect {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int add(int a, int b) {
		return a + b;
	}

	public String getReturnString() {
		return "test string";
	}
}
