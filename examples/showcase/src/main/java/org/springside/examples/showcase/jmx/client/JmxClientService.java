package org.springside.examples.showcase.jmx.client;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springside.examples.showcase.jmx.server.ServerConfigMBean;
import org.springside.modules.utils.jmx.JmxClientTemplate;

/**
 * JMX客户端对服务端访问的封装.
 * 
 * 分别演示标准MBean代理及无MBean的Class文件时直接反射读取属性/调用方法两种场景.
 * 
 * @author ben
 * @author calvin
 */
public class JmxClientService {

	public static final String TRACE_MBEAN_NAME = "Showcase:name=trace,type=Trace";

	private static Logger logger = LoggerFactory.getLogger(JmxClientService.class);

	//-- 可注入的连接参数 --//
	private String host;
	private String port;
	private String userName;
	private String passwd;

	//-- JMX客户端及Mbean代理 --//
	private JmxClientTemplate jmxClientTemplate;
	private ServerConfigMBean serverConfigMBean;

	//-- 连接参数设定函数 --//
	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	//-- 连接与断开远程连接 --//
	/**
	 * 连接JMX Server并创建本地的MBean代理.
	 */
	@PostConstruct
	public void init() {
		Assert.hasText(host, "host参数不能为空");
		Assert.hasText(port, "port参数不能为空");

		try {
			String serviceUrl = "service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi";
			jmxClientTemplate = new JmxClientTemplate(serviceUrl, userName, passwd);
			serverConfigMBean = jmxClientTemplate.createMBeanProxy(ServerConfigMBean.SERVER_CONFIG_MBEAN_NAME,
					ServerConfigMBean.class);
		} catch (Exception e) {
			logger.error("连接Jmx Server 或 创建Mbean Proxy时失败", e);
		}
	}

	/**
	 * 断开JMX连接.
	 */
	@PreDestroy
	public void close() throws IOException {
		jmxClientTemplate.close();
	}

	//-- 标准MBean代理操作演示 --//
	/**
	 * 获取节点名.
	 */
	public String getNodeName() {
		return serverConfigMBean.getNodeName();
	}

	/**
	 * 设置节点名.
	 */
	public void setNodeName(String nodeName) {
		serverConfigMBean.setNodeName(nodeName);
	}

	/**
	 * 获取是否发送通知邮件.
	 */
	public boolean isNotificationMailEnabled() {
		return serverConfigMBean.isNotificationMailEnabled();
	}

	/**
	 * 设置是否发送通知邮件.
	 */
	public void setNotificationMailEnabled(boolean notificationMailEnabled) {
		serverConfigMBean.setNotificationMailEnabled(notificationMailEnabled);
	}

	//-- 无MBean的Class文件时直接访问属性调用方法的演示 --//
	/**
	 * 获取TraceMBean的是否已打开Trace属性.
	 */
	public Boolean getTraceStatus() {
		return (Boolean) jmxClientTemplate.getAttribute(TRACE_MBEAN_NAME, "TraceStatus");
	}

	/**
	 * 调用TraceMBean的startTrace函数.
	 */
	public void startTrace() {
		jmxClientTemplate.inoke(TRACE_MBEAN_NAME, "startTrace");
	}

	/**
	 * 调用TraceMBean的stopTrace函数.
	 */
	public void stopTrace() {
		jmxClientTemplate.inoke(TRACE_MBEAN_NAME, "stopTrace");
	}

}
