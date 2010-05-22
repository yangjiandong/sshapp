/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: WebTestUtils.java 1068 2010-05-07 16:00:06Z calvinxiu $
 */
package org.springside.modules.test.utils;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.opensymphony.xwork2.ActionContext;

/**
 * Web集成测试工具类.
 * 
 * 1.Spring WebApplicationContext初始化到ServletContext.
 * 2.将MockRequest/Response放入Struts2的ServletActionContext.
 * 
 * @author calvin
 */
public class WebTestUtils {

	private static boolean struts2ContextInited = false;

	/**
	 * 在ServletContext里初始化Spring WebApplicationContext.
	 * 
	 * @param configLocations application context路径列表.
	 */
	public static void initWebApplicationContext(MockServletContext servletContext, String... configLocations) {
		String configLocationsString = StringUtils.join(configLocations, ",");
		servletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, configLocationsString);
		new ContextLoader().initWebApplicationContext(servletContext);
	}

	/**
	 * 在ServletContext里初始化Spring WebApplicationContext.
	 * 
	 * @param applicationContext 已创建的ApplicationContext.
	 */
	public static void initWebApplicationContext(MockServletContext servletContext,
			ApplicationContext applicationContext) {
		ConfigurableWebApplicationContext wac = new XmlWebApplicationContext();
		wac.setParent(applicationContext);
		wac.setServletContext(servletContext);
		wac.setConfigLocation("");
		servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, wac);
		wac.refresh();
	}

	/**
	 * 关闭ServletContext中的Spring WebApplicationContext.
	 */
	public static void closeWebApplicationContext(MockServletContext servletContext) {
		new ContextLoader().closeWebApplicationContext(servletContext);
	}

	/**
	 * 将request放入Struts2的ServletActionContext,支持Struts2待测代码用ServletActionContext.getRequest()取出MockRequest.
	 */
	public static void setRequestToStruts2(HttpServletRequest request) {
		initStruts2ActionContext();
		ServletActionContext.setRequest(request);
	}

	/**
	 * 将response放入Struts2的ServletActionContext,支持Struts2待测代码用ServletActionContext.getResponse()取出MockResponse.
	 */
	public static void setResponseToStruts2(HttpServletResponse response) {
		initStruts2ActionContext();
		ServletActionContext.setResponse(response);
	}

	/**
	 * 初始化 Struts2 ActionContext.
	 */
	@SuppressWarnings("unchecked")
	private static void initStruts2ActionContext() {
		if (!struts2ContextInited) {
			ActionContext.setContext(new ActionContext(new HashMap()));
			struts2ContextInited = true;
		}
	}
}
