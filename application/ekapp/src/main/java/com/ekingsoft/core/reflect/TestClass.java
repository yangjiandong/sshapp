package com.ekingsoft.core.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestClass {

	public static void main(String args[]) {
		try {
			// 通过反射获取class
			Class<?> clazz = Class
					.forName("com.ekingsoft.core.reflect.MethodReflect");

			// 定义被调用方法的参数类型列表
			Class<?> partypes[] = new Class[2];
			partypes[0] = Integer.TYPE;
			partypes[1] = Integer.TYPE;

			// 获取属性
			Field fldName = clazz.getDeclaredField("name");
			if (!fldName.isAccessible()) {
				fldName.setAccessible(true);
			}
			System.out.println(fldName.getType());

			// 获取被调用的方法(有参数)
			Method meth = clazz.getMethod("add", partypes);
			// 获取被调用的方法(无参数)
			Method testMeth = clazz.getDeclaredMethod("getReturnString");
			if (!testMeth.isAccessible()) {
				testMeth.setAccessible(true);
			}

			// 创建调用类的对象
			Object methobj = clazz.newInstance();
			// 创建调用方法的参数列表
			Object arglist[] = new Object[2];
			arglist[0] = new Integer(37);
			arglist[1] = new Integer(47);

			// 调用方法(有参数)
			Object retobj = meth.invoke(methobj, arglist);
			Integer retval = (Integer) retobj;
			System.out.println(retval.intValue());

			// 调用方法(无参数)
			Object retobj2 = testMeth.invoke(methobj);
			String retval2 = retobj2.toString();
			System.out.println(retval2);
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
}
