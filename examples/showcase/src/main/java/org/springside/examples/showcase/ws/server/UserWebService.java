package org.springside.examples.showcase.ws.server;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.springside.examples.showcase.ws.server.result.GetAllUserResult;
import org.springside.examples.showcase.ws.server.result.GetUserResult;

/**
 * JAX-WS2.0的WebService接口定义类.
 * 
 * @author calvin
 */
@WebService(name = "UserService", targetNamespace = WsConstants.NS)
public interface UserWebService {
	/**
	 * 显示所有用户.
	 */
	GetAllUserResult getAllUser();

	/**
	 * 获取用户, 受SpringSecurity保护.
	 */
	GetUserResult getUser(@WebParam(name = "id") String id);
}
