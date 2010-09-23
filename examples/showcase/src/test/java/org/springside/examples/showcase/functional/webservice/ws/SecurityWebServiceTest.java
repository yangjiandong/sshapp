package org.springside.examples.showcase.functional.webservice.ws;

import static org.junit.Assert.*;

import java.util.Map;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.cxf.BusFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springside.examples.showcase.functional.BaseFunctionalTestCase;
import org.springside.examples.showcase.ws.client.PasswordCallback;
import org.springside.examples.showcase.ws.server.UserWebService;
import org.springside.examples.showcase.ws.server.result.GetAllUserResult;
import org.springside.examples.showcase.ws.server.result.GetUserResult;

import com.google.common.collect.Maps;

/**
 * WS-Security 测试,测试PlainText, Digest, SpringSecurity三种EndPoint.
 * 
 * @author calvin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/webservice/applicationContext-cxf-client.xml" })
public class SecurityWebServiceTest extends BaseFunctionalTestCase implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	public final void setApplicationContext(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * 测试Digest密码认证, 使用CXF的API自行创建Client.
	 * @throws InterruptedException 
	 */
	@Test
	public void getAllUserWithDigestPassword() throws InterruptedException {

		BusFactory.setDefaultBus(null);

		String address = BASE_URL + "/services/UserServiceWithDigestPassword";
		JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
		proxyFactory.setAddress(address);
		proxyFactory.setServiceClass(UserWebService.class);

		//定义WSS4JOutInterceptor
		Map<String, Object> outProps = Maps.newHashMap();
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		outProps.put(WSHandlerConstants.USER, "admin");
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
		outProps.put(WSHandlerConstants.PW_CALLBACK_REF, new PasswordCallback());
		WSS4JOutInterceptor wss4jOut = new WSS4JOutInterceptor(outProps);

		//将Inteceptor加入factory
		proxyFactory.getOutInterceptors().add(wss4jOut);

		//生成clientProxy
		UserWebService userWebService = (UserWebService) proxyFactory.create();

		//调用UserWebService
		GetAllUserResult result = userWebService.getAllUser();
		assertTrue(result.getUserList().size() > 0);
	}

	/**
	 * 测试明文密码, 用<jaxws:client/>创建的Client.
	 */
	@Test
	public void getAllUserWithPlainPassword() {
		UserWebService userWebService = (UserWebService) applicationContext.getBean("userServiceWithPlainPassword");
		GetAllUserResult result = userWebService.getAllUser();
		assertTrue(result.getUserList().size() > 0);
	}

	/**
	 * 测试访问与SpringSecurity结合的EndPoint, 调用受SpringSecurity保护的方法, 用<jaxws:client/>创建的Client.
	 */
	@Test
	public void getUserWithSpringSecurity() {
		UserWebService userWebService = (UserWebService) applicationContext.getBean("userServiceWithSpringSecurity");
		GetUserResult result = userWebService.getUser("1");
		assertEquals("admin", result.getUser().getLoginName());
	}

	/**
	 * 测试访问没有与SpringSecurity结合的EndPoint, 调用受SpringSecurity保护的方法, 用<jaxws:client/>创建的Client.
	 */
	@Test(expected = SOAPFaultException.class)
	public void getUserWithSpringSecurityWithoutPermission() {
		UserWebService userWebService = (UserWebService) applicationContext.getBean("userServiceWithPlainPassword");
		GetUserResult result = userWebService.getUser("1");
		assertEquals("admin", result.getUser().getLoginName());
	}
}
